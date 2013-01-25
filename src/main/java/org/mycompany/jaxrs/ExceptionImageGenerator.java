package org.mycompany.jaxrs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author artyukhov
 */
public class ExceptionImageGenerator
{
    private static final int HIGH = 50;


    public byte[] createErrorPicture(String errorText)
    {
        try {
            BufferedImage errorIcon = ImageIO.read(getClass().getResourceAsStream("/pictures/error.jpg"));

            final int width = 8 * errorText.length() + errorIcon.getWidth() + 10;
            BufferedImage errorPicture = new BufferedImage(width, HIGH, BufferedImage.TYPE_INT_ARGB);
            Graphics g = errorPicture.getGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, HIGH);
            g.drawImage(errorIcon, 0, 0, null);
            g.setColor(Color.BLACK);
            g.drawChars(errorText.toCharArray(), 0, errorText.length(), errorIcon.getWidth() + 10, HIGH / 2);

            g.dispose();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(errorPicture, "gif", stream);
            return stream.toByteArray();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }
}