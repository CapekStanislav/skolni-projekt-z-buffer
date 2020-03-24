package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Instance of class {@code Solid}
 *
 * @author Stanislav Čapek
 * @version 1.0
 */
public class Solid implements Renderable {

    protected final List<Vertex> vertexBuffer = new ArrayList<>();
    protected final List<Integer> indexBuffer = new ArrayList<>();
    protected final List<Part> parts = new ArrayList<>();

    @Override
    public List<Vertex> getVertexBuffer() {
        return vertexBuffer;
    }

    @Override
    public List<Integer> getIndexBuffer() {
        return indexBuffer;
    }

    @Override
    public List<Part> getParts() {
        return parts;
    }

    protected final void addVertices(Vertex... vertices) {
        vertexBuffer.addAll(List.of(vertices));
    }

    protected final void addIndices(Integer... indices) {
        indexBuffer.addAll(List.of(indices));
    }
}
