import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class AtariViewer extends JFrame {
    private Atari game;
    private static final int WINDOW_HEIGHT = 900;
    private static final int WINDOW_WIDTH = 1250;


    public AtariViewer(Atari game) {
        this.game = game;
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                game.updatePaddlePosition(e.getX());
            }
        });

        this.setVisible(true);

        createBufferStrategy(2);
    }
    @Override
    public void paint(Graphics g) {
        Image offImage = createImage(WINDOW_WIDTH, WINDOW_HEIGHT);
        Graphics offGraphics = offImage.getGraphics();
        if (game.isGameOver()) {
            // Display game over screen
            offGraphics.setColor(Color.BLACK);
            offGraphics.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
            offGraphics.setColor(Color.WHITE);
            offGraphics.setFont(new Font("Arial", Font.BOLD, 100));
            String gameOverText = "Game Over";
            offGraphics.drawString(gameOverText, (WINDOW_WIDTH) / 2 - 190, (WINDOW_HEIGHT) / 2 - 50);

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
                        offGraphics.setColor(brick.getColor());
                        offGraphics.fillRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
                    }
                }
            }

            offGraphics.setColor(Color.WHITE);
            offGraphics.fillRect(p.getX(), p.getY(), p.getWidth(), p.getHeight());
            offGraphics.fillOval((int) b.getX(), (int) b.getY(), b.getDiameter(), b.getDiameter());

            offGraphics.setColor(Color.WHITE);
            offGraphics.setFont(new Font("Playfair Display", Font.BOLD, 20));
            offGraphics.drawString("Score: " + game.getScore(), 10, 50);
            offGraphics.drawString("Lives: " + game.getLives(), 150, 50);
        }
        g.drawImage(offImage, 0, 0, this);
    }

}
