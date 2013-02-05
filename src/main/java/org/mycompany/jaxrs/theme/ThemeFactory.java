package org.mycompany.jaxrs.theme;

import java.awt.Color;

/**
 *
 * @author artyukhov
 */
public class ThemeFactory
{
    private ThemeImpl getCellColoredTheme(String themeId)
    {
        if (themeId.equalsIgnoreCase("www.chess-online.ru")) {
            return new ThemeImpl(new Color(136, 136, 136), new Color(255, 255, 255), new Color(136, 136, 136));
        }
        return new ThemeImpl(new Color(209, 139, 71), new Color(255, 206, 158), new Color(176, 176, 176));
    }


    public Theme createTheme(String themeId)
    {
        if (themeId == null) {
            themeId = "null_theme";
        }
        ThemeImpl result = getCellColoredTheme(themeId);

        if (themeId.equalsIgnoreCase("wikiBig")) {
            result.setCellSize(50);
            result.setOffsetFromCanvas(25);
            result.setFigureFactory(new ChessFigureFactoryImpl("wikiBig"));
            return result;
        }

        //default figures and sizes
        result.setCellSize(35);
        result.setOffsetFromCanvas(20);
        result.setFigureFactory(new ChessFigureFactoryImpl("wiki"));
        return result;
    }
}