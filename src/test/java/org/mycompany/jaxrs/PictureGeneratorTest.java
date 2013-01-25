package org.mycompany.jaxrs;

import org.junit.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author artyukhov
 */
public class PictureGeneratorTest
{
    @Test
    public void getCharArray()
    {
        PictureGenerator generator = new PictureGenerator();
        Character[] result = generator.getCharArray("r1q2Q3");
        Assert.assertArrayEquals(result, new Character[]{'r', null, 'q', null, null, 'Q', null, null, null});
        result = generator.getCharArray("r10q1");
        Assert.assertArrayEquals(result, new Character[]{
                    'r', null, null, null, null, null, null, null, null, null, null, 'q', null});
        result = generator.getCharArray("2rqK");
        Assert.assertArrayEquals(result, new Character[]{null, null, 'r', 'q', 'K'});
    }
}