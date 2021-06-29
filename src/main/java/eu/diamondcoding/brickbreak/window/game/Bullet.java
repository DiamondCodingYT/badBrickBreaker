package eu.diamondcoding.brickbreak.window.game;

import java.awt.*;

public class Bullet extends CollideBox {

    private boolean queuedForRemove = false;

    public Bullet(double x, double y) {
        super(x, y, 8, 10);
    }

    public void updateLoc(double deltaS) {
        y += -250.0D * deltaS;
    }

    public void queueRemove() {
        queuedForRemove = true;
    }

    public boolean isQueuedForRemove() {
        return queuedForRemove;
    }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillRect((int) x, (int)y, width, height);
    }

}
