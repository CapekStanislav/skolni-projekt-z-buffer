package model;

/**
 * Instance of enum {@code Topology} describe  s part of
 * the object's topology and how should be rendered.
 *
 * @author Stanislav Čapek
 * @version 1.0
 */
public enum Topology {

    /**
     * Separate points
     */
    POINTS,
    /**
     * Separate lines
     */
    LINE_LIST,
    /**
     * Together joined lines
     */
    LINE_STRIP,
    /**
     * Together joined lines. Starting point and
     * ending point are joined as well.
     */
    LINE_LOOP,
    /**
     * Separate triangles
     */
    TRIANGLES,
    /**
     * Together joined triangle
     */
    TRIANGLE_STRIP,
    /**
     * First index is origin followed by vertices to connect
     */
    TRIANGLE_FAN

}
