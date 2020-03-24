package model;

import model.transformation.TransformState;
import model.transformation.Transformable;
import model.transformation.TransformableState;
import transforms.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Instance of class {@code SurfaceBiCubic}
 *
 * @author Stanislav Čapek
 * @version 1.0
 */
public class SurfaceBiCubic extends Solid implements Transformable {
    private final TransformableState state = new TransformState();
    private final Col color;

    /**
     * Create surface
     *
     * @param cubic         type
     * @param numPoints     number of points in one row
     * @param color         color of surface
     * @param controlPoints 16 points
     */
    public SurfaceBiCubic(Mat4 cubic, int numPoints, Color color, Point3D... controlPoints) {
        this.color = new Col(color.getRGB());

        final Point3D[] points;
        if (controlPoints.length == 0) {
            List<Point3D> contrPoints = new ArrayList<>();
            double z = 0d;
            // controll points
            for (int y = 0; y <= 3; y++) {
                for (int x = 0; x <= 3; x++) {
                    contrPoints.add(new Point3D(x, y, z));
                }
            }
            points = contrPoints.toArray(Point3D[]::new);
        } else {
            points = controlPoints;
        }

        final Bicubic bicubic = new Bicubic(cubic, points);
        calulateSurface(bicubic, numPoints);

    }

    /**
     * Create flat surface
     *
     * @param cubic     type
     * @param numPoints number of points in one row
     * @param color     color of surface
     */
    public SurfaceBiCubic(Mat4 cubic, int numPoints, Color color) {
        this(cubic, numPoints, color, new Point3D[0]);
    }

    /**
     * Create flat surface with default color (blue)
     *
     * @param cubic     type
     * @param numPoints number of points in one row
     */
    public SurfaceBiCubic(Mat4 cubic, int numPoints) {
        this(cubic, numPoints, Color.BLUE);
    }

    /**
     * Create flat surface with default color and default
     * detail.
     *
     * @param cubic type
     */
    public SurfaceBiCubic(Mat4 cubic) {
        this(cubic, 5);
    }

    private void calulateSurface(Bicubic bicubic, int numPoints) {
        final double ration = 1d / (numPoints - 1);
        for (double u = 0; u <= 1; u += ration) {
            for (double v = 0; v <= 1; v += ration) {
                addVertices(new Vertex(
                        bicubic.compute(u, v), color
                ));
            }
        }
        connectVertices();
    }

    private void connectVertices() {
        final int numPoints = (int) Math.sqrt(vertexBuffer.size());
        for (int i = 0; i < numPoints - 1; i++) {
            this.parts.add(
                    new Part(Topology.TRIANGLE_STRIP,
                            i * numPoints * 2,
                            2 * numPoints - 2
                    )
            );
        }

        for (int i = 0; i < parts.size(); i++) {
            final int shift = i * numPoints;
            for (int j = 0; j < numPoints; j++) {
                addIndices(
                        j + shift,
                        j + numPoints + shift
                );
            }
        }
    }

    @Override
    public List<Vertex> getVertices() {
        return vertexBuffer;
    }

    @Override
    public TransformableState getTransformableState() {
        return state;
    }

}
