package view;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Instance of class {@code Raster}. Canvas (graphic context)
 *
 * @author Milan Košťák, Stanislav Čapek
 * @version 1.5
 */
public class Raster extends JPanel {

    private final BufferedImage img; // objekt pro zápis pixelů
    private final Graphics g; // objekt nad kterým jsou k dispozici grafické funkce
    private static final int FPS = 1000 / 30;
    private final int width;
    private final int height;
    private String textAnimation = "";


    /**
     * @param width  of raster
     * @param height of raster
     */
    public Raster(int width, int height) {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        // inicializace image, nastavení rozměrů (nastavení typu - pro nás nedůležité)
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        g = img.getGraphics();
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        setLoop();
        clear();
    }

    /**
     * with defaul size {@code width} 800 and {@code height} 600
     */
    public Raster() {
        this(800, 600);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
        firePropertyChange("fps", 0, FPS);
        // pro zájemce - co dělá observer - https://stackoverflow.com/a/1684476
    }

    private void setLoop() {
        // časovač, který 30 krát za vteřinu obnoví obsah plátna aktuálním img
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        }, 0, FPS);
    }

    public void clear() {
        final GradientPaint gradientPaint = new GradientPaint(
                0, 0, Color.WHITE,
                0, ((float) (height * 0.75)), Color.LIGHT_GRAY);
        ((Graphics2D) g).setPaint(gradientPaint);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        g.drawString("F1 - HELP", 15, 15);
        g.drawString(textAnimation, 15, 45);

    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setTextAnimation(String textAnimation) {
        this.textAnimation = textAnimation;
    }

    public void drawPixel(int x, int y, int color) {
        img.setRGB(x, y, color);
    }

    public int getPixelColor(int x, int y) {
        return img.getRGB(x, y);
    }

    public void drawLine(int x1, int y1, int x2, int y2, Color color) {
        g.setColor(color);
        g.drawLine(x1, y1, x2, y2);
    }
}
