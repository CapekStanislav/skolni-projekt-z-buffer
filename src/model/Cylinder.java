package model;

import model.primitives.Circle;
import model.transformation.TransformState;
import model.transformation.Transformable;
import model.transformation.TransformableState;
import model.transformation.Transformer;

import java.awt.Color;
import java.util.List;

/**
 * Instance of class {@code Cylinder}
 *
 * @author Stanislav Čapek
 * @version 1.0
 */
public class Cylinder extends Solid implements Transformable {

    private final TransformableState state = new TransformState();

    /**
     * Creates cylinder
     *
     * @param count detail of base
     */
    public Cylinder(int count) {
        this(count, 3);
    }

    /**
     * Creates cylinder with specific length
     *
     * @param count  detail of base
     * @param length vertical length
     */
    public Cylinder(int count, int length) {
        this(count, length, Color.RED);

    }

    /**
     * Creates cylinder with specific length and color
     *
     * @param count  detail of base
     * @param length vertical length
     * @param color  color of whole cylinder
     */
    public Cylinder(int count, int length, Color color) {
        this(count, length, color, Color.DARK_GRAY);
    }

    /**
     * Creates cylinder with specific length, color of base
     * and wrap
     *
     * @param count  detail of base
     * @param length vertical length
     * @param base   color of base
     * @param wrap   color of wrap
     */
    public Cylinder(int count, int length, Color base, Color wrap) {
        final Transformer t = new Transformer();

        final Circle circle1 = new Circle(count, wrap);
        this.vertexBuffer.addAll(circle1.getVertexBuffer());

        final Circle circle2 = new Circle(count, wrap);
        t.move(circle2, 0, 0, length);
        this.vertexBuffer.addAll(circle2.getVertices());

        this.parts.add(new Part(Topology.TRIANGLE_STRIP, 0, count * 2));
        for (int i = 1; i <= count; i++) {
            addIndices((i + count + 1), i);
        }
        addIndices(2 + count, 1);

        final Circle baseCircle = new Circle(count, base);
        createBase(count, baseCircle);
        t.move(baseCircle, 0, 0, length);
        createBase(count, baseCircle);
    }


    private void createBase(int count, Circle circle) {
        final int ibSize = indexBuffer.size();
        this.parts.add(new Part(Topology.TRIANGLE_FAN, ibSize, count));

        final int vbSize = vertexBuffer.size();
        for (int i = 0; i < circle.indexBuffer.size(); i++) {
            addIndices(vbSize + circle.indexBuffer.get(i));
        }
        this.vertexBuffer.addAll(circle.vertexBuffer);

//        int lastVertex = getVertexBuffer().size();
//        this.vertexBuffer.addAll(circle.vertexBuffer);
//        for (int i = 0; i < circle.getIndexBuffer().size(); i++) {
//            addIndices(circle.getIndexBuffer().get(i) + lastVertex);
//        }
//        this.parts.add(new Part(Topology.TRIANGLE_FAN, (indexBuffer.size() - count - 1), count));
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
