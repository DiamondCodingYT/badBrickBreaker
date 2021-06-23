package eu.diamondcoding.brickbreak.window;

import javax.swing.*;
import java.awt.*;

public class SceneHolderComponent extends JComponent {

    Screen activeScene;

    public SceneHolderComponent(Screen initialScreen) {
        displayScreen(initialScreen);
    }

    public void displayScreen(Screen screen) {
        if(activeScene != null) activeScene.onHide();
        screen.holder = this;
        screen.init();
        activeScene = screen;
    }

    public void handleClick() {
        activeScene.handleClick(getMousePosition());
    }

    public void handleKeyPress(int keyCode) {
        activeScene.handleKeyPress(keyCode);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(500, 650);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 650);
    }

    long lastDraw = 0L;
    @Override
    public void paintComponent(Graphics g) {
        //get elapsed
        long elapsed;
        if(lastDraw > 0L) {
            elapsed = System.currentTimeMillis() - lastDraw;
        } else {
            elapsed = 0;
        }
        lastDraw = System.currentTimeMillis();
        double deltaS = elapsed / 1000.0D;

        Dimension dimension = getSize();
        super.paintComponent(g);

        activeScene.paint(deltaS, dimension, getMousePosition(), g);
    }

}