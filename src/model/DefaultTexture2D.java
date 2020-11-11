package model;

import transforms.Col;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * An instance of class {@code DefaultTexture2D}
 *
 * @author Stanislav Čapek
 * @version 1.0
 */
public class DefaultTexture2D implements Texture2D {

    private BufferedImage img;

    public DefaultTexture2D(String file) {

        final InputStream is = this.getClass().getClassLoader().getResourceAsStream(file);
        if (is == null) {
            throw new RuntimeException("File not found: " + file);
        }

        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to read image");
        }
    }

//    @Override
//    public Vec3D getColorVec(int x, int y) {
//        int rgb = img.getRGB(x, y);
//
//        int red = (rgb >> 16) & 0xFF;
//        int green = (rgb >> 8) & 0xFF;
//        int blue = rgb & 0xFF;
//
//        return new Vec3D(red / 255d, green/255d, blue/255d);
//    }

    // test
    public static void main(String[] args) {
        System.out.println("Testing texture:");
        final DefaultTexture2D texture2D = new DefaultTexture2D("textures/cube-test-tex.jpg");
        System.out.println("texture2D.getWidth() = " + texture2D.getWidth());
        System.out.println("texture2D.getHeight() = " + texture2D.getHeight());
//        final Vec3D colorVec = texture2D.getColorVec(990, 990);
        final Col col = texture2D.getColor(990, 990);
        System.out.println("texture2D.getColorVec(990, 990) = " + col);
        System.out.println("colorVec.getX() = " + col);

    }

    @Override
    public Col getColor(int x, int y) {
        return new Col(img.getRGB(x, y));
    }

    @Override
    public int getWidth() {
        return img.getWidth();
    }

    @Override
    public int getHeight() {
        return img.getHeight();
    }
}
