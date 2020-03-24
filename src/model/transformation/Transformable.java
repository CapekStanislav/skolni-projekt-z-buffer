package model.transformation;

import model.Vertex;

import java.util.List;

/**
 * Instance rozhraní {@code Transformable} představují objekty, které o sobě dokáží
 * prozradit základní geometrické vlastnosti a provedené transformace.
 *
 * @author Stanislav Čapek
 * @version 1.0
 */
public interface Transformable {

    /**
     * Vrátí seznam vrcholů objektu
     *
     * @return seznam vrcholů
     */
    List<Vertex> getVertices();

    /**
     * Vrátí tranforamční stav
     *
     * @return transformační stav
     */
    TransformableState getTransformableState();


}
