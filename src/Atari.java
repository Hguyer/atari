public class Atari {
    private Paddle paddle;
    private Ball ball;
    private Brick[][] bricks;
    private int score;
    private int lives;
    private AtariViewer viewer;
    private static final int WINDOW_HEIGHT = 1700;
    private static final int WINDOW_WIDTH = 2700;

    public Atari(int gameWidth, int gameHeight, int rows, int cols) {
        viewer = new AtariViewer(this);
        // make a new ball & paddle in the middle
        paddle = new Paddle(gameWidth / 2 - 40, gameHeight - 30, 100, 10);
        ball = new Ball(gameWidth / 2, gameHeight / 2, 20, 2, 3);
        score = 0;
        lives = 3;
        bricks = new Brick[rows][cols];
    }
    public Paddle getPaddle() {
        return paddle;
    }

    public Ball getBall() {
        return ball;
    }

    public Brick[][] getBricks() {
        return bricks;
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }
    public static void main(String[] args) {
        Atari atari = new Atari(WINDOW_WIDTH, WINDOW_HEIGHT, 4, 9);
        new AtariViewer(atari);
    }
}
