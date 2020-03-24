package model.primitives;

import model.Part;
import model.Solid;
import model.Topology;
import model.Vertex;
import model.transformation.TransformState;
import model.transformation.Transformable;
import model.transformation.TransformableState;
import transforms.Col;
import transforms.Point3D;

import java.awt.Color;
import java.util.List;

/**
 * Instance of class {@code Line}
 *
 * @author Stanislav Čapek
 * @version 1.0
 */
public class Line extends Solid implements Transformable {

    private TransformableState state = new TransformState();

    /**
     * Create straight line
     *
     * @param color color of line
     */
    public Line(Color color) {
        this(color, color);
    }


    /**
     * Create straight line
     *
     * @param a     start
     * @param b     end
     * @param color color of line
     */
    public Line(Point3D a, Point3D b, Color color) {
        this(a, b, color, color);
    }


    /**
     * Create straight line with mixed colors
     *
     * @param col1 start's color
     * @param col2 end's color
     */
    public Line(Color col1, Color col2) {
        this(
                new Point3D(-1, 0, 0),
                new Point3D(1, 0, 0),
                col1,
                col2
        );
    }

    /**
     * Create straight line with mixed colors
     *
     * @param a    start
     * @param b    end
     * @param col1 start's color
     * @param col2 end's color
     */
    public Line(Point3D a, Point3D b, Color col1, Color col2) {
        final Vertex v0 = new Vertex(a, new Col(col1.getRGB()));
        final Vertex v1 = new Vertex(b, new Col(col2.getRGB()));

        addVertices(v0, v1);
        addIndices(0, 1);
        this.parts.add(new Part(Topology.LINE_LIST, 0, 1));
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
