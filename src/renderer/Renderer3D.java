package renderer;

import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Vec3D;
import view.Raster;

public class Renderer3D implements GPURenderer {

    private Raster raster;
    private Mat4 model, view, projection;

    public Renderer3D(Raster raster) {
        this.raster = raster;
        model = new Mat4Identity();
        view = new Mat4Identity();
        projection = new Mat4Identity();
    }

    @Override
    public void clear() {
        raster.clear();
    }

    @Override
    public void draw() {
        // TODO
    }

    private Vec3D transformToWindow(Vec3D vec) {
        return vec
                .mul(new Vec3D(1, -1, 1)) // Y jde nahoru a my ho chceme dolů
                .add(new Vec3D(1, 1, 0)) // (0,0) je uprostřed, chceme v rohu
                // máme <0;2> -> vynásobíme polovinou velikosti okna
                .mul(new Vec3D(800 / 2f, 600 / 2f, 1));
    }

    @Override
    public void setModel(Mat4 model) {
        this.model = model;
    }

    @Override
    public void setView(Mat4 view) {
        this.view = view;
    }

    @Override
    public void setProjection(Mat4 projection) {
        this.projection = projection;
    }

}
