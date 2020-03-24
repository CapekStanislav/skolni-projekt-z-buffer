package model;

import model.primitives.Square;
import model.transformation.TransformState;
import model.transformation.Transformable;
import model.transformation.TransformableState;
import model.transformation.Transformer;

import java.awt.Color;
import java.util.List;

/**
 * Instance of class {@code Cube}
 *
 * @author Stanislav Čapek
 * @version 1.0
 */
public class Cube extends Solid implements Transformable {

    private final TransformableState stat = new TransformState();

    /**
     * Creates cube with colored sides with different color.
     */
    public Cube() {
        final Transformer t = new Transformer();
        final Square sq1 = new Square(Color.BLUE);
        final Square sq2 = new Square(Color.GREEN);
        final Square sq3 = new Square(Color.RED);
        final Square sq4 = new Square(Color.YELLOW);
        final Square sq5 = new Square(Color.CYAN);
        final Square sq6 = new Square(Color.BLACK);

        this.vertexBuffer.addAll(sq1.vertexBuffer);

        final double r = Math.toRadians(90);
        t.rotate(sq2, 0, r, 0);
        t.move(sq2, 1, 0, 1);
        this.vertexBuffer.addAll(sq2.vertexBuffer);

        t.rotate(sq3, 0, -r, 0);
        t.move(sq3, -1, 0, 1);
        this.vertexBuffer.addAll(sq3.vertexBuffer);

        t.rotate(sq4, r, 0, 0);
        t.move(sq4, 0, 1, 1);
        this.vertexBuffer.addAll(sq4.vertexBuffer);

        t.rotate(sq5, -r, 0, 0);
        t.move(sq5, 0, -1, 1);
        this.vertexBuffer.addAll(sq5.vertexBuffer);

        t.move(sq6, 0, 0, 2);
        this.vertexBuffer.addAll(sq6.vertexBuffer);

        for (int i = 0; i < getVertices().size(); i++) {
            addIndices(i);
        }
        this.parts.add(new Part(Topology.TRIANGLE_FAN, 0, 2));
        this.parts.add(new Part(Topology.TRIANGLE_FAN, 4, 2));
        this.parts.add(new Part(Topology.TRIANGLE_FAN, 8, 2));
        this.parts.add(new Part(Topology.TRIANGLE_FAN, 12, 2));
        this.parts.add(new Part(Topology.TRIANGLE_FAN, 16, 2));
        this.parts.add(new Part(Topology.TRIANGLE_FAN, 20, 2));

    }

    @Override
    public List<Vertex> getVertices() {
        return this.vertexBuffer;
    }

    @Override
    public TransformableState getTransformableState() {
        return this.stat;
    }
}
