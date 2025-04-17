public class Paddle {
    private int x;
    private int y;
    private int width;
    private int height;

    private static final int WINDOW_HEIGHT = 1700;
    private static final int WINDOW_WIDTH = 2700;

    public Paddle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
    public void moveTo(int x) {
        this.x = x;
        if (this.x < 0) {
            this.x = 0;
        } else if (this.x + width > WINDOW_WIDTH) {
            this.x = WINDOW_WIDTH - width;
        }
    }
}
