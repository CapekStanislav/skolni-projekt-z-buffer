package model;

import transforms.Col;
import transforms.Point3D;
import transforms.Vec3D;

import java.util.Optional;

/**
 * Instance of class {@code Vertex}
 *
 * @author Stanislav Čapek
 * @version 1.0
 */
public class Vertex {
    private final Point3D point;
    private final Col color;
    /**
     * Coordinates of vertex
     */
    private final double x, y, z, w;

    /**
     * One point with his one state.
     *
     * @param point location
     * @param color color
     */
    public Vertex(Point3D point, Col color) {
        this.point = point;
        this.color = color;
        this.x = point.getX();
        this.y = point.getY();
        this.z = point.getZ();
        this.w = point.getW();
    }

    public Vertex mul(double d) {
        return new Vertex(point.mul(d), color.mul(d));
    }

    public Vertex add(Vertex v) {
        return new Vertex(
                point.add(v.getPoint()),
                color.add(v.getColor())
        );
    }

    public Optional<Vertex> demohog() {
        Optional<Vec3D> optional = point.dehomog();
        if (optional.isPresent()) {
            return Optional.of(new Vertex(new Point3D(optional.get()), color));
        } else {
            return Optional.empty();
        }
    }

    public Point3D getPoint() {
        return point;
    }

    public Col getColor() {
        return color;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getW() {
        return w;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "point=" + point +
                ", color=" + color +
                '}';
    }
}
