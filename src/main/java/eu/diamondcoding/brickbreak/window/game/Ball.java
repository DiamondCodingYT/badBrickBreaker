package eu.diamondcoding.brickbreak.window.game;

public class Ball {

    int diameter = 15;
    double x, y;
    double vx, vy;

    public Ball(double x, double y, double vx, double vy) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
    }

    public void updateLoc(double deltaS) {
        x += vx * deltaS;
        y += vy * deltaS;
    }

}
