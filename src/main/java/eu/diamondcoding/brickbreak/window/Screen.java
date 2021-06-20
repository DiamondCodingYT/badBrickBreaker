package eu.diamondcoding.brickbreak.window;

import eu.diamondcoding.brickbreak.window.utils.ScreenButton;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Screen {

    public List<ScreenButton> buttonList = new ArrayList<>();
    public SceneHolderComponent holder;

    public void init() {}

    public void onHide() {}

    public void handleClick(Point mousePoint) {
        onClick(mousePoint);
        for (ScreenButton button : buttonList) {
            button.handleClick(mousePoint);
        }
    }

    public void onClick(Point mousePoint) {}

    public void paint(double deltaS, Dimension dimension, Point mousePoint, Graphics g) {
        draw(deltaS, dimension, mousePoint, g);
        for (ScreenButton button : buttonList) {
            button.paint(g, mousePoint);
        }
    }

    public abstract void draw(double deltaS, Dimension dimension, Point mousePoint, Graphics g);

}
