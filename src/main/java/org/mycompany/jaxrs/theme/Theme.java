package org.mycompany.jaxrs.theme;

import java.awt.Color;
import java.awt.image.BufferedImage;

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


    BufferedImage getFigureByCode(char imageCode);
}