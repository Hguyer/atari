public class Ball {

    private double x;
    private double y;
    private double dx;
    private double dy;
    private int diameter;

    public Ball(double x, double y, int diameter, double dx, double dy) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.dx = dx;
        this.dy = dy;
    }
    public void move() {
        x += dx;
        y += dy;
    }
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

}
