/*
 * Tn3270paint.java:
 *
 * (c) 1995 Jean-Marc Naef, Geneva, Switzerland
 *
 * E-mail: Jean-Marc.Naef@seinf.unige.ch
 */

package tn3270;

import	java.io.IOException;

public interface Tn3270paint {

    /**
     * @param <i>x</i> columns position
     * @param <i>y</i> rows position
     * @param <i>c</i> character under the cursor
     * @param <i>color</i> color of the current caracter
     * @param <i>on</i> erase/paint the cursor
     */
    void paintCursor(int x, int y, byte c, int color, boolean on);

    /**
     * @param <i>x</i> string painting columns position
     * @param <i>y</i> string painting rows position
     * @param <i>color</i>color of the string
     * @param <i>buf</i>the byte array to be painted
     * @param <i>len</i>length of the array
     */
    void paintString(int x, int y, int color, byte buf[], int len);

    /**
     * @param <i>str</i> status string
     */
    void paintStatus(String str);
}
