package org.mycompany.jaxrs.decrypt;

import java.util.ArrayList;
import java.util.List;
import org.mycompany.jaxrs.ChessFigure;

/**
 *
 * @author artyukhov
 */
public class ChessFenDecryptor
{
    private final int CELL_AMMOUNT = 8;


    public ChessFigure[][] decryptFen(String fen)
    {
        ChessFigure[][] result = new ChessFigure[CELL_AMMOUNT][CELL_AMMOUNT];
        String[] rows = fen.split("/");
        if (rows.length != CELL_AMMOUNT) {
            throw new IllegalStateException("You need to describe not " + rows.length
                    + " but " + CELL_AMMOUNT + " lines exactly!");
        }

        for (int i = 0; i < rows.length; i++) {
            ChessFigure[] figuresCode = getFigureArray(rows[i]);
            if (figuresCode.length != CELL_AMMOUNT) {
                throw new IllegalStateException("You need to describe not " + figuresCode.length
                        + " but " + CELL_AMMOUNT + " cells in line " + (CELL_AMMOUNT - i) + "(" + rows[i] + ")" + "!");
            }
            result[i] = figuresCode;
        }


        return result;
    }


    private int getInt(List<Character> numberBuffer)
    {
        StringBuilder builder = new StringBuilder();
        for (Character c : numberBuffer) {
            builder.append(c);
        }
        return Integer.parseInt(builder.toString());
    }


    protected ChessFigure[] getFigureArray(String rowInFen)
    {
        List<ChessFigure> result = new ArrayList<ChessFigure>();
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
                result.add(ChessFigure.byFenCode(current));
            }
        }
        //if ends with number
        if (!numberBuffer.isEmpty()) {
            int numberOfEmptyCells = getInt(numberBuffer);
            for (int j = 0; j < numberOfEmptyCells; j++) {
                result.add(null);
            }
        }
        return result.toArray(new ChessFigure[result.size()]);
    }
}