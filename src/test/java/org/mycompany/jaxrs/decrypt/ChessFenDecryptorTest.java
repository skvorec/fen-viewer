package org.mycompany.jaxrs.decrypt;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.mycompany.jaxrs.ChessFigure;
import static org.mycompany.jaxrs.ChessFigure.*;
import org.testng.annotations.Test;

/**
 *
 * @author artyukhov
 */
public class ChessFenDecryptorTest
{
    @Test
    public void getCharArray()
    {
        ChessFenDecryptor decryptor = new ChessFenDecryptor();
        ChessFigure[] result = decryptor.getFigureArray("r1q2Q3");
        assertThat(result, arrayContaining(BR, null, BQ, null, null, WQ, null, null, null));
        assertThat(result, arrayWithSize(9));
        result = decryptor.getFigureArray("r10q1");
        assertThat(result, arrayContaining(BR, null, null, null, null, null, null, null, null, null, null, BQ, null));
        assertThat(result, arrayWithSize(13));
        result = decryptor.getFigureArray("2rqK");
        assertThat(result, arrayContaining(null, null, BR, BQ, WK));
        assertThat(result, arrayWithSize(5));
        result = decryptor.getFigureArray("8");
        assertThat(result, arrayWithSize(8));
    }


    @Test
    public void decryptFen()
    {
        ChessFenDecryptor decryptor = new ChessFenDecryptor();
        ChessFigure[][] position = decryptor.decryptFen("rnbkqbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBKQBNR");
        assertThat(position[0], arrayContaining(BR, BN, BB, BK, BQ, BB, BN, BR));
        assertThat(position[1], arrayContaining(BP, BP, BP, BP, BP, BP, BP, BP));
        assertArrayHasOnlyNulls(position[2], 8);
        assertArrayHasOnlyNulls(position[3], 8);
        assertArrayHasOnlyNulls(position[4], 8);
        assertArrayHasOnlyNulls(position[5], 8);
        assertThat(position[6], arrayContaining(WP, WP, WP, WP, WP, WP, WP, WP));
        assertThat(position[7], arrayContaining(WR, WN, WB, WK, WQ, WB, WN, WR));
    }


    private void assertArrayHasOnlyNulls(Object[] array, int size)
    {
        assertThat(array.length, equalTo(size));
        for (int i = 0; i < array.length; i++) {
            assertThat(array[i], nullValue());
        }
    }


    @Test(expectedExceptions = {IllegalStateException.class})
    public void decryptFenNotEightRows()
    {
        ChessFenDecryptor decryptor = new ChessFenDecryptor();
        decryptor.decryptFen("rnbkqbnr/pppppppp/8/8/8/8/PPPPPPPP/");
    }


    @Test(expectedExceptions = {IllegalStateException.class})
    public void decryptFenNotEightCells()
    {
        ChessFenDecryptor decryptor = new ChessFenDecryptor();
        decryptor.decryptFen("rnbkqbnr/pppppppp/8/8/8/8/PPPPPPPP/RNB");
    }
}