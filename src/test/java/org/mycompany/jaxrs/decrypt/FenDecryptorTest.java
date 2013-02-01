package org.mycompany.jaxrs.decrypt;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

/**
 *
 * @author artyukhov
 */
public class FenDecryptorTest
{
    @Test
    public void getCharArray()
    {
        FenDecryptor decryptor = new FenDecryptor();
        Character[] result = decryptor.getCharArray("r1q2Q3");
        assertThat(result, arrayContaining('r', null, 'q', null, null, 'Q', null, null, null));
        assertThat(result, arrayWithSize(9));
        result = decryptor.getCharArray("r10q1");
        assertThat(result, arrayContaining('r', null, null, null, null, null, null, null, null, null, null, 'q', null));
        assertThat(result, arrayWithSize(13));
        result = decryptor.getCharArray("2rqK");
        assertThat(result, arrayContaining(null, null, 'r', 'q', 'K'));
        assertThat(result, arrayWithSize(5));
        result = decryptor.getCharArray("8");
        assertThat(result, arrayWithSize(8));
    }


    @Test
    public void decryptFen()
    {
        FenDecryptor decryptor = new FenDecryptor();
        Character[][] position = decryptor.decryptFen("rnbkqbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBKQBNR");
        assertThat(position[0], arrayContaining('r', 'n', 'b', 'k', 'q', 'b', 'n', 'r'));
        assertThat(position[1], arrayContaining('p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'));
        assertArrayHasOnlyNulls(position[2], 8);
        assertArrayHasOnlyNulls(position[3], 8);
        assertArrayHasOnlyNulls(position[4], 8);
        assertArrayHasOnlyNulls(position[5], 8);
        assertThat(position[6], arrayContaining('P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'));
        assertThat(position[7], arrayContaining('R', 'N', 'B', 'K', 'Q', 'B', 'N', 'R'));
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
        FenDecryptor decryptor = new FenDecryptor();
        decryptor.decryptFen("rnbkqbnr/pppppppp/8/8/8/8/PPPPPPPP/");
    }


    @Test(expectedExceptions = {IllegalStateException.class})
    public void decryptFenNotEightCells()
    {
        FenDecryptor decryptor = new FenDecryptor();
        decryptor.decryptFen("rnbkqbnr/pppppppp/8/8/8/8/PPPPPPPP/RNB");
    }
}