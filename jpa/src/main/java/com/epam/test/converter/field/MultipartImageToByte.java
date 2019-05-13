package com.epam.test.converter.field;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;

import org.springframework.web.multipart.MultipartFile;

public interface MultipartImageToByte {

    default byte[] multipartImageToByte(MultipartFile image) throws IOException {
        byte[] imageInByte = null;
        if (!image.isEmpty()) {
            JPEGImageWriteParam jpegParams = new JPEGImageWriteParam(null);
            jpegParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            jpegParams.setCompressionQuality(0.5f);
            ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
            BufferedImage src = fillTransparentPixels(image.getBytes());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
            writer.setOutput(ios);
            writer.write(null, new IIOImage(src, null, null), jpegParams);
            baos.flush();
            imageInByte = baos.toByteArray();
            ios.close();
            baos.close();
        }
        return imageInByte;
    }

    default BufferedImage fillTransparentPixels(byte[] imageBytes) throws IOException {
        Color fillColor = Color.WHITE;
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage outPutImage = new BufferedImage(w, h,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = outPutImage.createGraphics();
        g.setColor(fillColor);
        g.fillRect(0, 0, w, h);
        g.drawRenderedImage(image, null);
        g.dispose();
        return outPutImage;
    }
}
