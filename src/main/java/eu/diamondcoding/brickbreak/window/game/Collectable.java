package eu.diamondcoding.brickbreak.window.game;

import java.awt.*;
import java.util.Random;

public class Collectable extends CollideBox {

    CollectableType type;

    public Collectable(double x, double y) {
        super(x, y, 30, 30);
        type = randomCollectableType();
    }

    public void updateLoc(double deltaS) {
        y += 250.0D * deltaS;
    }

    public void draw(Graphics g) {
        switch (type) {
            case BIGGER_PADDLE -> {
                g.setColor(Color.green);
                g.drawRect((int) x, (int) y + height / 4, width, height / 3);
            }
            case SMALLER_PADDLE -> {
                g.setColor(Color.red);
                g.drawRect((int) x, (int) y + height / 4, width, height / 3);
            }
            case RED_MODE -> {
                g.setColor(Color.red);
                g.drawOval((int) x + width/4, (int) y + height/4, width/2, height/2);
            }
            case TRIPPLE_BALLS -> {
                g.setColor(Color.white);
                g.drawOval((int) x+width/4, (int) y, width/2, height/2);
                g.drawOval((int) x, (int) y+height/2, width/2, height/2);
                g.drawOval((int) x+width/2, (int) y+height/2, width/2, height/2);
            }
            case SPAWN_NEW_BALLS -> {
                g.setColor(Color.green);
                g.drawOval((int) x, (int) y+height/6, width/3, height/3);
                g.drawOval((int) x+width/3, (int) y+height/6, width/3, height/3);
                g.drawOval((int) x+(width/3)*2, (int) y+height/6, width/3, height/3);
            }
            case SHOOTER -> {
                g.setColor(Color.white);
                //left shooter
                g.drawRect((int) x, (int) y+4*(height/6), width/6, height/6);
                //right shooter
                g.drawRect((int) x+5*(height/6), (int) y+4*(height/6), width/6, height/6);
                //paddle
                g.drawRect((int) x, (int) y+5*(height/6), width, height/6);
            }
        }
    }

    public enum CollectableType {
        BIGGER_PADDLE,
        SMALLER_PADDLE,
        RED_MODE,
        TRIPPLE_BALLS,
        SPAWN_NEW_BALLS,
        SHOOTER
    }

    private CollectableType randomCollectableType() {
        CollectableType[] values = CollectableType.values();
        Random random = new Random();
        return values[random.nextInt(values.length)];
    }

}
