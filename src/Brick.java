import java.awt.*;

public class Brick {
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean isDestroyed;
    private Color color;
    // constructor
    public Brick(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.isDestroyed = false;
    }
    // getters and setters
    public void hit() {
        isDestroyed = true;
    }
    public boolean isDestroyed() {
        return isDestroyed;
    }
    public Color getColor() {
        return color;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}
