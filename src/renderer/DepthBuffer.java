package renderer;

import java.util.Arrays;

/**
 * Instance of class {@code DepthBuffer}
 *
 * @author Stanislav Čapek
 * @version 1.0
 */
public class DepthBuffer<T extends Number> {

    private T[][] data;

    public DepthBuffer(T[][] data) {
        this.data = data;
    }

    public T get(int x, int y) {
        return data[x][y];
    }

    public void set(int x, int y, T z) {
        data[x][y] = z;
    }

    public void clear(T clearValue) {
        for (T[] d : data) {
            Arrays.fill(d, clearValue);
        }
    }
}
