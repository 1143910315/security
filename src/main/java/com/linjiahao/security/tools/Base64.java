package com.linjiahao.security.tools;

import org.thymeleaf.util.StringUtils;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class Base64 {
    public enum Type {
        ImageType;
    }

    //当不为null时，即为用户最后设置。如若用户设置了其他项目，需要将此值清空
    private byte[] bytes = null;

    public Base64() {
    }

    public Base64 setBytes(byte[] bytes) {
        this.bytes = bytes;
        return this;
    }

    public Base64 setByteArrayOutputStream(ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        bytes = byteArrayOutputStream.toByteArray();
        return this;
    }

    public Base64 setBufferedImage(BufferedImage bufferedImage, String type) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, type, bos);
        setByteArrayOutputStream(bos);
        bos.close();
        return this;
    }

    public Base64 setFile(File file, Type type) throws IOException {
        String path = file.getAbsolutePath();
        switch (type) {
            case ImageType:
                String t = StringUtils.substring(path, path.lastIndexOf(".") + 1);
                BufferedImage image = ImageIO.read(file);
                setBufferedImage(image, t);
                break;
        }
        return this;
    }

    private byte[] getByte() throws Exception {
        if (bytes == null) {
            throw new Exception("找不到需要操作的数据源。");
        } else {
            return bytes;
        }
    }

    public String encodeBase64() throws Exception {
        byte[] imageBytes = getByte();
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(imageBytes);
    }

}
