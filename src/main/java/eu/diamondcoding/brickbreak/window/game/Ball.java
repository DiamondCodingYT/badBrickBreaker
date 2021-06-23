package eu.diamondcoding.brickbreak.window.game;

public class Ball extends CollideBox {

    double vx, vy;

    public Ball(double x, double y, double vx, double vy) {
        super(x, y, 15, 15);
        this.vx = vx;
        this.vy = vy;
    }

    public void updateLoc(double deltaS) {
        x += vx * deltaS;
        y += vy * deltaS;
    }

}
