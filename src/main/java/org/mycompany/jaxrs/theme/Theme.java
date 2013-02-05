package org.mycompany.jaxrs.theme;

import java.awt.Color;
import java.awt.image.BufferedImage;
import org.mycompany.jaxrs.ChessFigure;

/**
 *
 * @author artyukhov
 */
public interface Theme
{
    int getCellSize();


    int getOffsetFromCanvas();


    Color getBlackCellColor();


    Color getWhiteCellColor();


    Color getBorderColor();


    BufferedImage getFigureByCode(ChessFigure imageCode);
}