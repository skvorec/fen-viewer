package org.mycompany.jaxrs;

/**
 *
 * @author artyukhov
 */
public enum ChessFigure
{
    BR, BN, BB, BK, BQ, BP,
    WR, WN, WB, WK, WQ, WP;


    public static ChessFigure byFenCode(Character c)
    {
        ChessFigure result = null;
        switch (c) {
            case 'p':
                result = BP;
                break;
            case 'r':
                result = BR;
                break;
            case 'n':
                result = BN;
                break;
            case 'b':
                result = BB;
                break;
            case 'q':
                result = BQ;
                break;
            case 'k':
                result = BK;
                break;
            case 'P':
                result = WP;
                break;
            case 'R':
                result = WR;
                break;
            case 'N':
                result = WN;
                break;
            case 'B':
                result = WB;
                break;
            case 'Q':
                result = WQ;
                break;
            case 'K':
                result = WK;
                break;
            default:
        }
        return result;
    }
}
