package org.mycompany.jaxrs.theme;

import java.awt.Color;
import java.awt.image.BufferedImage;
import org.mycompany.jaxrs.ChessFigure;

/**
 *
 * @author artyukhov
 */
public class ThemeImpl implements Theme
{
    private final Color blackCellColor;
    private final Color whiteCellColor;
    private final Color borderColor;
    private int cellSize;
    private int offsetFromCanvas;
    private FigureFactory figureFactory;


    public ThemeImpl(Color blackCellColor, Color whiteCellColor, Color borderColor)
    {
        this.blackCellColor = blackCellColor;
        this.whiteCellColor = whiteCellColor;
        this.borderColor = borderColor;
    }


    @Override
    public int getCellSize()
    {
        return cellSize;
    }


    @Override
    public int getOffsetFromCanvas()
    {
        return offsetFromCanvas;
    }


    @Override
    public Color getBlackCellColor()
    {
        return blackCellColor;
    }


    @Override
    public Color getWhiteCellColor()
    {
        return whiteCellColor;
    }


    @Override
    public Color getBorderColor()
    {
        return borderColor;
    }


    public void setCellSize(int cellSize)
    {
        this.cellSize = cellSize;
    }


    public void setOffsetFromCanvas(int offsetFromCanvas)
    {
        this.offsetFromCanvas = offsetFromCanvas;
    }


    public void setFigureFactory(FigureFactory figureFactory)
    {
        this.figureFactory = figureFactory;
    }


    @Override
    public BufferedImage getFigureByCode(ChessFigure imageCode)
    {
        return figureFactory.getImage(imageCode);
    }
}