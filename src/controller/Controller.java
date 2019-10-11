package controller;

import model.Point;
import renderer.Renderer;
import view.Raster;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private final Renderer renderer;
    private List<Point> points;

    public Controller(Raster raster) {
        this.renderer = new Renderer(raster);
        points = new ArrayList<>();

        initListeners(raster);
    }

    private void initListeners(Raster raster) {
        raster.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                e.getButton() == MouseEvent.BUTTON3
//                e.isControlDown()
//                e.isShiftDown()
                if (e.getButton() == MouseEvent.BUTTON1) {
                    points.add(new Point(e.getX(), e.getY()));
                } else {
                    renderer.clear();
                    renderer.drawPolygon(points);
                    points.clear();
                }
            }
        });

        raster.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                renderer.clear();
                renderer.drawLine(
                        400, 300,
                        e.getX(), e.getY(),
                        Color.YELLOW.getRGB()
                );
            }
        });
    }
}
