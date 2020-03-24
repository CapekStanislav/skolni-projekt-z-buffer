package model;

import model.transformation.TransformState;
import model.transformation.Transformable;
import model.transformation.TransformableState;
import transforms.Col;
import transforms.Point3D;

import java.awt.Color;
import java.util.List;

/**
 * Instance of class {@code Pyramid}
 *
 * @author Stanislav Čapek
 * @version 1.0
 */
public class Pyramid extends Solid implements Transformable {

    private final TransformableState state = new TransformState();

    /**
     * Create pyramid with 5 colors for each side
     *
     * @param col1 base
     * @param col2 side
     * @param col3 side
     * @param col4 side
     * @param col5 side
     */
    public Pyramid(Color col1, Color col2, Color col3, Color col4, Color col5) {
        final Point3D a = new Point3D(1, 1, 0);
        final Point3D b = new Point3D(1, -1, 0);
        final Point3D c = new Point3D(-1, -1, 0);
        final Point3D d = new Point3D(-1, 1, 0);
        final Point3D v = new Point3D(0, 0, 2);

        final Vertex z0 = new Vertex(a, new Col(col1.getRGB()));
        final Vertex z1 = new Vertex(b, new Col(col1.getRGB()));
        final Vertex z2 = new Vertex(c, new Col(col1.getRGB()));
        final Vertex z3 = new Vertex(d, new Col(col1.getRGB()));

        // base
        addVertices(z0, z1, z2, z3);
        addIndices(0, 1, 2, 3);
        this.parts.add(new Part(Topology.TRIANGLE_FAN, 0, 2));

        final Vertex v1 = new Vertex(v, new Col(col2.getRGB()));
        final Vertex z4 = new Vertex(a, new Col(col2.getRGB()));
        final Vertex z5 = new Vertex(b, new Col(col2.getRGB()));

        // side 1
        this.parts.add(new Part(Topology.TRIANGLES, getVertices().size(), 1));
        addVertices(v1, z4, z5);
        addIndices(4, 5, 6);

        // side 2
        this.parts.add(new Part(Topology.TRIANGLES, getVertices().size(), 1));
        addVertices(
                new Vertex(v, new Col(col3.getRGB())),
                new Vertex(b, new Col(col3.getRGB())),
                new Vertex(c, new Col(col3.getRGB()))
        );
        addIndices(7, 8, 9);

        // side 3
        this.parts.add(new Part(Topology.TRIANGLES, getVertices().size(), 1));
        addVertices(
                new Vertex(v, new Col(col4.getRGB())),
                new Vertex(c, new Col(col4.getRGB())),
                new Vertex(d, new Col(col4.getRGB()))
        );
        addIndices(10, 11, 12);

        // side 4
        this.parts.add(new Part(Topology.TRIANGLES, getVertices().size(), 1));
        addVertices(
                new Vertex(v, new Col(col5.getRGB())),
                new Vertex(d, new Col(col5.getRGB())),
                new Vertex(a, new Col(col5.getRGB()))
        );
        addIndices(13, 14, 15);


    }

    /**
     * Create pyramid with one solid color
     *
     * @param color color
     */
    public Pyramid(Color color) {
        final Vertex z0 = new Vertex(new Point3D(1, 1, 0), new Col(color.getRGB()));
        final Vertex z1 = new Vertex(new Point3D(-1, 1, 0), new Col(color.getRGB()));
        final Vertex z2 = new Vertex(new Point3D(-1, -1, 0), new Col(color.getRGB()));
        final Vertex z3 = new Vertex(new Point3D(1, -1, 0), new Col(color.getRGB()));

        final Vertex v = new Vertex(new Point3D(0, 0, 2), new Col(color.getRGB()));

        addVertices(v, z0, z1, z2, z3);
        // základna
        addIndices(1, 2, 3, 3, 4, 1);
        parts.add(new Part(Topology.TRIANGLES, 0, 2));
        // strany
        parts.add(new Part(Topology.TRIANGLE_FAN, getIndexBuffer().size(), 4));
        addIndices(0, 1, 2, 3, 4, 1);
    }

    /**
     * Create pyramid with 5 colors - yellow, red, blue, green, white
     */
    public Pyramid() {
        final Vertex z0 = new Vertex(new Point3D(1, 1, 0), new Col(Color.YELLOW.getRGB()));
        final Vertex z1 = new Vertex(new Point3D(-1, 1, 0), new Col(Color.RED.getRGB()));
        final Vertex z2 = new Vertex(new Point3D(-1, -1, 0), new Col(Color.BLUE.getRGB()));
        final Vertex z3 = new Vertex(new Point3D(1, -1, 0), new Col(Color.GREEN.getRGB()));

        final Vertex v = new Vertex(new Point3D(0, 0, 2), new Col(Color.WHITE.getRGB()));

        addVertices(v, z0, z1, z2, z3);
        // základna
        addIndices(1, 2, 3, 3, 4, 1);
        parts.add(new Part(Topology.TRIANGLES, 0, 2));
        // strany
        parts.add(new Part(Topology.TRIANGLE_FAN, getIndexBuffer().size(), 4));
        addIndices(0, 1, 2, 3, 4, 1);

    }

    @Override
    public List<Vertex> getVertices() {
        return this.vertexBuffer;
    }

    @Override
    public TransformableState getTransformableState() {
        return this.state;
    }
}
