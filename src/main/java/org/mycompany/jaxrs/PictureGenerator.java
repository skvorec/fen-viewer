package org.mycompany.jaxrs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author artyukhov
 */
public class PictureGenerator
{
    private final static int CELL_SIZE = 50;
    private final static int OFFSET = 25;
    private final int CELL_AMMOUNT = 8;
    private final static Color BLACK_COLOR = Color.DARK_GRAY;
    private final static Color WHITE_COLOR = Color.WHITE;


    private BufferedImage drawEmptyChessBoard()
    {

        BufferedImage chessBoard = new BufferedImage(
                CELL_AMMOUNT * CELL_SIZE + 2 * OFFSET,
                CELL_AMMOUNT * CELL_SIZE + 2 * OFFSET,
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = chessBoard.getGraphics();
        //draw cells
        for (int i = 0; i < CELL_AMMOUNT; i++) {
            for (int j = 0; j < CELL_AMMOUNT; j++) {
                Color currentCellColor = ((i + j) % 2 == 0) ? WHITE_COLOR : BLACK_COLOR;
                g.setColor(currentCellColor);
                g.fillRect(OFFSET + i * CELL_SIZE, OFFSET + (CELL_AMMOUNT - j - 1) * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
        //draw border
        g.setColor(BLACK_COLOR);
        g.drawRect(0, 0, CELL_AMMOUNT * CELL_SIZE + 2 * OFFSET - 1, CELL_AMMOUNT * CELL_SIZE + 2 * OFFSET - 1);
        //draw line numbers
        g.setColor(WHITE_COLOR);
        for (int i = 0; i < CELL_AMMOUNT; i++) {
            String str = String.valueOf(i + 1);
            g.drawChars(str.toCharArray(), 0, str.length(),
                    OFFSET / 2,
                    OFFSET + (CELL_AMMOUNT - i - 1) * CELL_SIZE + CELL_SIZE / 2);
        }
        //draw letters
        for (int i = 0; i < CELL_AMMOUNT; i++) {
            g.drawChars(new char[]{(char) (i + 65)}, 0, 1,
                    OFFSET + CELL_SIZE / 2 + i * CELL_SIZE,
                    OFFSET + CELL_AMMOUNT * CELL_SIZE + OFFSET / 2);
        }
        g.dispose();
        return chessBoard;
    }


    private BufferedImage addFiguresToLine(BufferedImage board, int lineNumber, Character[] figures)
    {
        FigureImageFactory factory = new FigureImageFactory();
        Graphics g = board.getGraphics();
        for (int i = 0; i < figures.length; i++) {
            if (figures[i] != null) {
                BufferedImage figureImage = factory.getImage(figures[i]);
                int offsetFromCellBorder = 0;
                g.drawImage(
                        figureImage,
                        OFFSET + i * CELL_SIZE + offsetFromCellBorder,
                        OFFSET + (CELL_AMMOUNT - lineNumber) * CELL_SIZE + offsetFromCellBorder, null);
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
