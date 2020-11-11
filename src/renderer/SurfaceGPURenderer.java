package renderer;

import model.*;
import transforms.Mat4;
import view.Raster;

import java.util.List;

/**
 * Instance of class {@code SurfaceGPURenderer}. Render object's surface.
 *
 * @author Stanislav Čapek
 * @version 1.0
 */
public class SurfaceGPURenderer extends AbstractGPURenderer {
    Texture2D texture = null;

    public SurfaceGPURenderer(Raster raster) {
        super(raster);
    }

    @Override
    public void draw(Renderable... renderables) {
        for (Renderable renderable : renderables) {
            texture = renderable instanceof TextureRenderable ? ((TextureRenderable) renderable).getTexture() : null;
            draw(renderable.getParts(), renderable.getVertexBuffer(), renderable.getIndexBuffer());
        }
    }

    @Override
    public void draw(List<Part> parts, List<Vertex> vb, List<Integer> ib) {
        for (Part part : parts) {
            final int start = part.getIndex();
            final int count = part.getCount();
            switch (part.getType()) {
                case TRIANGLES:
                    for (int index = start; index < start + (count * 3); index += 3) {
                        final Integer i1 = ib.get(index);
                        final Integer i2 = ib.get(index + 1);
                        final Integer i3 = ib.get(index + 2);

                        final Vertex v1 = vb.get(i1);
                        final Vertex v2 = vb.get(i2);
                        final Vertex v3 = vb.get(i3);

                        prepareTriangle(v1, v2, v3);
                    }
                    break;

                case TRIANGLE_STRIP:
                    for (int index = start; index < count + start; index++) {
                        final Integer i1 = ib.get(index);
                        final Integer i2 = ib.get(index + 1);
                        final Integer i3 = ib.get(index + 2);

                        final Vertex v1 = vb.get(i1);
                        final Vertex v2 = vb.get(i2);
                        final Vertex v3 = vb.get(i3);
                        prepareTriangle(v1, v2, v3);
                    }
                    break;

                case TRIANGLE_FAN:
                    final Vertex origin = vb.get(ib.get(start));
                    for (int index = start; index < count + start; index++) {
                        final Integer i2 = ib.get(index + 1);
                        final Integer i3 = ib.get(index + 2);

                        final Vertex v2 = vb.get(i2);
                        final Vertex v3 = vb.get(i3);
                        prepareTriangle(origin, v2, v3);
                    }
                    break;

                case LINE_LIST:

                    for (int index = start; index <= count; index += 2) {
                        final Vertex v1 = vb.get(ib.get(index));
                        final Vertex v2 = vb.get(ib.get(index + 1));
                        prepareLine(v1, v2);
                    }
                    break;

                case LINE_STRIP:
                    for (int index = start; index < count + start; index++) {
                        final Vertex v1 = vb.get(ib.get(index));
                        final Vertex v2 = vb.get(ib.get(index + 1));
                        prepareLine(v1, v2);
                    }
                    break;

                case LINE_LOOP:
                    Vertex firstVertex = vb.get(ib.get(start));
                    Vertex lastVertex = vb.get(vb.size() - 1);
                    for (int index = start; index < count + start; index++) {
                        final Vertex v1 = vb.get(ib.get(index));
                        final Vertex v2 = vb.get(ib.get(index + 1));
                        prepareLine(v1, v2);
                        lastVertex = v2;
                    }
                    prepareLine(firstVertex, lastVertex);
                    break;

                case POINTS:
                    for (int index = start; index < count; index++) {
                        preparePoint(vb.get(ib.get(index)));
                    }
                    break;

                default:
                    throw new IllegalStateException("Neznámy typ topologie: " + part.getType());
            }
        }
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

    @Override
    protected void fillLine(Vertex a, Vertex b) {
        if (a.getX() > b.getX()) {
            Vertex tmp = a;
            a = b;
            b = tmp;
        }

        for (int x = Math.max((int) a.getX() + 1, 0); x <= Math.min((int) b.getX(), width - 1); x++) {
            double tz = getParameterT(x, a.getX(), b.getX());
            Vertex ab = interpolate(a, b, tz);

            drawPixel(ab);
        }
    }

    @Override
    protected void drawPixel(Vertex ver) {
        final int x = (int) Math.round(ver.getX());
        final int y = (int) Math.round(ver.getY());
        final double z = ver.getZ();

        // z-test

        try {
            if (zb.get(x, y) > z) {
                zb.set(x, y, z);

                int rgb = ver.getColor().mul(1/ver.getOne()).getRGB()ł;
                // kontrola jestli má objekt texturu
                if (texture != null) {
                    final int u = ((int) Math.round(ver.getU()*(1/ver.getOne())));
                    final int v = ((int) Math.round(ver.getV()*(1/ver.getOne())));

                    rgb = texture.getColor(u, v).getRGB();
                }
                raster.drawPixel(x, y, rgb);
            }
        } catch (Exception ignore) {
            // při zaokrouhlení dochází k chybě, zanedbatelné
        }
    }
}

