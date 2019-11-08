package model3d;

import transforms.Point3D;

import java.awt.*;

public class Cube extends Solid {

    public Cube() {
        this(Color.YELLOW);
    }

    public Cube(Color color) {
        this.color = color;
        vertices.add(new Point3D(-1, -1, -1));
        vertices.add(new Point3D(1, -1, -1));
        vertices.add(new Point3D(1, -1, 1));
        vertices.add(new Point3D(-1, -1, 1));

        vertices.add(new Point3D(-1, 1, -1));
        vertices.add(new Point3D(1, 1, -1));
        vertices.add(new Point3D(1, 1, 1));
        vertices.add(new Point3D(-1, 1, 1));

        // spodní "stěna"
        addIndices(0, 1, 1, 2, 2, 3, 3, 0);
        // horní "stěna"
        addIndices(4, 5, 5, 6, 6, 7, 7, 4);
        // okolní obal
        addIndices(0, 4, 1, 5, 2, 6, 3, 7);
    }
}
