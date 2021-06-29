package eu.diamondcoding.brickbreak.window.game;

import java.awt.*;

public class Ball extends CollideBox {

    double vx, vy;
    private boolean queuedForRemove = false;

    public Ball(double x, double y, double vx, double vy) {
        super(x, y, 15, 15);
        this.vx = vx;
        this.vy = vy;
    }

    public void updateLoc(double deltaS) {
        x += vx * deltaS;
        y += vy * deltaS;
    }

    public void queueRemove() {
        queuedForRemove = true;
    }

    public boolean isQueuedForRemove() {
        return queuedForRemove;
    }

    public void draw(Graphics g, boolean debugMode, boolean redMode) {
        if(debugMode) {
            double predictedX = x + vx * 0.5D;
            double predictedY = y + vy * 0.5D;
            g.setColor(Color.blue);
            g.drawLine((int)x+width/2, (int)y+height/2, (int)predictedX+width/2, (int)predictedY+height/2);
        }
        g.setColor(redMode ? Color.red : Color.white);
        g.fillOval((int)x, (int)y, width, height);
    }

}
