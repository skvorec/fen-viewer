package org.mycompany.jaxrs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.mycompany.jaxrs.theme.Theme;

/**
 *
 * @author artyukhov
 */
public class PictureGenerator
{
    private final int CELL_AMMOUNT = 8;
    private Theme theme;


    public void setTheme(Theme theme)
    {
        this.theme = theme;
    }


    private BufferedImage drawEmptyChessBoard()
    {
        int cellSize = theme.getCellSize();
        int offset = theme.getOffsetFromCanvas();
        BufferedImage chessBoard = new BufferedImage(
                CELL_AMMOUNT * cellSize + 2 * offset,
                CELL_AMMOUNT * cellSize + 2 * offset,
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = chessBoard.getGraphics();
        //draw cells
        for (int i = 0; i < CELL_AMMOUNT; i++) {
            for (int j = 0; j < CELL_AMMOUNT; j++) {
                Color currentCellColor = ((i + j) % 2 == 0) ? theme.getWhiteCellColor() : theme.getBlackCellColor();
                g.setColor(currentCellColor);
                g.fillRect(offset + i * cellSize, offset + (CELL_AMMOUNT - j - 1) * cellSize, cellSize, cellSize);
            }
        }
        //draw border
        g.setColor(theme.getBlackCellColor());
        g.drawRect(0, 0, CELL_AMMOUNT * cellSize + 2 * offset - 1, CELL_AMMOUNT * cellSize + 2 * offset - 1);
        //draw line numbers
        g.setColor(theme.getWhiteCellColor());
        for (int i = 0; i < CELL_AMMOUNT; i++) {
            String str = String.valueOf(i + 1);
            g.drawChars(str.toCharArray(), 0, str.length(),
                    offset / 2,
                    offset + (CELL_AMMOUNT - i - 1) * cellSize + cellSize / 2);
        }
        //draw letters
        for (int i = 0; i < CELL_AMMOUNT; i++) {
            g.drawChars(new char[]{(char) (i + 65)}, 0, 1,
                    offset + cellSize / 2 + i * cellSize,
                    offset + CELL_AMMOUNT * cellSize + offset / 2);
        }
        g.dispose();
        return chessBoard;
    }


    private BufferedImage addFiguresToLine(BufferedImage board, int lineNumber, Character[] figures)
    {
        int cellSize = theme.getCellSize();
        int offsetFromCanvas = theme.getOffsetFromCanvas();
        Graphics g = board.getGraphics();
        for (int i = 0; i < figures.length; i++) {
            if (figures[i] != null) {
                BufferedImage figureImage = theme.getFigureByCode(figures[i]);
                int offsetFromCellBorder = 0;
                g.drawImage(
                        figureImage,
                        offsetFromCanvas + i * cellSize + offsetFromCellBorder,
                        offsetFromCanvas + (CELL_AMMOUNT - lineNumber) * cellSize + offsetFromCellBorder, null);
            }
        }
        g.dispose();
        return board;
    }


    private int getInt(List<Character> numberBuffer)
    {
        StringBuilder builder = new StringBuilder();
        for (Character c : numberBuffer) {
            builder.append(c);
        }
        return Integer.parseInt(builder.toString());
    }


    protected Character[] getCharArray(String rowInFen)
    {
        List<Character> result = new ArrayList<Character>();
        List<Character> numberBuffer = new ArrayList<Character>();

        for (int i = 0; i < rowInFen.length(); i++) {
            Character current = rowInFen.charAt(i);
            if (Character.isDigit(current)) {
                numberBuffer.add(current);
            }
            else {
                if (!numberBuffer.isEmpty()) {
                    int numberOfEmptyCells = getInt(numberBuffer);
                    numberBuffer = new ArrayList<Character>();
                    for (int j = 0; j < numberOfEmptyCells; j++) {
                        result.add(null);
                    }
                }
                result.add(current);
            }
        }
        //if ends with number
        if (!numberBuffer.isEmpty()) {
            int numberOfEmptyCells = getInt(numberBuffer);
            for (int j = 0; j < numberOfEmptyCells; j++) {
                result.add(null);
            }
        }
        return result.toArray(new Character[result.size()]);
    }


    public byte[] createBoard(String fen)
    {
        if (fen == null) {
            throw new IllegalStateException("Please, write FEN in \'fen\' query parameter!");
        }
        String[] rows = fen.split("/");
        if (rows.length != CELL_AMMOUNT) {
            throw new IllegalStateException("You need to describe not " + rows.length
                    + " but " + CELL_AMMOUNT + " lines exactly!");
        }
        BufferedImage board = drawEmptyChessBoard();
        for (int i = 0; i < rows.length; i++) {
            Character[] figuresCode = getCharArray(rows[i]);
            if (figuresCode.length != CELL_AMMOUNT) {
                throw new IllegalStateException("You need to describe not " + figuresCode.length
                        + " but " + CELL_AMMOUNT + " cells in line " + (CELL_AMMOUNT - i) + "(" + rows[i] + ")" + "!");
            }
            addFiguresToLine(board, CELL_AMMOUNT - i, figuresCode);
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
