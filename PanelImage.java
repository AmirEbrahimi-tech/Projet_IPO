import javax.swing.*;
import java.awt.*;

public class PanelImage extends JPanel {
    private Image image;

    public PanelImage() {
        image = Toolkit.getDefaultToolkit().getImage("Mur.png");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dessiner l'image à la position (50,50)
        g.drawImage(image, 50, 50, this);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Dessiner Image");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new PanelImage());
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}
