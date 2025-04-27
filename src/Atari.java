import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Atari implements ActionListener {
    private static final int DELAY = 30;
    private Paddle paddle;
    private Ball ball;
    private Brick[][] bricks;
    private int score;
    private int lives;
    private AtariViewer viewer;
    private static final int WINDOW_HEIGHT = 900;
    private static final int WINDOW_WIDTH = 1250;
    private boolean resetBall; // Flag to indicate ball reset
    private int resetDelay;
    private boolean gameOver;
    public Atari(int gameWidth, int gameHeight, int rows, int cols) {
        // add in the backend
        viewer = new AtariViewer(this);
        // make a new ball & paddle in the middle bricks at the top that change colors depending on the row, lives, score
        paddle = new Paddle(gameWidth / 2 - 40, gameHeight - 70, 150, 10);
        ball = new Ball(gameWidth / 2, gameHeight / 2, 20, 4.7, 9);
        score = 0;
        gameOver = false;
        lives = 3;
        bricks = new Brick[rows][cols];
        int brickHeight = 60;
        int brickWidth = 125;
        int spacing = 12;
        //draw the bricks but space them out
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int brickX = j * (brickWidth + spacing) + spacing;
                int brickY = i * (brickHeight + spacing) + 50 + spacing;
                Color color;
                // switch case for the colors
                switch (i) {
                    //top
                    case 0:
                        color = Color.RED;
                        break;
                    case 1: // Third from bottom
                        color = Color.ORANGE;
                        break;
                    case 2: // Second from bottom
                        color = Color.YELLOW;
                        break;
                    case 3: // Bottom row
                        color = Color.GREEN;
                        break;
                    default:
                        color = Color.RED;
                }
                bricks[i][j] = new Brick(brickX, brickY, brickWidth, brickHeight, color);
            }
        }
        // add in a reset variable that if true resets the game
        resetBall = false;
        // delay the reset
        resetDelay = 0;
        // add a times to run on 30FPS
        Timer clock = new Timer(DELAY, this);
        clock.start();
    }
    // getters and setters
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
    public boolean isGameOver() {
        return gameOver;
    }
    // using the mouselistener in viewer update the paddle's x according to
    public void updatePaddlePosition(int mouseX) {
        paddle.moveTo(mouseX - paddle.getWidth() / 2);
    }
    // if the ball hits the paddle bounce off of it
    public void checkPaddleCollision() {
        Ball b = ball;
        Paddle p = paddle;

        if (b.getX() + b.getDiameter() >= p.getX() &&
                b.getX() <= p.getX() + p.getWidth() &&
                b.getY() + b.getDiameter() >= p.getY() &&
                b.getY() <= p.getY() + p.getHeight()) {
            //randomness so it doesn't bounce where expected
            b.setDx(b.getDx() + (Math.random()* 5.1) - 2.5);
            b.setDy(-b.getDy());
        }
    }
    // if it hits a wall bounce
    private void checkWallCollision() {
        Ball b = ball;
        if (b.getX() <= 0 || b.getX() + b.getDiameter() >= WINDOW_WIDTH) {
            b.setDx(-b.getDx());
            if (b.getX() <= 0) b.setX(0);
            if (b.getX() + b.getDiameter() >= WINDOW_WIDTH) b.setX(WINDOW_WIDTH - b.getDiameter());
        }
        // titlebar
        if (b.getY() <= 23) {
            b.setY(23);
            b.setDy(-b.getDy());
        }
        // if it hits the bottom take away a life wait 2 seconds then continue
        if (b.getY() + b.getDiameter() >= WINDOW_HEIGHT) {
            lives--;
            if (lives <= 0) {
                gameOver = true;
            } else {
                resetBall = true;
                resetDelay = 66;
            }
        }
    }

    private void checkBrickCollision() {
        Ball b = ball;
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[i].length; j++) {
                Brick brick = bricks[i][j];
                if (!brick.isDestroyed()) {
                    if (b.getX() + b.getDiameter() >= brick.getX() &&
                            b.getX() <= brick.getX() + brick.getWidth() &&
                            b.getY() + b.getDiameter() >= brick.getY() &&
                            b.getY() <= brick.getY() + brick.getHeight()) {
                        brick.hit();

                        if(i == 0){
                            score+= 100;
                        }
                        else if (i == 1) {
                            score += 50;
                        }
                        else if (i == 2) {
                            score+= 25;
                        }
                        else {
                            score+=10;
                        }
                        if(b.getDx() > 0){
                            b.setDx(b.getDx() + ((Math.random()* 5.1) - 2.5));
                        }
                        else{
                            b.setDx(b.getDx() - ((Math.random()* 5.1) - 2.5));
                        }

                        if(b.getDy() > 0){
                            b.setDx(b.getDx() + (Math.random()));
                        }
                        else{
                            b.setDy(b.getDy() - (Math.random()));
                        }
                        b.setDy(-b.getDy());
                        return;
                    }
                }
            }
        }
    }

    // reset the position with a random dx
    private void resetBall() {

        ball.setX(WINDOW_WIDTH / 2);
        ball.setY(WINDOW_HEIGHT / 2);
        ball.setDx((int) ((Math.random() * 25) - 12));
        ball.setDy(ball.getDy());
        resetBall = false;
        resetDelay = 0;
    }
    public void reset(){
        this.score = 0;
        this.lives = 3; // or however many lives you start with

        // Reset the ball position and velocity
        ball.resetPosition(); // assuming your Ball class has a resetPosition() method

        // Reset the paddle position
        paddle.resetPosition(); // assuming your Paddle class has a resetPosition() method

        // Reset all bricks (make them undestroyed)
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[i].length; j++) {
                bricks[i][j].reset(); // assuming Brick class has a reset() method that marks it as not destroyed
            }
        }

        // Reset the game over flag
        this.gameOver = false;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (viewer.welcomeScreen) {
            viewer.repaint();
            return; // Do nothing until the player clicks
        }
        // move if the game isn't over and the ball isn't reset
        if (!gameOver && !resetBall) {
            ball.move();
            checkWallCollision();
            checkPaddleCollision();
            checkBrickCollision();
        }
        //otherwise decrease the reset delay until the ball resets
        else if (!gameOver && resetDelay > 0) {
            resetDelay--;
        }
        // otherwise if the game isn't over reset the ball
        else if (!gameOver) {
            resetBall();
        }
        viewer.repaint();
    }

    public static void main(String[] args) {
        Atari game = new Atari(WINDOW_WIDTH, WINDOW_HEIGHT, 4, 9);
    }
}
