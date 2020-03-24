package controller;

import model.*;
import model.primitives.Line;
import model.transformation.Animation;
import model.transformation.Transformable;
import model.transformation.Transformer;
import renderer.GPURenderer;
import renderer.SurfaceGPURenderer;
import renderer.WireframeGPURenderer;
import transforms.*;
import view.InfoPanel;
import view.Raster;

import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Controls rendering and interaction in application.
 *
 * @author Milan Košťák, Stanislav Čapek
 * @version 1.5
 */
public class Controller3D {

    private final List<Animation> animations = new ArrayList<>();
    private final List<Renderable> renderables = new ArrayList<>();
    private final double moveStep = 1d;
    private final double rotateStep = 10d;
    private final Renderable[] axises;
    private final Camera defCamera;
    private GPURenderer renderer;
    private Mat4 model, projection, persMat, orthoMat;
    private Camera camera;
    private boolean toggleAnimate;


    public Controller3D(Raster raster) {
        this.renderer = new SurfaceGPURenderer(raster);

        model = new Mat4Identity(); // jednotková matice -> nic se s tělesem nestane

        camera = new Camera()
                .withPosition(new Vec3D(5.17, -10.6, 7.55))
                .withAzimuth(1.92)
                .withZenith(-0.52);
        defCamera = new Camera(camera, true);

        final int width = raster.getWidth();
        final int height = raster.getHeight();

        persMat = new Mat4PerspRH(Math.PI / 3, height / (float) width, 0.1, 50);
        orthoMat = new Mat4OrthoRH(width / 100d, height / 100d, 0.1, 50);
        projection = persMat;

        final Line axisX = new Line(new Point3D(), new Point3D(2, 0, 0), Color.RED);
        final Line axisY = new Line(new Point3D(), new Point3D(0, 2, 0), Color.GREEN);
        final Line axisZ = new Line(new Point3D(), new Point3D(0, 0, 2), Color.BLUE);
        axises = new Renderable[]{axisX, axisY, axisZ};

        createObjectsAndAnimations();
        initListeners(raster);
        display();
    }

    private void display() {
        renderer.clear();

        renderer.setView(camera.getViewMatrix());
        renderer.setProjection(projection);

        // vykreslení os
        renderer.setModel(new Mat4Identity());
        renderer.draw(axises);

        // vykreslení ostatních těles
        renderer.draw(renderables.toArray(Renderable[]::new));
    }


    private void initListeners(Raster raster) {
        raster.grabFocus();

//        ovládání klávesnicí
        raster.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getExtendedKeyCode()) {
                    case KeyEvent.VK_W:
                        camera = camera.forward(moveStep);
                        break;
                    case KeyEvent.VK_S:
                        camera = camera.backward(moveStep);
                        break;
                    case KeyEvent.VK_A:
                        camera = camera.left(moveStep);
                        break;
                    case KeyEvent.VK_D:
                        camera = camera.right(moveStep);
                        break;
                    case KeyEvent.VK_LEFT:
                        double az = camera.getAzimuth() + Math.toRadians(rotateStep);
                        camera = camera.withAzimuth(az);
                        break;
                    case KeyEvent.VK_RIGHT:
                        double az2 = camera.getAzimuth() - Math.toRadians(rotateStep);
                        camera = camera.withAzimuth(az2);
                        break;
                    case KeyEvent.VK_UP:
                        double zen1 = camera.getZenith() - Math.toRadians(rotateStep);
                        if (Math.toDegrees(zen1) < -90) {
                            zen1 = Math.toRadians(-90);
                        }
                        camera = camera.withZenith(zen1);
                        break;
                    case KeyEvent.VK_DOWN:
                        double zen2 = camera.getZenith() + Math.toRadians(rotateStep);
                        if (Math.toDegrees(zen2) > 90) {
                            zen2 = Math.toRadians(90);
                        }
                        camera = camera.withZenith(zen2);
                        break;
                    case KeyEvent.VK_SHIFT:
                        camera = camera.up(moveStep);
                        break;
                    case KeyEvent.VK_CONTROL:
                        camera = camera.down(moveStep);
                        break;
                    case KeyEvent.VK_SPACE:
                        camera = defCamera;
                        break;
                    case KeyEvent.VK_V:
                        changeRendere(raster);
                        break;
                    case KeyEvent.VK_C:
                        changeProjection();
                        break;
                    case KeyEvent.VK_F1:
                        showInfoWindow();
                        break;
                    case KeyEvent.VK_P:
                        if (toggleAnimate) {
                            raster.setTextAnimation("Animation: stopped");
                            toggleAnimate = false;
                        } else {
                            raster.setTextAnimation("Animation: playing");
                            toggleAnimate = true;
                        }
                        break;
//                    case KeyEvent.VK_SPACE:
//                        camera = defaultCamera;
//                        break;
//                    case KeyEvent.VK_C:
//                        if (!toggleProjection) {
//                            projections = new Mat4OrthoRH(8, 60, 0.1, 100);
//                            toggleProjection = true;
//                        } else {
//                            projections = defaultProjetions;
//                            toggleProjection = false;
//                        }
//                        break;
//                    case KeyEvent.VK_P:
//                        if (toggleAnimate) {
//                            raster.setTextAnimation("Animation: stopped");
//                            toggleAnimate = false;
//                        } else {
//                            raster.setTextAnimation("Animation: playing");
//                            toggleAnimate = true;
//                        }
//                        break;
//                    case KeyEvent.VK_F1:
//                        JOptionPane.showMessageDialog(
//                                null,
//                                new InfoPanel(),
//                                "Controls",
//                                JOptionPane.PLAIN_MESSAGE
//                        );
//                        break;

                }
                display();
            }
        });
//        ovládání myší
        raster.addMouseMotionListener(new MouseAdapter() {
            double lastX = -1;
            double lastY = -1;
            final int speed = 8;

            @Override
            public void mouseMoved(MouseEvent e) {
                lastX = e.getX();
                lastY = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (lastX < 0 || lastY < 0) {
                    lastX = e.getX();
                    lastY = e.getY();
                }
                double dx = lastX - e.getX();
                double dy = lastY - e.getY();

                double zenith = Math.toDegrees(camera.getZenith());
                zenith += dy / speed;
                if (zenith > 90) {
                    zenith = 90;
                }
                if (zenith < -90) {
                    zenith = -90;
                }

                double azimut = Math.toDegrees(camera.getAzimuth());
                azimut += dx / speed;
//                azimut = azimut % 360;

                camera = camera.withZenith(Math.toRadians(zenith)).withAzimuth(Math.toRadians(azimut));
                lastX = e.getX();
                lastY = e.getY();
                display();
            }

        });
//        fokus myši
        raster.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                raster.grabFocus();
            }
        });

//        animace
        raster.addPropertyChangeListener("fps", this::runAnimation);
    }

    private void runAnimation(PropertyChangeEvent evt) {
        if (toggleAnimate) {
            boolean isAnimationDone = true;
            final int fps = (int) evt.getNewValue();
            for (Animation animation : animations) {
                animation.doAllAnimations(fps);
                if (isAnimationDone) {
                    isAnimationDone = animation.isDone();
                }
            }
            if (isAnimationDone) {
                createObjectsAndAnimations();
            }
        }
        display();

    }

    private void createObjectsAndAnimations() {
        animations.clear();
        renderables.clear();
        final Transformer t = new Transformer();

        final Cube cube = new Cube();
        cube.getTransformableState().setCenter(new Point3D(0, 0, 1));
        t.move(cube, -5, 5, 0);
        renderables.add(cube);
        animations.add(createAnimationForCube(cube));

        final Cylinder cylinder = new Cylinder(12, 3);
        t.move(cylinder, 3, 3, 0);
        renderables.add(cylinder);

        final Pyramid pyramid1 = new Pyramid(Color.WHITE, Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW);
        t.move(pyramid1, 0, 0, 3);
        t.rotateByCenter(pyramid1, 0, Math.toRadians(180), 0);
        renderables.add(pyramid1);
        animations.add(createAnimationForColoredPyramid(pyramid1));

        final Pyramid pyramid = new Pyramid();
        t.rotate(pyramid, 0, Math.toRadians(90), 0);
        t.move(pyramid, -2, 0, 0);
        pyramid.getTransformableState().setCenter(new Point3D());
        renderables.add(pyramid);
        animations.add(createAnimationForPyramid(pyramid));

        final Sector sector = new Sector(15);
        t.move(sector, 3, 0, 0);
        renderables.add(sector);
    }

    /**
     * Creates specific animation for colored cube
     *
     * @param cube cube to animate
     * @return new animation
     */
    private Animation createAnimationForCube(Transformable cube) {
        Animation animation = new Animation(cube);
        final double fullRoll = Math.toRadians(360);
        animation.addAnimation(
                animation.setMove(10, 0, 0).setRotionByCenter(0, fullRoll, 0)
        );
        animation.addAnimation(
                animation.setMove(0, -10, 0).setRotionByCenter(fullRoll, 0, 0)
        );
        animation.addAnimation(
                animation.setMove(-10, 0, 0).setRotionByCenter(0, -fullRoll, 0)
        );
        animation.addAnimation(
                animation.setMove(0, 10, 0).setRotionByCenter(-fullRoll, 0, 0)
        );
        return animation;
    }

    /**
     * Creates specific animation for colored pyramid (each side with diff color)
     *
     * @param pyramid pyramid to animate
     * @return new animation
     */
    private Animation createAnimationForColoredPyramid(Transformable pyramid) {
        Animation animation = new Animation(pyramid);
        for (int i = 0; i < 4; i++) {
            animation.addAnimation(
                    animation.setRotionByCenter(0, 0, Math.toRadians(-90))
            );
        }
        return animation;
    }

    /**
     * Creates specifi animation for multicolored pyramid
     *
     * @param pyramid pyramid to animate
     * @return new animation
     */
    private Animation createAnimationForPyramid(Transformable pyramid) {
        Animation animation = new Animation(pyramid);
        for (int i = 0; i < 4; i++) {
            animation.addAnimation(
                    animation.setRotionByCenter(Math.toRadians(90), 0, 0)
                            .setScaleByCenter(5, 5, 5)
            );
        }
        return animation;
    }

    private void showInfoWindow() {
        JOptionPane.showMessageDialog(
                null,
                new InfoPanel(),
                "Information",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    /**
     * It switches between surface and wireframe renderer.
     *
     * @param raster canvas to paint
     */
    private void changeRendere(Raster raster) {
        renderer = renderer instanceof SurfaceGPURenderer ?
                new WireframeGPURenderer(raster) : new SurfaceGPURenderer(raster);
        display();
    }

    /**
     * It switches between orthogonal and perspective projection
     */
    private void changeProjection() {
        projection = projection instanceof Mat4PerspRH ? orthoMat : persMat;
        display();
    }


}
