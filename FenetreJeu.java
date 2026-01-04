import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class FenetreJeu extends JPanel implements MouseMotionListener{
    /* Attributs */
    private Jeu jeu;
    protected static int tailleCase = 32;
    private int hauteur, largeur;
    private JFrame frame;
    private Robot robot;
    private Image imImpact;
    private Image imCoeur;
    private Timer ClearTimer;

    /* Constructeur */
    public FenetreJeu(Jeu j) {
        jeu = j;
        hauteur = jeu.getTerrain().getHauteur();
        largeur = jeu.getTerrain().getLargeur();

        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(largeur * tailleCase, hauteur * tailleCase));
        frame = new JFrame("Fenetre jeu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
        addMouseMotionListener(this);

        try{robot = new Robot();}
        catch(Exception e){System.err.print(e);}

        //On charge un curseur transparent
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
        frame.setCursor(blankCursor);

        imCoeur  = Toolkit.getDefaultToolkit().getImage("Media/Images/Bille/Coeur.png");
        imImpact = Toolkit.getDefaultToolkit().getImage("Media/Images/Bille/Impact.gif");
    }

    /* Méthodes */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int y = 0;y<hauteur;y++){
            for(int x = 0;x<largeur;x++){
                Case c = jeu.getCase(y,x);
                if (c == null) continue;
                if (c instanceof CaseIntraversable) ((CaseIntraversable)c).affiche(g,this,c);
                else if (c instanceof Trou) ((Trou)c).affiche(g,this,c);
                else if (c instanceof CaseTraversable) ((CaseTraversable)c).affiche(g,this,c);
            }
        }
        
        jeu.rebonditSurBord(g, this);
        jeu.getBille().affiche(g,this);
        
        afficheImpact(g);
        afficheVies(g);
    }

    // Draws the impact image and manages the timer that clears it.
    private void afficheImpact(Graphics g) {
        Position p = Jeu.impact;
        if (p != null) {
            g.drawImage(imImpact, (int)p.getX() - 16, (int)p.getY() - 16, this);
            if (ClearTimer == null) {
                ClearTimer = new Timer(250, e -> {
                    Jeu.impact = null;
                    ClearTimer.stop();
                    ClearTimer = null;
                });
                ClearTimer.setRepeats(false);
                ClearTimer.start();
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        Point location = getLocationOnScreen();
        Point center = new Point(location.x + getWidth()/2,location.y + getHeight()/2);
        
        //On récupère le décalage entre la position de la souris et le centre de l'écran
        int dx = e.getXOnScreen() - center.x;
        int dy = e.getYOnScreen() - center.y;
        Position p = jeu.getBille().getPosition();
        CaseTraversable courante  = (CaseTraversable) jeu.getCase((int) p.getY() / FenetreJeu.tailleCase, (int) p.getX() / FenetreJeu.tailleCase);
        double f = courante.getFacAcceleration();
        jeu.getBille().getVitesse().setVitesse(jeu.getBille().getVitesseX() + dx * f, jeu.getBille().getVitesseY() + dy * f);
        
        //On replace la souris au centre de la fenêtre
        robot.mouseMove(center.x, center.y);
    }

    private void afficheVies(Graphics g) { 
        int vies = jeu.getBille().getVies(); 
        String txt = "" + vies; 
        g.setFont(new Font("Monospaced", 1, 30)); 
        g.setColor(Color.BLACK); 
        FontMetrics fm = g.getFontMetrics(); 
        int textWidth = fm.stringWidth(txt); 
        int x = getWidth() - textWidth - 52; int y = getHeight() - 10; 

        // Contour noir 
        g.setColor(Color.BLACK); 
        int[] dx = {-2, -2, -2, 0, 0, 2, 2, 2}; 
        int[] dy = {-2, 0, 2, -2, 2, -2, 0, 2}; 
        for (int i = 0; i < dx.length; i++) { 
            g.drawString(txt, x + dx[i], y + dy[i]); 
        } 
        // Texte en blanc
        g.setColor(Color.WHITE); 
        g.drawString(txt, x, y);
        g.drawImage(imCoeur, x + 18, y - 28, 40, 40, this);

    }

    public void ecranDefaite(String message) {
        frame.remove(this);
        JLabel label = new JLabel("<html><div style='text-align:center;font='Monospaced';'>"+"Game Over<br><br>" + message + "</div></html>");
        label.setFont(new Font("Monospaced", 1, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setSize(this.getSize());
        frame.getContentPane().add(label);
        frame.repaint();
    }

    public void ecranVictoire(long temps) {
        frame.remove(this);
        JLabel label = new JLabel("<html><div style='text-align:center;'>"+"Félicitations !<br><br>Votre temps : " + temps/1000 + " secondes");
        label.setFont(new Font("Monospaced", 1, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setSize(this.getSize());
        frame.getContentPane().add(label);
        frame.repaint();
    }
}
