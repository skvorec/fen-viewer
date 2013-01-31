package org.mycompany.jaxrs.theme;

import java.awt.image.BufferedImage;

/**
 *
 * @author artyukhov
 */
public interface FigureFactory 
{
    BufferedImage getImage(char imageCode);
}