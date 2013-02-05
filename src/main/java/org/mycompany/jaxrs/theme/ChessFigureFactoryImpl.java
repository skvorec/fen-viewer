package org.mycompany.jaxrs.theme;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.mycompany.jaxrs.ChessFigure;
import static org.mycompany.jaxrs.ChessFigure.*;

/**
 *
 * @author artyukhov
 */
public class ChessFigureFactoryImpl implements FigureFactory
{
    private final String folderPrefix;


    public ChessFigureFactoryImpl(String folderPrefix)
    {
        this.folderPrefix = folderPrefix;
    }


    @Override
    public BufferedImage getImage(ChessFigure figure)
    {
        try {
            BufferedImage image;

            switch (figure) {
                case BP:
                    image = ImageIO.read(getClass().getResourceAsStream("/pictures/chess/" + folderPrefix + "/bp.png"));
                    break;
                case BR:
                    image = ImageIO.read(getClass().getResourceAsStream("/pictures/chess/" + folderPrefix + "/br.png"));
                    break;
                case BN:
                    image = ImageIO.read(getClass().getResourceAsStream("/pictures/chess/" + folderPrefix + "/bn.png"));
                    break;
                case BB:
                    image = ImageIO.read(getClass().getResourceAsStream("/pictures/chess/" + folderPrefix + "/bb.png"));
                    break;
                case BQ:
                    image = ImageIO.read(getClass().getResourceAsStream("/pictures/chess/" + folderPrefix + "/bq.png"));
                    break;
                case BK:
                    image = ImageIO.read(getClass().getResourceAsStream("/pictures/chess/" + folderPrefix + "/bk.png"));
                    break;
                case WP:
                    image = ImageIO.read(getClass().getResourceAsStream("/pictures/chess/" + folderPrefix + "/wp.png"));
                    break;
                case WR:
                    image = ImageIO.read(getClass().getResourceAsStream("/pictures/chess/" + folderPrefix + "/wr.png"));
                    break;
                case WN:
                    image = ImageIO.read(getClass().getResourceAsStream("/pictures/chess/" + folderPrefix + "/wn.png"));
                    break;
                case WB:
                    image = ImageIO.read(getClass().getResourceAsStream("/pictures/chess/" + folderPrefix + "/wb.png"));
                    break;
                case WQ:
                    image = ImageIO.read(getClass().getResourceAsStream("/pictures/chess/" + folderPrefix + "/wq.png"));
                    break;
                case WK:
                    image = ImageIO.read(getClass().getResourceAsStream("/pictures/chess/" + folderPrefix + "/wk.png"));
                    break;
                default:
                    throw new IllegalStateException("Incorrect fen code: " + figure);
            }

            return image;
        } catch (IOException exc) {
            throw new IllegalStateException(exc);
        }
    }
}