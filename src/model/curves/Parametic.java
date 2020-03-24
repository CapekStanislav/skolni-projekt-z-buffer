package model.curves;

import model.Part;
import model.Solid;
import model.Topology;
import model.Vertex;
import transforms.Col;
import transforms.Point3D;

import java.awt.Color;
import java.util.function.DoubleFunction;

/**
 * Instance of class {@code Parametic}
 *
 * @author Stanislav Čapek
 * @version 1.0
 */
public class Parametic extends Solid {
    private final double detail = 0.1;
    private Col color = new Col(Color.BLACK.getRGB());

    /**
     * Parametric curve x squared and fixed y axis
     *
     * @param min on x axis
     * @param max on x axis
     */
    public Parametic(double min, double max) {
        this(min, max, v -> 0d);
    }

    /**
     * Parametric curve with x squared
     *
     * @param min   on x axis
     * @param max   on x axis
     * @param funcY function for y axis
     */
    public Parametic(double min, double max, DoubleFunction<Double> funcY) {
        this(min, max, funcY, v -> Math.pow(v, 2));
    }

    /**
     * Parametric curve
     *
     * @param min   on x axis
     * @param max   on x axis
     * @param funcY function for y axis
     * @param funcZ function for z axis
     */
    public Parametic(double min, double max,
                     DoubleFunction<Double> funcY,
                     DoubleFunction<Double> funcZ) {

        for (double x = min; x < max; x += detail) {
            addIndices(getVertexBuffer().size());
            addVertices(
                    new Vertex(
                            new Point3D(x, funcY.apply(x), funcZ.apply(x)),
                            color
                    )
            );
        }
        this.parts.add(
                new Part(Topology.LINE_STRIP, 0, getVertexBuffer().size() - 1)
        );
    }
}
