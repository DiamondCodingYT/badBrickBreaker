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

    public boolean collides(CollideBox otherBox) {
        return x < otherBox.x + otherBox.width &&
                x + width > otherBox.x &&
                y < otherBox.y + otherBox.height &&
                y + height > otherBox.y;
    }

    public Point getPoint() {
        return new Point(x, y);
    }

    public Point midPoint() {
        return new Point(
                x + width/2.0D,
                y + height/2.0D
        );
    }

    public void teleport(Point point) {
        x = point.getX();
        y = point.getY();
    }

    public CollideBox cloneBox() {
        return new CollideBox(x, y, width, height);
    }

    public String debugBox() {
        return "CollideBox{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    public class Point {

        private final double x, y;
        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }
        public double getY() {
            return y;
        }

    }

}
