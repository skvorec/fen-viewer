package org.mycompany.jaxrs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.mycompany.jaxrs.theme.Theme;

/**
 *
 * @author artyukhov
 */
public class PictureGenerator
{
    private Theme theme;


    public void setTheme(Theme theme)
    {
        this.theme = theme;
    }


    private BufferedImage drawEmptyChessBoard(int cellAmmount)
    {
        int cellSize = theme.getCellSize();
        int offset = theme.getOffsetFromCanvas();
        BufferedImage chessBoard = new BufferedImage(
                cellAmmount * cellSize + 2 * offset,
                cellAmmount * cellSize + 2 * offset,
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = chessBoard.getGraphics();
        //draw background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, cellAmmount * cellSize + 2 * offset, cellAmmount * cellSize + 2 * offset);
        //draw cells
        for (int i = 0; i < cellAmmount; i++) {
            for (int j = 0; j < cellAmmount; j++) {
                Color currentCellColor = ((i + j) % 2 == 0) ? theme.getWhiteCellColor() : theme.getBlackCellColor();
                g.setColor(currentCellColor);
                g.fillRect(offset + i * cellSize, offset + (cellAmmount - j - 1) * cellSize, cellSize, cellSize);
            }
        }
        //draw border
        g.setColor(theme.getBorderColor());
        g.drawRect(0, 0, cellAmmount * cellSize + 2 * offset - 1, cellAmmount * cellSize + 2 * offset - 1);
        //draw line numbers
        g.setColor(Color.BLACK);
        for (int i = 0; i < cellAmmount; i++) {
            String str = String.valueOf(i + 1);
            g.drawChars(str.toCharArray(), 0, str.length(),
                    offset / 2,
                    offset + (cellAmmount - i - 1) * cellSize + cellSize / 2);
        }
        //draw letters
        for (int i = 0; i < cellAmmount; i++) {
            g.drawChars(new char[]{(char) (i + 97)}, 0, 1,
                    offset + cellSize / 2 + i * cellSize,
                    offset + cellAmmount * cellSize + offset / 2);
        }
        g.dispose();
        return chessBoard;
    }


    private BufferedImage addFiguresToLine(BufferedImage board, int lineNumber, ChessFigure[] figures)
    {
        int cellAmmount = figures.length;
        int cellSize = theme.getCellSize();
        int offsetFromCanvas = theme.getOffsetFromCanvas();
        Graphics g = board.getGraphics();
        for (int i = 0; i < cellAmmount; i++) {
            if (figures[i] != null) {
                BufferedImage figureImage = theme.getFigureByCode(figures[i]);
                int offsetFromCellBorder = 0;
                g.drawImage(
                        figureImage,
                        offsetFromCanvas + i * cellSize + offsetFromCellBorder,
                        offsetFromCanvas + (cellAmmount - lineNumber) * cellSize + offsetFromCellBorder, null);
            }
        }
        g.dispose();
        return board;
    }


    public byte[] createBoard(ChessFigure[][] position)
    {
        int cellAmmount = position.length;
        BufferedImage board = drawEmptyChessBoard(cellAmmount);
        for (int i = 0; i < cellAmmount; i++) {
            ChessFigure[] figuresCode = position[i];
            addFiguresToLine(board, cellAmmount - i, figuresCode);
        }
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(board, "gif", stream);
            return stream.toByteArray();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
