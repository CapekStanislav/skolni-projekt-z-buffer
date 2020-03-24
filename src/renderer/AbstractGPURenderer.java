package renderer;

import model.Vertex;
import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Point3D;
import transforms.Vec3D;
import view.Raster;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Stanislav Čapek
 * @version 1.0
 */
public abstract class AbstractGPURenderer implements GPURenderer {

    protected final DepthBuffer<Double> zb;
    private final int width;
    private final int height;
    private Raster raster;
    protected Mat4 model, view, projection;

    public AbstractGPURenderer(Raster raster) {
        this.raster = raster;
        this.width = raster.getWidth();
        this.height = raster.getHeight();
        model = new Mat4Identity();
        view = new Mat4Identity();
        projection = new Mat4Identity();
        zb = new DepthBuffer<>(new Double[width][height]);
    }

    @Override
    public void clear() {
        raster.clear();
        zb.clear(1d);
    }

    protected void preparePoint(Vertex v) {
        Vertex a = new Vertex(
                v.getPoint().mul(model).mul(view).mul(projection),
                v.getColor()
        );
        if (-a.getW() > a.getX()) {
            return;
        }

        if (a.getX() > a.getW()) {
            return;
        }

        // ořezání pro hranu Y
        if (-a.getW() > a.getY()) {
            return;
        }
        if (a.getY() > a.getW()) {
            return;
        }

        // ořezání pro hranu Z
        if (0 > a.getZ()) {
            return;
        }
        if (a.getZ() > a.getW()) {
            return;
        }

        final Optional<Vertex> ad = a.demohog();
        if (!ad.isPresent()) {
            return;
        }
        a = ad.get();

        final Vec3D vec3D = transformToWindow(a.getPoint());

        a = new Vertex(new Point3D(vec3D), a.getColor());

        drawPixel(a);
    }

    protected void prepareLine(Vertex v1, Vertex v2) {

        Vertex a = new Vertex(
                v1.getPoint().mul(model).mul(view).mul(projection),
                v1.getColor()
        );
        Vertex b = new Vertex(
                v2.getPoint().mul(model).mul(view).mul(projection),
                v2.getColor()
        );

//        ořezání celé úsečky do objemu
        // ořezání pro hranu X
        if (-a.getW() > a.getX() && -b.getW() > b.getX()) {
            return;
        }

        if (a.getX() > a.getW() && b.getX() > b.getW()) {
            return;
        }

        // ořezání pro hranu Y
        if (-a.getW() > a.getY() && -b.getW() > b.getY()) {
            return;
        }
        if (a.getY() > a.getW() && b.getY() > b.getW()) {
            return;
        }

        // ořezání pro hranu Z
        if (0 > a.getZ() && 0 > b.getZ()) {
            return;
        }

        if (a.getZ() > a.getW() && b.getZ() > b.getW()) {
            return;
        }

        // seřazení podle Z
        if (a.getZ() < b.getZ()) {
            Vertex tmp = a;
            a = b;
            b = tmp;
        }

        // ořezání pro hranu Z
        if (a.getZ() < 0) {
            return;
        } else if (b.getZ() < 0) {
            final double t = getParameterT(0d, a.getZ(), b.getZ());
            final Vertex c = interpolate(a, b, t);
            drawLine(a, c);
        } else {
            drawLine(a, b);
        }


    }

    protected void drawLine(Vertex v1, Vertex v2) {
        final Optional<Vertex> v1d = v1.demohog();
        final Optional<Vertex> v2d = v2.demohog();

        if (!v1d.isPresent() || !v2d.isPresent()) {
            return;
        }
        Vertex a = v1d.get();
        Vertex b = v2d.get();

        final Vec3D vec3D1 = transformToWindow(a.getPoint());
        final Vec3D vec3D2 = transformToWindow(b.getPoint());

        a = new Vertex(new Point3D(vec3D1), a.getColor());
        b = new Vertex(new Point3D(vec3D2), b.getColor());

        double k = (b.getY() - a.getY()) / (b.getX() - a.getX());


        if (Math.abs(k) <= 1) {

            // clip line
            if (a.getY() > b.getY()) {
                Vertex tmp = a;
                a = b;
                b = tmp;
            }
            if (a.getY() < 0) {
                final double t = getParameterT(1, a.getY(), b.getY());
                a = interpolate(a, b, t);
            } else if (b.getY() >= height) {
                final double t = getParameterT((height - 1), a.getY(), b.getY());
                b = interpolate(a, b, t);
            }

            // draw line
            if (a.getX() > b.getX()) {
                Vertex tmp = a;
                a = b;
                b = tmp;
            }
            for (int x = Math.max((int) a.getX() + 1, 0); x <= Math.min((int) b.getX(), width - 1); x++) {
                double t = getParameterT(x, a.getX(), b.getX());
                final Vertex ab = interpolate(a, b, t);
                drawPixel(ab);
            }
        } else if (Math.abs(k) > 1) {

            // clip line
            if (a.getX() > b.getX()) {
                Vertex tmp = a;
                a = b;
                b = tmp;
            }
            if (a.getX() < 0) {
                final double t = getParameterT(1, a.getX(), b.getX());
                a = interpolate(a, b, t);
            } else if (b.getX() >= width) {
                final double t = getParameterT((width - 1), a.getX(), b.getX());
                b = interpolate(a, b, t);
            }

            // draw line
            if (a.getY() > b.getY()) {
                Vertex tmp = a;
                a = b;
                b = tmp;
            }
            for (int y = Math.max((int) a.getY() + 1, 0); y <= Math.min((int) b.getY(), height - 1); y++) {
                double t = getParameterT(y, a.getY(), b.getY());
                final Vertex ab = interpolate(a, b, t);
                drawPixel(ab);
            }
        }
    }

    protected void prepareTriangle(Vertex v1, Vertex v2, Vertex v3) {
        // transformace vrcholu
        Vertex a = new Vertex(
                v1.getPoint().mul(model).mul(view).mul(projection),
                v1.getColor()
        );
        Vertex b = new Vertex(
                v2.getPoint().mul(model).mul(view).mul(projection),
                v2.getColor()
        );
        Vertex c = new Vertex(
                v3.getPoint().mul(model).mul(view).mul(projection),
                v3.getColor()
        );

//        ořezání celého trojúhehlníku do objemu
        // ořezání pro hranu X
        if (-a.getW() > a.getX() && -b.getW() > b.getX() && -c.getW() > c.getX()) {
            return;
        }

        if (a.getX() > a.getW() && b.getX() > b.getW() && c.getX() > c.getW()) {
            return;
        }

        // ořezání pro hranu Y
        if (-a.getW() > a.getY() && -b.getW() > b.getY() && -c.getW() > c.getY()) {
            return;
        }
        if (a.getY() > a.getW() && b.getY() > b.getW() && c.getY() > c.getW()) {
            return;
        }

        // ořezání pro hranu Z
        if (0 > a.getZ() && 0 > b.getZ() && 0 > c.getZ()) {
            return;
        }

        if (a.getZ() > a.getW() && b.getZ() > b.getW() && c.getZ() > c.getW()) {
            return;
        }


        // seřazení podle Z
        final List<Vertex> vertexList = new ArrayList<>(List.of(a, b, c));
        vertexList.sort(Comparator.comparing(Vertex::getZ).reversed());
        a = vertexList.get(0);
        b = vertexList.get(1);
        c = vertexList.get(2);


        // ořezání podle hrany Z
        if (a.getZ() < 0) {
            return;
        } else if (b.getZ() < 0) {
            double t = getParameterT(0d, a.getZ(), b.getZ());
            final Vertex ab = interpolate(a, b, t);

            double t2 = getParameterT(0d, a.getZ(), c.getZ());
            final Vertex ac = interpolate(a, c, t2);

            drawTriangle(a, ab, ac);
        } else if (c.getZ() < 0) {
            double t = getParameterT(0d, b.getZ(), c.getZ());
            final Vertex bc = interpolate(b, c, t);

            final double t2 = getParameterT(0d, a.getZ(), c.getZ());
            final Vertex ac = interpolate(a, c, t2);

            drawTriangle(a, b, bc);
            drawTriangle(a, bc, ac);
        } else {
            drawTriangle(a, b, c);
        }

    }

    protected Vertex interpolate(Vertex a, Vertex b, double t) {
        return a.mul(1 - t).add(b.mul(t));
    }

    protected double getParameterT(double ux, double u1, double u2) {
        return (ux - u1) / (u2 - u1);
    }

    protected void drawTriangle(Vertex a, Vertex b, Vertex c) {

        final Optional<Vertex> dA = a.demohog();
        final Optional<Vertex> dB = b.demohog();
        final Optional<Vertex> dC = c.demohog();

        if (!dA.isPresent() || !dB.isPresent() || !dC.isPresent()) {
            return;
        }

        // po demohogizaci
        final Vertex v1 = dA.get();
        final Vertex v2 = dB.get();
        final Vertex v3 = dC.get();

        final Vec3D vec3D1 = transformToWindow(v1.getPoint());
        final Vec3D vec3D2 = transformToWindow(v2.getPoint());
        final Vec3D vec3D3 = transformToWindow(v3.getPoint());


        Vertex aa = new Vertex(new Point3D(vec3D1), v1.getColor());
        Vertex bb = new Vertex(new Point3D(vec3D2), v2.getColor());
        Vertex cc = new Vertex(new Point3D(vec3D3), v3.getColor());

        final List<Vertex> vertices = new ArrayList<>(List.of(aa, bb, cc));
        vertices.sort(Comparator.comparing(Vertex::getY));
        aa = vertices.get(0);
        bb = vertices.get(1);
        cc = vertices.get(2);

        for (int y = Math.max((int) aa.getY() + 1, 0); y <= Math.min(bb.getY(), height - 1); y++) {
            final double t = getParameterT(y, aa.getY(), bb.getY());
            final Vertex ab = interpolate(aa, bb, t);

            final double t2 = getParameterT(y, aa.getY(), cc.getY());
            final Vertex ac = interpolate(aa, cc, t2);
            fillLine(ab, ac);

        }

        for (int y = Math.max((int) bb.getY() + 1, 0); y <= Math.min((int) cc.getY(), height - 1); y++) {
            final double t = getParameterT(y, bb.getY(), cc.getY());
            final Vertex bc = interpolate(bb, cc, t);

            final double t2 = getParameterT(y, aa.getY(), cc.getY());
            final Vertex ac = interpolate(aa, cc, t2);

            fillLine(bc, ac);
        }

    }

    protected void fillLine(Vertex a, Vertex b) {
        if (a.getX() > b.getX()) {
            Vertex tmp = a;
            a = b;
            b = tmp;
        }

        for (int x = Math.max((int) a.getX() + 1, 0); x <= Math.min((int) b.getX(), width - 1); x++) {
            double tz = getParameterT(x, a.getX(), b.getX());
            final Vertex ab = interpolate(a, b, tz);
            drawPixel(ab);
        }
    }

    protected void drawPixel(Vertex v) {
        final int x = (int) Math.round(v.getX());
        final int y = (int) Math.round(v.getY());
        final double z = v.getZ();

        // z-test

        try {
            if (zb.get(x, y) > z) {
                zb.set(x, y, z);
                raster.drawPixel(x, y, v.getColor().getRGB());
            }
        } catch (Exception ignore) {
            // při zaokrouhlení dochází k chybě, zanedbatelné
        }
    }

    protected Vec3D transformToWindow(Point3D point3D) {
        return new Vec3D(point3D)
                .mul(new Vec3D(1, -1, 1)) // Y jde nahoru a my ho chceme dolů
                .add(new Vec3D(1, 1, 0)) // (0,0) je uprostřed, chceme v rohu
                // máme <0;2> -> vynásobíme polovinou velikosti okna
                .mul(new Vec3D(width / 2f, height / 2f, 1));
    }

}
