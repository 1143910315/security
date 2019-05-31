package com.linjiahao.security.controller;

import com.alibaba.fastjson.JSONObject;
import com.linjiahao.security.data.JsonMessage;
import com.linjiahao.security.tools.Base64Tool;
import com.linjiahao.security.tools.ImageDeal;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

@RestController
public class VerifyController {
    @PostMapping("/noAuthenticate/check_verify.json")
    public JsonMessage checkVerify(HttpServletRequest request, @RequestParam("left") String leftStr) {
        JsonMessage jsonMessage = new JsonMessage();
        try {
            int left = Integer.parseInt(leftStr);
            HttpSession session = request.getSession();
            int attribute;
            try {
                attribute = (int) session.getAttribute("verifyLeft");
            } catch (Exception e) {
                jsonMessage.setStatus(2);
                jsonMessage.setMessage("验证码过期，请刷新验证码");
                return jsonMessage;
            }
            if (Math.abs(left - attribute) < 10) {
                jsonMessage.setStatus(0);
                jsonMessage.setMessage("验证成功");
            } else {
                jsonMessage.setStatus(1);
                jsonMessage.setMessage("验证失败");
            }
        } catch (Exception e) {
            jsonMessage.setException(e);
        }
        return jsonMessage;
    }

    /**
     * 以json的格式返回前端ajax请求，其中包括验证码背景图片，验证码滑块图片，滑块图片高度
     * 图片返回格式为dataURL
     * 目前，验证码背景图片宽高为400×150；滑块图片宽高为40×40
     *
     * @param request 请求的request
     * @return 消息
     */
    @PostMapping("/noAuthenticate/gen_verify.json")
    public JsonMessage generateVerifyImage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        // 返回前端数据
        JSONObject data = new JSONObject();
        JsonMessage jsonMessage = new JsonMessage();
        // 遮罩部分的坐标
        int left = (int) Math.floor(Math.random() * 200) + 150;
        int top = (int) Math.floor(Math.random() * 100) + 5;
        data.put("top", top);
        try {
            //随机获取一张图片作为验证码背景图
            File randomImageFile = getRandomImage();
            //阴影图片
            File shapeImgFile = ResourceUtils.getFile("classpath:verify/shape.png");
            //读入图片
            BufferedImage srcImg = ImageIO.read(randomImageFile);
            BufferedImage shapeImg = ImageIO.read(shapeImgFile);
            //创建临时图片，并将阴影图片及验证码背景图片混合渲染
            BufferedImage bufImg = new BufferedImage(400, 150, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, 400, 150, null);
            g.drawImage(shapeImg, left, top, 40, 40, null);
            g.dispose();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            //随机压缩质量
            ImageDeal.compress(bufImg, byteArrayOutputStream, (float) (Math.random() / 5 + 0.3));
            //图片转base64编码
            Base64Tool base64Tool = new Base64Tool();
            base64Tool.setByteArrayOutputStream(byteArrayOutputStream);
            byteArrayOutputStream.close();
            //写入base64数据
            data.put("verifyBackground", base64Tool.encodeBase64());
            //生成滑块图片
            bufImg = new BufferedImage(40, 40, BufferedImage.TYPE_INT_RGB);
            g = bufImg.createGraphics();
            g.drawImage(srcImg, -left, -top, 400, 150, null);
            g.dispose();
            byteArrayOutputStream = new ByteArrayOutputStream();
            ImageDeal.compress(bufImg, byteArrayOutputStream, (float) (Math.random() / 5 + 0.3));
            base64Tool.setByteArrayOutputStream(byteArrayOutputStream);
            byteArrayOutputStream.close();
            data.put("verifySlider", base64Tool.encodeBase64());
            //无异常时，将数据返回
            session.setAttribute("verifyLeft", left);// 记录验证码图片左边
            //设置过期时间
            session.setMaxInactiveInterval(3 * 60);
            jsonMessage.setStatus(0);
            jsonMessage.setData(data);
        } catch (Exception e) {
            jsonMessage.setException(e);
        }
        return jsonMessage;
    }

    /**
     * @return 此方法获取resource/verify目录下以v_开头的图片，这些图片将被用作验证码背景图片
     * @throws FileNotFoundException 一般不抛出异常，除非项目没有相关目录或文件
     */
    private File getRandomImage() throws FileNotFoundException {
        //定义resource/verify目录下以v_开头的图片都是验证码背景图片
        File file = ResourceUtils.getFile("classpath:verify");
        File[] v_s = file.listFiles((dir, name) -> name.startsWith("v_"));
        Random random = new Random();
        assert v_s != null;
        return v_s[random.nextInt(v_s.length)];
    }
}
