package com.linjiahao.security.tools;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.IOException;
import java.io.OutputStream;

public class ImageDeal {
    /**
     * @param bufferedImage 指定图片
     * @param outputStream 将图片写到此outputStream
     * @param quality 图片质量，取值0~1
     * @throws IOException
     */
    public static void compress(BufferedImage bufferedImage, OutputStream outputStream, float quality) throws IOException {
        // 指定写图片的方式为 jpg
        ImageWriter imageWriter = ImageIO.getImageWritersByFormatName("jpg").next();
        JPEGImageWriteParam imageWriteParam = new JPEGImageWriteParam(null);
        // 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
        imageWriteParam.setCompressionMode(imageWriteParam.MODE_EXPLICIT);
        // 这里指定压缩的程度，参数qality是取值0~1范围内
        imageWriteParam.setCompressionQuality(quality);
        imageWriteParam.setProgressiveMode(imageWriteParam.MODE_DISABLED);
        ColorModel colorModel = bufferedImage.getColorModel();
        // 指定压缩时使用的色彩模式
        imageWriteParam.setDestinationType(new ImageTypeSpecifier(colorModel, colorModel.createCompatibleSampleModel(16, 16)));
        imageWriter.reset();
        // 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何OutputStream构造
        imageWriter.setOutput(ImageIO.createImageOutputStream(outputStream));
        imageWriter.write(null, new IIOImage(bufferedImage, null, null), imageWriteParam);
    }
}
