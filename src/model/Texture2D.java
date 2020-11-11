package model;

import transforms.Col;

/**
 * An instance of interface {@code Texture2D}
 *
 * @author Stanislav Čapek
 * @version 1.0
 */
public interface Texture2D {

    /**
     * @param x specific pixel in width range
     * @param y specific pixel in height range
     * @return Color
     */
    Col getColor(int x, int y);

    int getWidth();

    int getHeight();
}
