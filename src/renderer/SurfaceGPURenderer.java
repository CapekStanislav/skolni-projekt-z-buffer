package renderer;

import model.Part;
import model.Renderable;
import model.Vertex;
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

    public SurfaceGPURenderer(Raster raster) {
        super(raster);
    }

    @Override
    public void draw(Renderable... renderables) {
        for (Renderable renderable : renderables) {
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

}
