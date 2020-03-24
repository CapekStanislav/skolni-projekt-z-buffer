package view;

import javax.swing.*;

/**
 * Instance of class {@code InfoPanel}. For showing information about
 * controlling application and author.
 *
 * @author Stanislav Čapek
 * @version 1.0
 */
public class InfoPanel extends JPanel {
    public InfoPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        final String htmlOpen = "<html>";

        String mouse = htmlOpen +
                "CLICK & DRAGG - look around";
        this.add(new JLabel(mouse));

        insertSpace();

        String wsad = htmlOpen +
                "W - forward <br>" +
                "S - backward <br>" +
                "A - left <br>" +
                "D - right";
        this.add(new JLabel(wsad));

        insertSpace();

        String arrows = htmlOpen +
                "UP - turn up <br>" +
                "DOWN - turn down <br>" +
                "LEFT - turn left <br>" +
                "RIGHT - turn right <br>";
        this.add(new JLabel(arrows));

        insertSpace();

        String other = htmlOpen +
                "SHIFT - move up <br>" +
                "CTRL - move down <br>" +
                "C - change view persp./ortho. <br>" +
                "V - change view surface/wireframe <br>" +
                "SPACE - reset camera <br>" +
                "P - start/stop animation";
        this.add(new JLabel(other));

        this.add(Box.createVerticalStrut(10));
        this.add(new JSeparator(JSeparator.HORIZONTAL));


        String author = htmlOpen + "Author: Stanislav Čapek \u00A92020- UHK FIM project";
        this.add(new JLabel(author));
    }

    private void insertSpace() {
        this.add(Box.createVerticalStrut(10));
        this.add(new JSeparator(JSeparator.HORIZONTAL));
        this.add(Box.createVerticalStrut(10));
    }
}
