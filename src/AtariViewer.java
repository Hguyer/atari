import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class AtariViewer extends JFrame {
    private Atari game;
    private static final int WINDOW_HEIGHT = 900;
    private static final int WINDOW_WIDTH = 1250;
    boolean welcomeScreen = true;
    private Image openingImage;



    public AtariViewer(Atari game) {
        this.game = game;
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        openingImage = new ImageIcon("Resources/Opening.png").getImage();
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                game.updatePaddlePosition(e.getX());
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (welcomeScreen) {
                    welcomeScreen = false;
                }
                else if (game.isGameOver()) {
                    game.reset();  // <- assuming you have a reset method
                    welcomeScreen = false;
                    repaint();
                }
            }
        });

        this.setVisible(true);

        createBufferStrategy(2);
    }

    @Override
    public void paint(Graphics g) {
        Image offImage = createImage(WINDOW_WIDTH, WINDOW_HEIGHT);
        Graphics offGraphics = offImage.getGraphics();

        if (welcomeScreen) {
            // Draw welcome screen
            offGraphics.setColor(Color.BLACK);
            offGraphics.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

            // Draw your preloaded welcome image
            offGraphics.drawImage(openingImage, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);
            // Draw text on top
            offGraphics.setColor(Color.WHITE);
            offGraphics.setFont(new Font("Arial", Font.BOLD, 80));
            offGraphics.drawString("Welcome to Atari Breakout!", 120, 330);
            offGraphics.setFont(new Font("Arial", Font.ITALIC, 60));
            offGraphics.drawString("Click to Start", 450, 400);
            offGraphics.setFont(new Font("Arial", Font.ITALIC, 40));
            offGraphics.drawString("Move your mouse to move the paddle!", 40, 500);
            offGraphics.drawString("Don't let the ball hit the floor!", 150, 650);


        }
       else if (game.isGameOver()) {
            // Display game over screen
            offGraphics.setColor(Color.BLACK);
            offGraphics.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
            offGraphics.setColor(Color.WHITE);
            offGraphics.setFont(new Font("Arial", Font.BOLD, 100));
            String gameOverText = "Game Over";
            offGraphics.drawString(gameOverText, (WINDOW_WIDTH) / 2 - 220, (WINDOW_HEIGHT) / 2 - 50);
            String instructionsText = "Click anywhere to reset";
            offGraphics.setFont(new Font("Times New Roman", Font.ITALIC, 40));

            offGraphics.drawString(instructionsText, (WINDOW_WIDTH) / 2 - 140, (WINDOW_HEIGHT) / 2);
        }
        else {

            offGraphics.setColor(Color.BLACK);
            offGraphics.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

            Paddle p = game.getPaddle();
            Ball b = game.getBall();

            Brick[][] bricks = game.getBricks();
            for (int i = 0; i < bricks.length; i++) {
                for (int j = 0; j < bricks[i].length; j++) {
                    Brick brick = bricks[i][j];
                    if (!brick.isDestroyed()) {
                        offGraphics.drawImage(brick.getImage(), brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight(), this);
                        offGraphics.setColor(brick.getColor());
                        offGraphics.fillRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
                    }
                }
            }
            if (game.allBricksDestroyed() && game.getLives() > 0 && !game.isGameOver()) {
                offGraphics.setColor(Color.WHITE);
                offGraphics.setFont(new Font("Arial", Font.BOLD, 80));
                offGraphics.drawString("Level " + game.getLevel() + " Completed!", WINDOW_WIDTH / 2 - 300, WINDOW_HEIGHT / 2);
            }

            offGraphics.setColor(Color.WHITE);
            offGraphics.fillRect(p.getX(), p.getY(), p.getWidth(), p.getHeight());
            offGraphics.fillOval((int) b.getX(), (int) b.getY(), b.getDiameter(), b.getDiameter());

            offGraphics.setColor(Color.WHITE);
            offGraphics.setFont(new Font("Playfair Display", Font.BOLD, 20));
            offGraphics.drawString("Score: " + game.getScore(), 10, 50);
            offGraphics.drawString("Lives: " + game.getLives(), 150, 50);
            offGraphics.drawString("Level: " + game.getLevel(), 290, 50);
        }
        g.drawImage(offImage, 0, 0, this);
    }

}
