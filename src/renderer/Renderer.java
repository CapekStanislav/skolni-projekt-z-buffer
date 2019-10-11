package renderer;

import model.Point;
import view.Raster;

import java.util.ArrayList;
import java.util.List;

public class Renderer {

    private final Raster raster;

    public Renderer(Raster raster) {
        this.raster = raster;
    }

    public void drawLine(int x1, int y1, int x2, int y2, int color) {
        float k = (y2 - y1) / (float) (x2 - x1);
        // TODO k = Infinity
        float q = y1 - k * x1;
        System.out.println(k);

        // TODO prohození podle X
        // teď funguje podle řídící osy X
        for (int x = x1; x <= x2; x++) {
            float y = k * x + q;
            raster.drawPixel(x, Math.round(y), color);
        }

        // TODO řídící osa Y
    }

    public void clear() {
        raster.clear();
    }

    public void drawPolygon(List<Point> points) {
        // TODO
    }

    public void drawRegularPolygon(Point center, int count, Point firstPoint) {
        List<Point> points = new ArrayList<>();
        points.add(firstPoint);
        float angle = 360f / count;
        // 2 * Math.PI / count
        double sin = Math.sin(Math.toRadians(angle));

        for (int i = 1; i < count; i++) {
            // posunout
            // otočit
            // posunout
            // points.add(...)
        }

        drawPolygon(points);
    }
}
