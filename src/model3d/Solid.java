package model3d;

import transforms.Point3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Solid {

    final List<Point3D> vertices = new ArrayList<>();
    final List<Integer> indices = new ArrayList<>();
    Color color;

    final void addIndices(Integer... toAdd) {
        indices.addAll(Arrays.asList(toAdd));
    }

    public List<Point3D> getVertices() {
        return vertices;
    }

    public List<Integer> getIndices() {
        return indices;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
