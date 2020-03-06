package controller;

import renderer.GPURenderer;
import renderer.Renderer3D;
import transforms.*;
import view.Raster;

import java.util.ArrayList;
import java.util.List;

public class Controller3D {

    private final GPURenderer renderer;
    private Mat4 model, view, projection;
    private Camera camera;

    public Controller3D(Raster raster) {
        this.renderer = new Renderer3D(raster);

        model = new Mat4Identity(); // jednotková matice -> nic se s tělesem nestane

        camera = new Camera()
                .withPosition(new Vec3D(0.5, -6, 2))
                .withAzimuth(Math.toRadians(90))
                .withZenith(Math.toRadians(-20));

        projection = new Mat4PerspRH(Math.PI / 3, 600 / 800f, 0.1, 20);

        display();
    }

    private void display() {
        renderer.clear();

        renderer.setView(camera.getViewMatrix());
        renderer.setProjection(projection);

        // vykreslení os
//        renderer.setModel(new Mat4Identity());
//        renderer.draw(axis);

        // vykreslení ostatních těles
        renderer.setModel(model);
//        renderer.draw(solids.toArray(new Solid[0]));
    }


}
