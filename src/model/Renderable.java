package model;

import java.util.List;

/**
 * Instance of interface {@code Renderable}. It can be rendered by {@link renderer.GPURenderer}
 *
 * @author Stanislav Čapek
 * @version 1.0
 */
public interface Renderable {

    /**
     * Object's vertex buffer
     *
     * @return list of vertices
     */
    List<Vertex> getVertexBuffer();

    /**
     * Object's index buffer. Pointer to vertices.
     *
     * @return list of indexes
     */
    List<Integer> getIndexBuffer();

    /**
     * Object's topology
     *
     * @return list of parts
     */
    List<Part> getParts();
}
