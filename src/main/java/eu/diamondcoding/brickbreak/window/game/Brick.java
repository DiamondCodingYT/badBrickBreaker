package eu.diamondcoding.brickbreak.window.game;

import java.awt.*;

public class Brick extends CollideBox {

    Color color, outlineColor;
    boolean permanent;
    private boolean queuedForRemove = false;

    public Brick(int x, int y, int width, int height, Color color) {
        this(x, y, width, height, color, Color.black, false);
    }

    public Brick(int x, int y, int width, int height, Color color, Color outlineColor, boolean permanent) {
        super(x, y, width, height);
        this.color = color;
        this.outlineColor = outlineColor;
        this.permanent = permanent;
    }

    public void queueRemove() {
        queuedForRemove = true;
    }

    public boolean isQueuedForRemove() {
        return queuedForRemove;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect((int)x, (int)y, width, height);
        if(permanent) {
            g.setColor(Color.yellow);
            Graphics2D g2 = (Graphics2D) g;
            Stroke oldStroke = g2.getStroke();
            g2.setStroke(new BasicStroke(1.5F));
            g.drawRect((int)x, (int)y, width, height);
            g.drawLine((int)x+1, (int)y+1, (int)x+width-2, (int)y+height-2);
            g.drawLine((int)x+width-2, (int)y+1, (int)x+1, (int)y+height-2);
            g2.setStroke(oldStroke);
        }
    }
    
}
