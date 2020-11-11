package model;

/**
 * An instance of interface {@code TextureRenderable}
 *
 * @author Stanislav Čapek
 * @version 1.0
 */
public interface TextureRenderable extends Renderable {

    Texture2D getTexture();

    void setTexture(String file);

}
