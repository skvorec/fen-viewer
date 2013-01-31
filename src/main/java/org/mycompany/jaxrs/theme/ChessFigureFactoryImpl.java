package org.mycompany.jaxrs.theme;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author artyukhov
 */
public class ChessFigureFactoryImpl implements FigureFactory
{
    @Override
    public BufferedImage getImage(char fenCode)
    {
        try {
            BufferedImage image;

            switch (fenCode) {
                case 'p':
                    image = ImageIO.read(getClass().getResourceAsStream("/pictures/bp.svg"));
                    break;
                case 'r':
                    image = ImageIO.read(getClass().getResourceAsStream("/pictures/br.svg"));
                    break;
                case 'n':
                    image = ImageIO.read(getClass().getResourceAsStream("/pictures/bn.svg"));
                    break;
                case 'b':
                    image = ImageIO.read(getClass().getResourceAsStream("/pictures/bb.svg"));
                    break;
                case 'q':
                    image = ImageIO.read(getClass().getResourceAsStream("/pictures/bq.svg"));
                    break;
                case 'k':
                    image = ImageIO.read(getClass().getResourceAsStream("/pictures/bk.svg"));
                    break;
                case 'P':
                    image = ImageIO.read(getClass().getResourceAsStream("/pictures/wp.svg"));
                    break;
                case 'R':
                    image = ImageIO.read(getClass().getResourceAsStream("/pictures/wr.svg"));
                    break;
                case 'N':
                    image = ImageIO.read(getClass().getResourceAsStream("/pictures/wn.svg"));
                    break;
                case 'B':
                    image = ImageIO.read(getClass().getResourceAsStream("/pictures/wb.svg"));
                    break;
                case 'Q':
                    image = ImageIO.read(getClass().getResourceAsStream("/pictures/wq.svg"));
                    break;
                case 'K':
                    image = ImageIO.read(getClass().getResourceAsStream("/pictures/wk.svg"));
                    break;
                default:
                    throw new IllegalStateException("Incorrect fen code: " + fenCode);
            }

            return image;
        } catch (IOException exc) {
            throw new IllegalStateException(exc);
        }
    }
}