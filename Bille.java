
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

// la class Bille
public class Bille extends JPanel{
    // les attributs
    private Position pos;
    private Vitesse vit;
    // à voir ...

    // le constructeur ar défaut
    public Bille() {
        this.pos = new Position(0, 0);
        this.vit = new Vitesse();
    }

    // le constructeur
    public Bille(Position pos, Vitesse vit) {
        this.pos = pos; this.vit = vit;
    }

    // la méthode getDirectionH
    public double getDirectionH() {
        return vit.x / vit.vitesseAbsolue();
    }

    // la méthode getDirectionH
    public double getDirectionV() {
        return vit.y / vit.vitesseAbsolue();
    }

    // la méthode deplacer
    public void deplacer() {
        pos.set(pos.x + vit.x,pos.y + vit.y);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(255, 0, 0, 127));
        g.fillOval((int) pos.x - 50, (int) pos.y - 50, 100, 100);
    
    }

    public static void main(String[] args) {
            JFrame frame = new JFrame("Fenetre");
            Bille test = new Bille(new Position(200, 200), new Vitesse(1.0, 1.0));
            test.setPreferredSize(new Dimension(400, 400));
            test.setBackground(new Color(200, 200, 200));
            frame.getContentPane().add(test);
            frame.pack();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            try {
                while (true) {
                test.deplacer();
                Thread.sleep(100);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}