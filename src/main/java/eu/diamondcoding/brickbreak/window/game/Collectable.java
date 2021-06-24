package eu.diamondcoding.brickbreak.window.game;

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

    public enum CollectableType {
        BIGGER_PADDLE,
        SMALLER_PADDLE,
        RED_MODE,
        TRIPPLE_BALLS,
        SPAWN_NEW_BALLS
    }

    private CollectableType randomCollectableType() {
        CollectableType[] values = CollectableType.values();
        Random random = new Random();
        return values[random.nextInt(values.length)];
    }

}
