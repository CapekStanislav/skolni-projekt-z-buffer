package renderer;

import model3d.Solid;
import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Point3D;
import transforms.Vec3D;
import view.Raster;

import java.awt.*;
import java.util.List;
import java.util.Optional;

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
    public void draw(Solid... solids) {
        for (Solid solid : solids) {
            List<Point3D> vertices = solid.getVertices();
            List<Integer> indices = solid.getIndices();
            for (int i = 0; i < indices.size(); i += 2) {
                Point3D a = vertices.get(indices.get(i));
                Point3D b = vertices.get(indices.get(i + 1));
                transformLine(a, b, solid.getColor());
            }
        }
    }

    private void transformLine(Point3D a, Point3D b, Color color) {
        a = a.mul(model).mul(view).mul(projection);
        b = b.mul(model).mul(view).mul(projection);

        if (clip(a)) return;
        if (clip(b)) return;

        Optional<Vec3D> dehomogA = a.dehomog();
        Optional<Vec3D> dehomogB = b.dehomog();

        if (!dehomogA.isPresent() || !dehomogB.isPresent()) return;

        Vec3D v1 = dehomogA.get();
        Vec3D v2 = dehomogB.get();

        v1 = transformToWindow(v1);
        v2 = transformToWindow(v2);

        raster.drawLine(
                (int) Math.round(v1.getX()),
                (int) Math.round(v1.getY()),
                (int) Math.round(v2.getX()),
                (int) Math.round(v2.getY()),
                color
        );
    }

    private boolean clip(Point3D p) {
        if (p.getW() < p.getX() || p.getX() < -p.getW()) return true;
        if (p.getW() < p.getY() || p.getY() < -p.getW()) return true;
        if (p.getW() < p.getZ() || p.getZ() < 0) return true;
        return false;
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
