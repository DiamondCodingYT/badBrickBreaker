package eu.diamondcoding.brickbreak.window.game;

import java.awt.*;

public class Brick extends CollideBox {

    Color color, outlineColor;
    boolean permanent;

    public Brick(int x, int y, int width, int height, Color color) {
        this(x, y, width, height, color, Color.black, false);
    }

    public Brick(int x, int y, int width, int height, Color color, Color outlineColor, boolean permanent) {
        super(x, y, width, height);
        this.color = color;
        this.outlineColor = outlineColor;
        this.permanent = permanent;
    }

}
