import java.awt.*;

public class Brick {
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean isDestroyed;
    private Color color;

    public Brick(int x, int y, int width, int height, Color color) {
    }

    public void hit() {}
    public boolean isDestroyed() {
        return false;
    }
    public Color getColor() {
        return null;
    }
}
