public class Ball {

    private double x;
    private double y;
    private double dx;
    private double dy;
    private int diameter;
    private static final int WINDOW_HEIGHT = 900;
    private static final int WINDOW_WIDTH = 1250;
// constructor
    public Ball(double x, double y, int diameter, double dx, double dy) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.dx = dx;
        this.dy = dy;
    }
    // move
    public void move() {
        x += dx;
        y += dy;
    }
    // getters and setters
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }

    // reset to the middle
    public void resetPosition() {
        this.x = WINDOW_WIDTH / 2;
        this.y = WINDOW_HEIGHT / 2;
        this.dx = (Math.random() * 5.1) - 2.5;
        this.dy = (Math.random() * 10.1) - 5;
    }
}
