package model;

import transforms.Col;
import transforms.Point3D;
import transforms.Vec1D;
import transforms.Vec2D;

import java.util.Optional;

/**
 * Instance of class {@code Vertex}
 *
 * @author Stanislav Čapek
 * @version 1.0
 */
public class Vertex {
    private final Point3D point;
    private final Vec2D texCoord;
    private final Col color;
    /**
     * Coordinates of vertex
     */
    private Vec1D one = new Vec1D(1);

    /**
     * One point with his one state.
     *
     * @param point location
     * @param color color
     */
    public Vertex(Point3D point, Col color) {
        this(point, color, new Vec2D());
    }

    public Vertex(Point3D point, Col color, Vec2D texCoord) {
        this.point = point;
        this.texCoord = texCoord;
        this.color = color;
    }

    /**
     * Copy constructor
     *
     * @param point
     * @param texCoord
     * @param color
     * @param one
     */
    private Vertex(Point3D point, Vec2D texCoord, Col color, Vec1D one) {
        this.point = point;
        this.texCoord = texCoord;
        this.color = color;
        this.one = one;
    }

    public Vertex withPoint(Point3D point) {
        return new Vertex(point, this.texCoord, this.color, this.one);
    }

    public Vertex withTextCoord(Vec2D texCoord) {
        return new Vertex(this.point, texCoord, this.color, this.one);
    }

    public Vertex withColor(Col color) {
        return new Vertex(this.point, this.texCoord, color, this.one);
    }

    public Vertex withOne(double one) {
        return new Vertex(this.point, this.texCoord, this.color, new Vec1D(one));
    }

    public Vertex mul(double d) {
        return new Vertex(point.mul(d), texCoord.mul(d), color.mul(d), one.mul(d));
    }

    public Vertex add(Vertex v) {
        return new Vertex(
                point.add(v.getPoint()),
                texCoord.add(v.getTexCoord()),
                color.add(v.getColor()),
                one.add(new Vec1D(v.getOne()))
        );
    }

    public Optional<Vertex> demohog() {
        return Optional.of(this.mul(1/this.getW()));
    }
//    public Optional<Vertex> demohog() {
//        one = one.mul(1 / getW());
//        Optional<Vec3D> optional = point.dehomog();
//        if (optional.isPresent()) {
//            final Vertex vertex = this.withPoint(new Point3D(optional.get()));
//            vertex.one = this.one;
//            return Optional.of(vertex);
//        } else {
//            return Optional.empty();
//        }
//    }

    public Point3D getPoint() {
        return point;
    }

    public Col getColor() {
        return color;
    }

    public double getX() {
        return point.getX();
    }

    public double getY() {
        return point.getY();
    }

    public double getZ() {
        return point.getZ();
    }

    public double getW() {
        return point.getW();
    }

    public double getU() {
        return texCoord.getX();
    }

    public double getV() {
        return texCoord.getY();
    }

    public double getOne() {
        return one.getX();
    }

    public Vec2D getTexCoord() {
        return texCoord;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "point=" + point +
                ", texCoord=" + texCoord +
                ", color=" + color +
                ", one=" + one +
                '}';
    }
}
