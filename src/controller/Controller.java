package controller;

import renderer.Renderer;
import view.Raster;

public class Controller {

    private final Renderer renderer;

    public Controller(Raster raster) {
        this.renderer = new Renderer(raster);

        initListeners(raster);
    }

    private void initListeners(Raster raster) {

    }
}
