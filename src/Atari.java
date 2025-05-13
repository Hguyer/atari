import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Atari implements ActionListener {
    // initilize constants and game elements
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
    private int level;
    private Image brickImage;
    private boolean levelPause;
    private int levelPauseCounter;

// constructor
    public Atari(int gameWidth, int gameHeight, int rows, int cols) {
        // add in the backend
        viewer = new AtariViewer(this);
        // make a new ball & paddle in the middle bricks at the top that change colors depending on the row, lives, score
        paddle = new Paddle(gameWidth / 2 - 40, gameHeight - 70, 150, 10);
        ball = new Ball(gameWidth / 2, gameHeight / 2, 20, 4.7, 4);
        score = 0;
        gameOver = false;
        // three lives
        lives = 3;
        level = 1;
        // the bricks
        bricks = new Brick[rows][cols];
        int brickHeight = 75;
        int brickWidth = 145;
        int spacing = 27;
        levelPause = false;
        levelPauseCounter = 0;
        // random image method later
        brickImage = loadRandomBrickImage();
        //draw the bricks but space them out
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // print out the bricks with spacing
                int brickX = j * (brickWidth + spacing) + spacing;
                int brickY = i * (brickHeight + spacing) + 50 + spacing;
                Color color;
                // switch case for the colors and opacity
                switch (i) {
                    case 0:
                        color = new Color(255, 0, 0, 100);
                        break;
                    case 1:
                        color = new Color(255, 255, 0, 100);
                        break;
                    case 2:
                        color = new Color(0, 255, 0, 100);
                        break;
                    default:
                        color = new Color(255, 0, 0, 100);
                }
                // initilize the bricks using the brick class
                bricks[i][j] = new Brick(brickX, brickY, brickWidth, brickHeight, color, brickImage);
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

    public int getLevel() {return level;}

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

    public boolean inBetweenLevels(){return levelPause;}

    // using the mouselistener in viewer update the paddle's x with the mouse, only the x
    public void updatePaddlePosition(int mouseX) {
        paddle.moveTo(mouseX - paddle.getWidth() / 2);
    }
    // use a random image, I labeled them 1-16 so math.random could be used
    private Image loadRandomBrickImage() {
        int randomImageIndex = (int) (Math.random() * 6);
        String imagePath = "Resources/" + randomImageIndex + ".jpeg";
        return new ImageIcon(imagePath).getImage();
    }

    // if the ball hits the paddle bounce off of it
    public void checkPaddleCollision() {
        Ball b = ball;
        Paddle p = paddle;

        // if the ball hits or is inside the paddle
        if (b.getX() + b.getDiameter() >= p.getX() &&
                b.getX() <= p.getX() + p.getWidth() &&
                b.getY() + b.getDiameter() >= p.getY() &&
                b.getY() <= p.getY() + p.getHeight()) {
            //randomness so it doesn't bounce where expected
            b.setDx(b.getDx() + (Math.random()* 11) - 5);
            b.setY(b.getY() - 8);
            b.setDy(-b.getDy() + (Math.random()* 7) - 3);
        }
    }
    // if it hits a wall bounce
    public void checkWallCollision() {
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

    public void checkBrickCollision() {
        Ball b = ball;
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[i].length; j++) {
                Brick brick = bricks[i][j];
                // check if it hits a brick
                if (!brick.isDestroyed()) {
                    if (b.getX() + b.getDiameter() >= brick.getX() &&
                            b.getX() <= brick.getX() + brick.getWidth() &&
                            b.getY() + b.getDiameter() >= brick.getY() &&
                            b.getY() <= brick.getY() + brick.getHeight()) {
                        brick.hit();

                        // top row is 100
                        if(i == 0){
                            score+= 100;
                        }
                        // middle is 50
                        else if (i == 1) {
                            score += 50;
                        }
                        // bottom is 25
                        else if (i == 2) {
                            score+= 25;
                        }

                        // bounce by reversing directions with some added randomness
                        if(b.getDx() > 0){
                            b.setDx(b.getDx() + ((Math.random()* 9.1) - 4.5));
                        }
                        else{
                            b.setDx(b.getDx() - ((Math.random()* 9.1) - 4.5));
                        }

                        if(b.getDy() > 0){
                            b.setDy(b.getDy() + (Math.random()*2));
                        }
                        else{
                            b.setDy(b.getDy() - (Math.random()*2));
                        }
                        b.setDy(-b.getDy());
                        return;
                    }
                }
            }
        }
    }
    // if all the bricks are destroyed return true to signal a new level
    public boolean allBricksDestroyed() {
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[i].length; j++) {
                if (!bricks[i][j].isDestroyed()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void startNewLevel() {
        // load a random image
        brickImage = loadRandomBrickImage();
        //reset the ball
        ball.resetPosition();
        level++;
        resetBall = true;
        // add in a 3 second pause
        levelPause = true;
        levelPauseCounter = 90;
        // Reset bricks
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[i].length; j++) {
                bricks[i][j].setImage(brickImage);
                bricks[i][j].reset();
            }
        }
        // slightly increase the speed
        ball.setDx(-4 * 1.1*level);
        ball.setDy(4 * 1.1*level);
    }

    // reset the position with a slightly slower dx and dy
    public void resetBall() {
        // reset the ball
        ball.setX(WINDOW_WIDTH / 2);
        ball.setY(WINDOW_HEIGHT / 2);
        ball.setDx((Math.random()* 11) - 5);
        ball.setDy(ball.getDy() * 0.9);
        resetBall = false;
        resetDelay = 0;
    }
    public void reset(){
        // load another image
        brickImage = loadRandomBrickImage();
        this.score = 0;
        this.lives = 3;

        // Reset the ball position and velocity
        ball.resetPosition();
        // Reset the paddle position
        paddle.resetPosition();

        // Reset all bricks (make them undestroyed)
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[i].length; j++) {
                bricks[i][j].setImage(brickImage);
                bricks[i][j].reset();
            }
        }
        this.levelPause = false;
        this.levelPauseCounter = 0;
        // Reset the game over flag
        this.gameOver = false;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // if they click the screen repaint
        if (viewer.welcomeScreen) {
            viewer.repaint();
            return;
        }
        // if in between levels do the pause
        if (levelPause) {
            levelPauseCounter--;
            if (levelPauseCounter <= 0) {
                levelPause = false;
                resetBall = true;
                resetDelay = 66;
            }
            viewer.repaint();
            return;
        }
        // move if the game isn't over and the ball isn't reset
        if (!gameOver && !resetBall) {
            if (allBricksDestroyed()) {
                startNewLevel();
            }
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
        Atari game = new Atari(WINDOW_WIDTH, WINDOW_HEIGHT, 3, 7);
    }
}
