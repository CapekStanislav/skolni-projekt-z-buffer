package model.transformation;

import transforms.Point3D;

/**
 * Instance třídy {@code TransformState} je základní implementací
 * rozhraní {@link TransformableState}.
 *
 * @author Stanislav Čapek
 * @version 1.0
 */
public class TransformState implements TransformableState {

    private Point3D center;
    private double scaleX = 1;
    private double scaleY = 1;
    private double scaleZ = 1;
    private double rotationX = 0;
    private double rotationY = 0;
    private double rotationZ = 0;

    public TransformState() {
        this(new Point3D());
    }

    public TransformState(Point3D center) {
        this.center = center;
    }

    @Override
    public Point3D getCenter() {
        return this.center;
    }

    @Override
    public void setCenter(Point3D point) {
        this.center = point;
    }

    @Override
    public double getScaleX() {
        return this.scaleX;
    }

    @Override
    public void setScaleX(double ration) {
        this.scaleX = ration;
    }

    @Override
    public double getRotationX() {
        return this.rotationX;
    }

    @Override
    public void setRotationX(double radians) {
        this.rotationX = radians;
    }

    @Override
    public double getScaleY() {
        return this.scaleY;
    }

    @Override
    public void setScaleY(double ration) {
        this.scaleY = ration;
    }

    @Override
    public double getRotationY() {
        return this.rotationY;
    }

    @Override
    public void setRotationY(double radians) {
        this.rotationY = radians;
    }

    @Override
    public double getScaleZ() {
        return this.scaleZ;
    }

    @Override
    public void setScaleZ(double ration) {
        this.scaleZ = ration;
    }

    @Override
    public double getRotationZ() {
        return this.rotationZ;
    }

    @Override
    public void setRotationZ(double radians) {
        this.rotationZ = radians;
    }
}
