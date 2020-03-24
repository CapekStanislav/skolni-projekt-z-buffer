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
 * Instance of class {@code Square}
 *
 * @author Stanislav Čapek
 * @version 1.0
 */
public class Square extends Solid implements Transformable {

    private final TransformableState state = new TransformState();

    /**
     * Create square with 4 colors yellow, red, green, blue on each corner.
     */
    public Square() {

        final Vertex z0 = new Vertex(new Point3D(1, 1, 0), new Col(Color.YELLOW.getRGB()));
        final Vertex z1 = new Vertex(new Point3D(-1, 1, 0), new Col(Color.RED.getRGB()));
        final Vertex z2 = new Vertex(new Point3D(-1, -1, 0), new Col(Color.GREEN.getRGB()));
        final Vertex z3 = new Vertex(new Point3D(1, -1, 0), new Col(Color.BLUE.getRGB()));

        this.vertexBuffer.addAll(List.of(z2, z1, z3, z0));
        this.indexBuffer.addAll(List.of(0, 1, 2, 3));
        this.parts.add(new Part(Topology.TRIANGLE_STRIP, 0, 2));
    }

    /**
     * Create square with one color
     *
     * @param color
     */
    public Square(Color color) {
        final Vertex z0 = new Vertex(new Point3D(1, 1, 0), new Col(color.getRGB()));
        final Vertex z1 = new Vertex(new Point3D(-1, 1, 0), new Col(color.getRGB()));
        final Vertex z2 = new Vertex(new Point3D(-1, -1, 0), new Col(color.getRGB()));
        final Vertex z3 = new Vertex(new Point3D(1, -1, 0), new Col(color.getRGB()));

        this.vertexBuffer.addAll(List.of(z0, z1, z2, z3));
        this.indexBuffer.addAll(List.of(0, 1, 2, 2, 3, 0));
        this.parts.add(new Part(Topology.TRIANGLES, 0, 2));

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
