package eu.diamondcoding.brickbreak.window.game;

import java.awt.*;

public class Brick {

    int x, y;
    int width, height;
    Color color, outlineColor;
    boolean permanent;

    public Brick(int x, int y, int width, int height, Color color) {
        this(x, y, width, height, color, Color.black, false);
    }

    public Brick(int x, int y, int width, int height, Color color, Color outlineColor, boolean permanent) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.outlineColor = outlineColor;
        this.permanent = permanent;
    }

}
