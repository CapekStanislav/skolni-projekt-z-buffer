package renderer;

import model.Part;
import model.Renderable;
import model.Vertex;
import transforms.Mat4;

import java.util.List;

public interface GPURenderer {

    void clear();

    /**
     * @param parts parts of model
     * @param vb    vertex buffer
     * @param ib    index buffer
     */
    void draw(List<Part> parts, List<Vertex> vb, List<Integer> ib);

    void draw(Renderable... renderables);

    void setModel(Mat4 model);

    void setView(Mat4 view);

    void setProjection(Mat4 projection);

}
