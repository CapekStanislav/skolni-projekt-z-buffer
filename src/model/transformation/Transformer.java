package model.transformation;

import model.Vertex;
import transforms.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Instance třídy {@code Transformer} představují služebníka pro objekty
 * instance rozhraní {@link Transformable} na kterých dokáže provést základní
 * transformace.
 *
 * @author Stanislav Čapek
 * @version 1.0
 */
public class Transformer {

    /**
     * Pohne s objektem přírůstkem podél os {@code X},{@code Y} a {@code Z}
     *
     * @param transformable transformovaný objekt
     * @param x             posun po ose x
     * @param y             posun po ose y
     * @param z             posun po ose z
     */
    public void move(Transformable transformable, double x, double y, double z) {
        final List<Vertex> vb = transformable.getVertices();
        Mat4Transl tran = new Mat4Transl(x, y, z);
        final List<Vertex> vertexList = vb.stream()
                .map(vertex -> {
                    final Point3D point = vertex.getPoint().mul(tran);
                    return new Vertex(point, vertex.getColor(),vertex.getTexCoord());
                })
                .collect(Collectors.toList());
        vb.clear();
        vb.addAll(vertexList);
        final TransformableState state = transformable.getTransformableState();
        state.setCenter(state.getCenter().mul(tran));
    }

    /**
     * Rotace podél počátku os {@code X}, {@code Y} a {@code Z}.
     *
     * @param transformable transformovaný objekt
     * @param alpha         rotace podél X, v radiánech
     * @param beta          rotace podél Y, v radiánech
     * @param gamma         rotace podél Z, v radiánech
     */
    public void rotate(Transformable transformable, double alpha, double beta, double gamma) {
        final List<Vertex> verticies = transformable.getVertices();
        Mat4RotXYZ rot = new Mat4RotXYZ(alpha, beta, gamma);
        final List<Vertex> vertexList = verticies.stream()
                .map(vertex ->
                        new Vertex(vertex.getPoint().mul(rot), vertex.getColor(),vertex.getTexCoord())
                )
                .collect(Collectors.toList());

        verticies.clear();
        verticies.addAll(vertexList);
        final TransformableState state = transformable.getTransformableState();
        state.setCenter(state.getCenter().mul(rot));
        setupTransformableRotation(transformable, alpha, beta, gamma);
    }

    /**
     * Rotace podél středu objektu.
     *
     * @param transformable transformovaný objekt
     * @param alpha         rotace podél X, v radiánech
     * @param beta          rotace podél Y, v radiánech
     * @param gamma         rotace podél Z, v radiánech
     */
    public void rotateByCenter(Transformable transformable, double alpha, double beta, double gamma) {
        final TransformableState state = transformable.getTransformableState();
        final Vec3D center = new Vec3D(state.getCenter());
        Mat4 rot = new Mat4Identity()
                .mul(new Mat4Transl(center.opposite()))
                .mul(new Mat4RotXYZ(alpha, beta, gamma))
                .mul(new Mat4Transl(center));
        final List<Vertex> verticies = transformable.getVertices();
        final List<Vertex> vertexList = verticies.stream()
                .map(vertex -> new Vertex(vertex.getPoint().mul(rot), vertex.getColor(),vertex.getTexCoord()))
                .collect(Collectors.toList());
        verticies.clear();
        verticies.addAll(vertexList);
        setupTransformableRotation(transformable, alpha, beta, gamma);
    }


    /**
     * Škáluje objekt podle počátku souřadnic.
     *
     * @param transformable transformovaný objekt
     * @param x             škálování podél X
     * @param y             škálování podél Y
     * @param z             škálování podél Z
     */
    public void scale(Transformable transformable, double x, double y, double z) {
        final List<Vertex> verticies = transformable.getVertices();
        Mat4Scale scale = new Mat4Scale(x, y, z);
        final List<Vertex> vertexList = verticies
                .stream()
                .map(vertex -> new Vertex(vertex.getPoint().mul(scale), vertex.getColor(),vertex.getTexCoord()))
                .collect(Collectors.toList());
        verticies.clear();
        verticies.addAll(vertexList);
        setupTransformabelScale(transformable, scale);
    }


    /**
     * Škáluje objekt podle středu objektu.
     *
     * @param transformable transformovaný objekt
     * @param x             škálování podle X
     * @param y             škálování podle Y
     * @param z             škálování podle Z
     */
    public void scaleByCenter(Transformable transformable, double x, double y, double z) {
        final List<Vertex> verticies = transformable.getVertices();
        final Vec3D center = new Vec3D(transformable.getTransformableState().getCenter());
        final Mat4Scale scale = new Mat4Scale(x, y, z);
        final Mat4 tranAndScale = new Mat4Identity()
                .mul(new Mat4Transl(center.opposite()))
                .mul(scale)
                .mul(new Mat4Transl(center));
        final List<Vertex> vertexList = verticies.stream()
                .map(vertex -> new Vertex(vertex.getPoint().mul(tranAndScale), vertex.getColor(),vertex.getTexCoord()))
                .collect(Collectors.toList());
        verticies.clear();
        verticies.addAll(vertexList);
        setupTransformabelScale(transformable, scale);
    }

    /**
     * Naství nové hodnoty objektu po škálování
     *
     * @param transformable transformovaný objekt
     * @param scale         matice škálování
     */
    private void setupTransformabelScale(Transformable transformable, Mat4 scale) {
        final TransformableState state = transformable.getTransformableState();
        Point3D scaling = new Point3D(
                state.getScaleX(),
                state.getScaleY(),
                state.getScaleZ())
                .mul(scale);
        state.setScaleX(scaling.getX());
        state.setScaleY(scaling.getY());
        state.setScaleZ(scaling.getZ());
    }

    /**
     * Nastaví nové hodnoty objektu po provedení rotace
     *
     * @param transformable transformovaný objekt
     * @param alpha         rotace podle X
     * @param beta          rotace podle Y
     * @param gamma         rotace podle Z
     */
    private void setupTransformableRotation(Transformable transformable, double alpha, double beta, double gamma) {
        final TransformableState state = transformable.getTransformableState();
        state.setRotationX(state.getRotationX() + alpha);
        state.setRotationY(state.getRotationY() + beta);
        state.setRotationZ(state.getRotationZ() + gamma);
    }
}
