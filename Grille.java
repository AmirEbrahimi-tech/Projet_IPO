import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.*;

// la classe de la grille du jeu contenant la bille
public class Grille extends JPanel implements MouseMotionListener {
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

    // la méthode getCase
    public Case getCase(int x, int y) {
        // if (x < 0 || x >= terrain.carte.length || y < 0 || y >= terrain.carte[0].length) {
        //     return null;
        // }
        try {
            return this.terrain.carte[x][y];
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }

    // la méthode touche
    public int touche() {
        int x = (int) bille.pos.x / tailleCase;
        int y = (int) bille.pos.y / tailleCase;
        int r = bille.rayon;

        int xd = ((int) bille.pos.x + r) / tailleCase; // droite
        int xg = ((int) bille.pos.x - r) / tailleCase; // gauche
        int yh = ((int) bille.pos.y + r) / tailleCase; // haut
        int yb = ((int) bille.pos.y - r) / tailleCase; // bas

        Case caseCourant = getCase(x, y);
        if (caseCourant == null) return 0;

        Case caseD = getCase(xd, y);    // case à droite
        Case caseH = getCase(x, yb);    // case en haut
        Case caseG = getCase(xg, y);    // case à gauche
        Case caseB = getCase(x, yh);    // case en bas

        // renvoie : 1=droite, 2=dessous, 3=gauche, 4=dessus
        if (caseD != null && caseD != caseCourant && caseD instanceof CaseIntraversable) return 1;
        if (caseB != null && caseB != caseCourant && caseB instanceof CaseIntraversable) return 2;
        if (caseG != null && caseG != caseCourant && caseG instanceof CaseIntraversable) return 3;
        if (caseH != null && caseH != caseCourant && caseH instanceof CaseIntraversable) return 4;

        return 0;
        // double cx = bille.pos.x;
        // double cy = bille.pos.y;
        // int r     = bille.rayon;
        // int i     = (int) cx / tailleCase;
        // int j     = (int) cy / tailleCase;
        // if ((int) (cx + r) / tailleCase >= i + 1 && getCase(i+1, j) instanceof CaseIntraversable) return 1;
        // if ((int) (cy + r) / tailleCase >= j + 1 && getCase(i, j+1) instanceof CaseIntraversable) return 2;
        // if ((int) (cx - r) / tailleCase >= i - 1 && getCase(i-1, j) instanceof CaseIntraversable) return 3;
        // if ((int) (cy - r) / tailleCase >= i - 1 && getCase(i, j-1) instanceof CaseIntraversable) return 4;
        // else return 0;
        




    }

    // la méthode contientBille pour debuger et définir l'emplacement de la bille sur la grille est égale aux coordonnées passées en param
    public boolean contientBille(int x, int y) {
        Position p = this.bille.pos;
        return (x == (int)p.x / tailleCase) && (y == (int)p.y / tailleCase);
    }

    // la méthode localiseBille qui renvoie l'emplacement de la bille sur la grille
    public Position localiseBille() {
        Position p = bille.pos;
        return new Position((int) p.x / tailleCase, (int) p.y / tailleCase);
    }

    // la méthode pour dessinner les murs
    public void afficheMurs(Graphics g, Case c) {
        g.setColor(new Color(100, 100, 100, 180));
        g.fillRect(c.colonne*tailleCase, c.ligne*tailleCase, tailleCase, tailleCase);
    }

    // la méthode paintComponent
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Case[] ligne : terrain.carte) {
            for (Case c : ligne) {
                if (c == null) continue;
                if (c instanceof CaseIntraversable) afficheMurs(g, c);
                else if (contientBille(c.ligne, c.colonne)) {
                    g.setColor(new Color(20, 230, 170)); // just random numbers :)
                    g.fillRect(c.ligne * tailleCase, c.colonne * tailleCase, tailleCase, tailleCase);
                }
                int t = touche();
                // debug: print touch once per paint cycle (could be noisy)
                System.out.println(t);
                if (t==1) {
                    // collision on right
                }
                else if (t==2) {
                    // collision below
                }
                else if (t==3) {
                    // collision left
                }
                else if (t==4) {
                    // collision above
                }
                    // else if (c instanceof Sortie) afficheSortie(g, c);
                    // else if (c instanceof Teleporteur) afficheTeleporteur(g, c);
                    // else if (c instanceof CaseTraversable caseTraversable) {
                    //     Entite contenu = caseTraversable.getContenu();
                    //     if (contenu instanceof Monstre) afficheLoup(g, c);
                    //     else if (contenu instanceof Personnage) afficheMouton(g, c);
                    //     else if (contenu instanceof Obstacle) afficheObs(g, c);
                    // }                    
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
        // System.out.println("Courant: " + courant.x + " , " + courant.y);
        // System.out.println(toucher());

        if (bille.positionPrec != null) {
            double dx = courant.x - bille.positionPrec.x;
            double dy = courant.y - bille.positionPrec.y;
            // System.out.println("Différence: " + dx + " , " + dy);
            bille.vit.setVitesse(dx * 0.05, dy * 0.05); // il faut y ajouter (comme le facteur de frottement) un facteur d'acceleration en fonction de la case qu'on est dedans
        }

        bille.positionPrec = courant;
    }


}