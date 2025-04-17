import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AtariViewer extends JFrame {
    private Atari atari;
    private Timer timer;
    private static final int WINDOW_HEIGHT = 1700;
    private static final int WINDOW_WIDTH = 2700;

    public AtariViewer(Atari atari) {
        this.atari = atari;
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Paddle paddle = atari.getPaddle();
                paddle.moveTo(e.getX() - paddle.getWidth() / 2);
                repaint();
            }
        });
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Draw paddle
        Paddle p = atari.getPaddle();
        g.setColor(Color.WHITE);
        g.fillRect(p.getX(), p.getY(), p.getWidth(), p.getHeight());

        // Draw ball
        Ball b = atari.getBall();
        g.setColor(Color.WHITE);
        g.fillOval((int)b.getX(), (int)b.getY(), b.getDiameter(), b.getDiameter());
    }

}
