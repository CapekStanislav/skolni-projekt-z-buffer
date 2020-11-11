package model;

import model.transformation.TransformState;
import model.transformation.Transformable;
import model.transformation.TransformableState;
import transforms.Col;
import transforms.Point3D;
import transforms.Vec2D;

import java.awt.Color;
import java.util.List;

/**
 * An instance of class {@code SquareTextured}
 *
 * @author Stanislav Čapek
 * @version 1.0
 */
public class SquareTextured extends Solid implements TextureRenderable, Transformable {

    private final TransformableState state = new TransformState();
    private Texture2D texture;

    public SquareTextured(String file) {
        setTexture(file);
        final int uMax = texture.getWidth() - 1;
        final int vMax = texture.getHeight() - 1;

        final Vertex z0 = new Vertex(
                new Point3D(1, 1, 0),
                new Col(Color.YELLOW.getRGB()),
                new Vec2D(uMax, vMax)
        );

        final Vertex z1 = new Vertex(
                new Point3D(-1, 1, 0),
                new Col(Color.RED.getRGB()),
                new Vec2D(0, vMax)
        );

        final Vertex z2 = new Vertex(
                new Point3D(-1, -1, 0),
                new Col(Color.GREEN.getRGB()),
                new Vec2D(0, 0)
        );

        final Vertex z3 = new Vertex(
                new Point3D(1, -1, 0),
                new Col(Color.BLUE.getRGB()),
                new Vec2D(uMax,0)
        );

        this.vertexBuffer.addAll(List.of(z2, z1, z3, z0));
        this.indexBuffer.addAll(List.of(0, 1, 2, 3));
        this.parts.add(new Part(Topology.TRIANGLE_STRIP, 0, 2));
    }

    @Override
    public Texture2D getTexture() {
        return texture;
    }

    @Override
    public void setTexture(String file) {
        this.texture = new DefaultTexture2D(file);
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
