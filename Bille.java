
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

// la class Bille
public class Bille extends JPanel implements MouseMotionListener {
    // les attributs
    private Position pos;
    private Position positionPrec;
    private Vitesse vit;
    private final int rayon = 20;
    // à voir ...

    // le constructeur ar défaut
    public Bille() {
        this.pos = new Position(0, 0);
        this.positionPrec = null;
        this.vit = new Vitesse();
        addMouseMotionListener(this);
    }

    // le constructeur
    public Bille(Position pos, Vitesse vit) {
        this.pos = pos; this.vit = vit;
        this.positionPrec = null;
        addMouseMotionListener(this);

    }

    // la méthode getDirectionH
    public double getDirectionH() {
        return vit.x / vit.vitesseAbsolue();
    }

    // la méthode getDirectionH
    public double getDirectionV() {
        return vit.y / vit.vitesseAbsolue();
    }

    // la méthode estDedans
    public boolean estDedans(double borneX, double borneY) {
        return ((0 <= pos.x - rayon + vit.x && pos.x + rayon + vit.x <= borneX) && (0 <= pos.y - rayon + vit.y && pos.y + rayon + vit.y <= borneY));
    }

    // la méthode deplacer
    public void deplacer() {
        // System.out.println(pos.getX() + " , " + pos.getY());
        if (estDedans(400, 400)) pos.set(pos.x + vit.x,pos.y + vit.y); // 400 est temporaire!!!!! il faut ajouter une variable static au class fenetreJeu qui contient la taille du fenetre et les utiliser ici
        frottement();
        repaint();
    }

    // la méthode frottement
    public void frottement() {
        if (vit.vitesseAbsolue() > 0) {
            vit.x *= 0.98; vit.y *= 0.98; // 0.98 doit est remplacer par une variable qui est donnée en fonction de la case qu'on est dessus
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(255, 0, 0, 127));
        g.fillOval((int) pos.x - rayon, (int) pos.y - rayon, rayon*2, rayon*2);
    
    }

    // Invoked when a mouse button is pressed on a component and then dragged.
    @Override
    public void mouseDragged(java.awt.event.MouseEvent e) {
        System.out.println("clicked!");
    }


    private Position mousePosition(java.awt.event.MouseEvent e) {
        return new Position(e.getX(), e.getY());
    }

    // Invoked when the mouse cursor has been moved onto a component but no buttons have been pushed.
    @Override
    public void mouseMoved(java.awt.event.MouseEvent e) {
        Position courant = mousePosition(e);
        // System.out.println("Courant: " + courant.x + " , " + courant.y);

        if (positionPrec != null) {
            double dx = courant.x - positionPrec.x;
            double dy = courant.y - positionPrec.y;
            // System.out.println("Différence: " + dx + " , " + dy);
            this.vit.setVitesse(dx * 0.05, dy * 0.05); // il faut y ajouter (comme le facteur de frottement) un facteur d'acceleration en fonction de la case qu'on est dedans
        }

        positionPrec = courant;
    }


    public static void main(String[] args) {
            JFrame frame = new JFrame("Fenetre");
            Bille test = new Bille(new Position(200, 200), new Vitesse(0, 0));
            test.setPreferredSize(new Dimension(400, 400));
            test.setBackground(new Color(200, 200, 200));
            frame.getContentPane().add(test);
            frame.pack();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            try {
                while (true) {
                test.deplacer();
                Thread.sleep(10);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}