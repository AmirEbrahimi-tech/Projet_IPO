import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class BilleGame extends JPanel {
    private int ballX = 200, ballY = 200;   // position de la bille
    private double vx = 0, vy = 0;          // vitesse
    private final int diameter = 30;        // taille de la bille
    private Point center;                   // centre de la fenêtre
    private Robot robot;

    public BilleGame(JFrame frame) throws AWTException {
        setPreferredSize(new Dimension(400, 400));

        // Curseur invisible
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");
        frame.setCursor(blankCursor);

        // Robot pour repositionner la souris
        robot = new Robot();

        // Listener pour mouvements de souris
        frame.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                // recalculer le centre du panel à chaque mouvement
                Point location = getLocationOnScreen();
                center = new Point(location.x + getWidth()/2,
                                   location.y + getHeight()/2);

                int dx = e.getXOnScreen() - center.x;
                int dy = e.getYOnScreen() - center.y;

                // Appliquer déplacement relatif comme force
                vx += dx * 0.1;
                vy += dy * 0.1;

                // Replacer la souris au centre du panel
                robot.mouseMove(center.x, center.y);
            }
        });

        // Timer pour mettre à jour la physique
        new Timer(16, e -> update()).start(); // ~60 FPS
    }

    private void update() {
        ballX += vx;
        ballY += vy;

        // Frottement
        vx *= 0.95;
        vy *= 0.95;

        // Collisions avec les bords
        if (ballX < 0) { ballX = 0; vx = -vx; }
        if (ballY < 0) { ballY = 0; vy = -vy; }
        if (ballX > getWidth()-diameter) { ballX = getWidth()-diameter; vx = -vx; }
        if (ballY > getHeight()-diameter) { ballY = getHeight()-diameter; vy = -vy; }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.fillOval(ballX, ballY, diameter, diameter);
    }

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Bille contrôlée par la souris");
        BilleGame game = new BilleGame(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Initialiser le centre une fois la fenêtre visible
        SwingUtilities.invokeLater(() -> {
            try {
                Point location = game.getLocationOnScreen();
                game.center = new Point(location.x + game.getWidth()/2,
                                        location.y + game.getHeight()/2);
                game.robot.mouseMove(game.center.x, game.center.y);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
