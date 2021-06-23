package eu.diamondcoding.brickbreak.window.game;

public class CollideBox {

    double x, y;
    int width, height;

    public CollideBox(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public String debugBox() {
        return "CollideBox{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

}
