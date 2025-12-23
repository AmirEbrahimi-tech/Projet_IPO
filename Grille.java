import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.*;

// la classe de la grille du jeu contenant la bille
public class Grille extends JPanel implements MouseMotionListener{
    // les attributs
    protected Terrain terrain;
    protected int tailleCase = 24;
    protected int hauteur = 10, largeur = 10;
    private JFrame frame;
    protected Bille2 bille;

    public Grille(Terrain t) {
        terrain = t;

        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(largeur * tailleCase, hauteur *  tailleCase));

        frame = new JFrame("Enigma");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        bille = new Bille2(new Position(hauteur*tailleCase/2, largeur*tailleCase/2), new Vitesse());
        addMouseMotionListener(this);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
    }

    // la méthode pour dessinner les murs
    public void afficheMurs(Graphics g, Case c) {
        g.setColor(Color.BLACK);
        g.fillRect(c.colonne*tailleCase, c.ligne*tailleCase, tailleCase, tailleCase);
    }

    // la méthode paintComponent
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Case[] ligne : terrain.carte) {
            for (Case c : ligne) {
                try {
                    if (c instanceof CaseIntraversable) afficheMurs(g, c);
                    // else if (c instanceof Sortie) afficheSortie(g, c);
                    // else if (c instanceof Teleporteur) afficheTeleporteur(g, c);
                    // else if (c instanceof CaseTraversable caseTraversable) {
                    //     Entite contenu = caseTraversable.getContenu();
                    //     if (contenu instanceof Monstre) afficheLoup(g, c);
                    //     else if (contenu instanceof Personnage) afficheMouton(g, c);
                    //     else if (contenu instanceof Obstacle) afficheObs(g, c);
                    // }                    
                } catch (Exception e) {
                }
            }
        }
        g.setColor(new Color(255, 0, 0, 127));
        g.fillOval((int) bille.pos.x - bille.rayon, (int) bille.pos.y - bille.rayon, bille.rayon*2, bille.rayon*2);
    }

    private Position mousePosition(java.awt.event.MouseEvent e) {
        return new Position(e.getX(), e.getY());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("Not supported yet.");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Position courant = mousePosition(e);
        System.out.println("Courant: " + courant.x + " , " + courant.y);

        if (bille.positionPrec != null) {
            double dx = courant.x - bille.positionPrec.x;
            double dy = courant.y - bille.positionPrec.y;
            // System.out.println("Différence: " + dx + " , " + dy);
            bille.vit.setVitesse(dx * 0.05, dy * 0.05); // il faut y ajouter (comme le facteur de frottement) un facteur d'acceleration en fonction de la case qu'on est dedans
        }

        bille.positionPrec = courant;
    }


}