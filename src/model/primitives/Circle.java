package model.primitives;

import model.Part;
import model.Solid;
import model.Topology;
import model.Vertex;
import model.transformation.TransformState;
import model.transformation.Transformable;
import model.transformation.TransformableState;
import transforms.Col;
import transforms.Mat4RotZ;
import transforms.Point3D;

import java.awt.Color;
import java.util.List;

/**
 * Instance of class {@code Circle}
 *
 * @author Stanislav Čapek
 * @version 1.0
 */
public class Circle extends Solid implements Transformable {
    private final Vertex radius;
    private final int count;
    private final Vertex center;
    private final TransformableState state = new TransformState();

    /**
     * Circle with center
     *
     * @param count outer ring
     * @param color color of circle
     */
    public Circle(int count, Color color) {
        final Col col = new Col(color.getRGB());
        this.center = new Vertex(new Point3D(), col);
        this.radius = new Vertex(new Point3D(1, 0, 0), col);
        this.count = count;

        this.vertexBuffer.add(center);
        this.vertexBuffer.add(radius);

        final double radians = Math.toRadians(360d / count);

        for (int i = 1; i < count; i++) {
            final Mat4RotZ rotZ = new Mat4RotZ(radians * i);
            this.vertexBuffer.add(
                    new Vertex(radius.getPoint().mul(rotZ), col)
            );
        }

        for (int i = 0; i <= count; i++) {
            this.indexBuffer.add(i);
        }
        this.indexBuffer.add(1);

        this.parts.add(new Part(Topology.TRIANGLE_FAN, 0, count));
    }

    /**
     * Circle with center
     *
     * @param count outer ring
     */
    public Circle(int count) {
        this(count, Color.CYAN);
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
