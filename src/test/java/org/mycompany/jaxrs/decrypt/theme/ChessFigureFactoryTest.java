package org.mycompany.jaxrs.decrypt.theme;

import org.mycompany.jaxrs.ChessFigure;
import org.mycompany.jaxrs.theme.ChessFigureFactoryImpl;
import org.testng.annotations.Test;

/**
 *
 * @author artyukhov
 */
public class ChessFigureFactoryTest
{
    @Test
    public void getImage()
    {
        ChessFigureFactoryImpl factory = new ChessFigureFactoryImpl("wiki");
        for (ChessFigure figure : ChessFigure.values()) {
            factory.getImage(figure); //assert no exceptions throw
        }

        factory = new ChessFigureFactoryImpl("wikiBig");
        for (ChessFigure figure : ChessFigure.values()) {
            factory.getImage(figure); //assert no exceptions throw
        }
    }
}