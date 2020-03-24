package model;

import model.transformation.TransformState;
import model.transformation.Transformable;
import model.transformation.TransformableState;
import transforms.Col;
import transforms.Cubic;
import transforms.Point3D;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Instance of class {@code Sector}
 *
 * @author Stanislav Čapek
 * @version 1.0
 */
public class Sector extends Solid implements Transformable {

    private TransformableState state = new TransformState();
    private Col color = new Col(Color.RED.getRGB());

    /**
     * Create sector with default detail
     */
    public Sector() {
        this(10);
    }

    /**
     * Create sector
     *
     * @param detail detail
     */
    public Sector(int detail) {
        if (detail < 1) {
            throw new IllegalArgumentException("Detail have to be larger then 0");
        }

        List<Vertex> e1 = new ArrayList<>();
        List<Vertex> e2 = new ArrayList<>();
        List<Vertex> e3 = new ArrayList<>();


        final int numPoints = 4;
        for (int i = 0; i < numPoints; i++) {
            e1.add(
                    new Vertex(new Point3D(3, i, 0), color)
            );
            e2.add(
                    new Vertex(new Point3D(0, i, 0), color)
            );
            e3.add(
                    new Vertex(new Point3D(0, i, 3), color)
            );
        }

        addVertices(e3.toArray(Vertex[]::new));
        final int count = numPoints * 2 - 2;
        parts.add(
                new Part(
                        Topology.TRIANGLE_STRIP,
                        getParts().size() * numPoints * 2,
                        count
                )
        );

        addVertices(e2.toArray(Vertex[]::new));
        parts.add(
                new Part(
                        Topology.TRIANGLE_STRIP,
                        getParts().size() * numPoints * 2,
                        count
                )
        );

        for (int i = 0; i < getVertices().size(); i++) {
            addIndices(i, i + numPoints);
        }

        addVertices(e1.toArray(Vertex[]::new));

        final List<Point3D> controlPoints = createControlPoints();
        final SurfaceBiCubic surface = new SurfaceBiCubic(
                Cubic.BEZIER, detail, Color.GREEN, controlPoints.toArray(Point3D[]::new)
        );

        for (int i = 0; i < surface.getParts().size(); i++) {
            final Part part = surface.getParts().get(i);
            this.parts.add(
                    new Part(part.getType(),
                            getIndexBuffer().size() + (part.getCount() + 2) * i,
                            part.getCount())
            );
        }

        final int vbSize = getVertices().size();
        surface.getIndexBuffer().forEach(integer -> addIndices(vbSize + integer));
        vertexBuffer.addAll(surface.vertexBuffer);
    }

    private List<Point3D> createControlPoints() {
        List<Point3D> cp = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            cp.add(new Point3D(0, i, 3));
            cp.add(new Point3D(2, i, 3));
            cp.add(new Point3D(3, i, 2));
            cp.add(new Point3D(3, i, 0));
        }
        return cp;
    }

    @Override
    public List<Vertex> getVertices() {
        return this.vertexBuffer;
    }

    @Override
    public TransformableState getTransformableState() {
        return state;
    }

}
