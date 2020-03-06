package renderer;

import transforms.Mat4;

public interface GPURenderer {

    void clear();

    void draw();

    void setModel(Mat4 model);

    void setView(Mat4 view);

    void setProjection(Mat4 projection);

}
