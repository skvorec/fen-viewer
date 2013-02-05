package org.mycompany.jaxrs.theme;

import java.awt.image.BufferedImage;
import org.mycompany.jaxrs.ChessFigure;

/**
 *
 * @author artyukhov
 */
public interface FigureFactory 
{
    BufferedImage getImage(ChessFigure imageCode);
}