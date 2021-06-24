package eu.diamondcoding.brickbreak.window.game;

public class Bullet extends CollideBox {

    public Bullet(double x, double y) {
        super(x, y, 8, 10);
    }

    public void updateLoc(double deltaS) {
        y += -250.0D * deltaS;
    }

}
