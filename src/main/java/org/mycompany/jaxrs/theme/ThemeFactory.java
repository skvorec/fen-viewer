package org.mycompany.jaxrs.theme;

import java.awt.Color;

/**
 *
 * @author artyukhov
 */
public class ThemeFactory
{
    private ThemeImpl getColoredTheme(String themeId)
    {
        return new ThemeImpl(Color.DARK_GRAY, Color.WHITE);
    }


    public Theme createTheme(String themeId)
    {
        ThemeImpl result = getColoredTheme(themeId);
        result.setCellSize(50);
        result.setOffsetFromCanvas(25);
        result.setFigureFactory(new ChessFigureFactoryImpl());

        return result;
    }
}