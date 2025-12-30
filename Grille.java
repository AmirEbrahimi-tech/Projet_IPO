import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.*;

// la classe de la grille du jeu contenant la bille
public class Grille extends JPanel implements MouseMotionListener {
    // les attributs
    protected Terrain terrain;
    protected static int tailleCase = 24;
    protected static int hauteur, largeur;
    private JFrame frame;
    protected Bille2 bille;

    public Grille(Terrain t) {
        terrain = t;
        hauteur = t.getHauteur();
        largeur = t.getLargeur();

        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(largeur * tailleCase, hauteur *  tailleCase));

        frame = new JFrame("Enigma");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        bille = new Bille2(new Position(largeur*tailleCase/2, hauteur*tailleCase/2), new Vitesse());
        addMouseMotionListener(this);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
    }

    // la méthode getCase
    public Case getCase(int ligne, int colonne) {
        try {
            return this.terrain.carte[ligne][colonne];
        } catch (Exception e) {
            return null;
        }
    }

    // la méthode rebonde
    public boolean rebondit() {
        // position actuel de la bille(pour vérifier que la bille est bien placée sur la grille)
        int x = (int) bille.pos.x / tailleCase;
        int y = (int) bille.pos.y / tailleCase;
        // le rayon de la bille
        int r = bille.rayon;
        // position suivant de la bille d'après sa vitesse(et bien sa direction)
        int nouvX = (int) (bille.pos.x + bille.vit.x) / tailleCase;  // emplacement de la bille après deplacement sur la grille
        int nouvY = (int) (bille.pos.y + bille.vit.y) / tailleCase;
        int xd = (int) (bille.pos.x + bille.vit.x + r) / tailleCase; // droite
        int xg = (int) (bille.pos.x + bille.vit.x - r) / tailleCase; // gauche
        int yh = (int) (bille.pos.y + bille.vit.y - r) / tailleCase; // haut
        int yb = (int) (bille.pos.y + bille.vit.y + r) / tailleCase; // bas

        Case courante = getCase(y, x);
        if (courante == null) throw new NullPointerException("bille n'est pas bien localisée sur la grille!");

        // DEBUG
        // System.out.println("[rebondit] pos(px):(" + bille.pos.x + "," + bille.pos.y + ") (lig,col):(" + y + "," + x + ") nouv(lig,col):(" + nouvY + "," + nouvX + ")");

        Case caseD = getCase(nouvY, xd);    // case à droite
        Case caseH = getCase(yh, nouvX);    // case en haut
        Case caseG = getCase(nouvY, xg);    // case à gauche
        Case caseB = getCase(yb, nouvX);    // case en bas

        // nouvelle position de la bille sur la fenêtre
        double cx = bille.pos.x + bille.vit.x;
        double cy = bille.pos.y + bille.vit.y;

        Case caseHD = getCase(nouvY - 1, nouvX + 1);    // case en haut à droite
        Case caseBD = getCase(nouvY + 1, nouvX + 1);    // case en bas à droite
        Case caseBG = getCase(nouvY + 1, nouvX - 1);    // case en bas à gauche
        Case caseHG = getCase(nouvY - 1, nouvX - 1);    // case en haut à gauche
        
        // DEBUG: print per-frame diagnostic to help trace collision detection
        System.out.println("[rebondit] pos(px):(" + bille.pos.x + "," + bille.pos.y + ") grille(x,y):(" + x + "," + y + ") nouv(grille):(" + nouvX + "," + nouvY + ") suivante(px):(" + cx + "," + cy + ")");
        System.out.println("[rebondit] voisins -> D:" + (caseD instanceof CaseIntraversable) + " B:" + (caseB instanceof CaseIntraversable) + " G:" + (caseG instanceof CaseIntraversable) + " H:" + (caseH instanceof CaseIntraversable) + " HD:" + (caseHD instanceof CaseIntraversable) + " BD:" + (caseBD instanceof CaseIntraversable) + " BG:" + (caseBG instanceof CaseIntraversable) + " HG:" + (caseHG instanceof CaseIntraversable));

        // 1er = droite, 2nd = dessous, 3eme = gauche, 4eme = dessus
        // 5eme = 13h30, 6eme = 16h30, 7eme = 19h30, 8eme = 22h30 (Position Aiguille Heure)
        if (caseD != null && caseD != courante && caseD instanceof CaseIntraversable) {
            bille.vit.renverseH();
            bille.deplacer();
        } else if (caseB != null && caseB != courante && caseB instanceof CaseIntraversable) {
            bille.vit.renverseV();
            bille.deplacer();
        } else if (caseG != null && caseG != courante && caseG instanceof CaseIntraversable) {
            bille.vit.renverseH();
            bille.deplacer();
        } else if (caseH != null && caseH != courante && caseH instanceof CaseIntraversable) {
            bille.vit.renverseV();
            bille.deplacer();
        } 
        else if (caseHD != null && caseHD != courante && caseHD instanceof CaseIntraversable) {
            bille.deplacer();
            double coinX = (nouvX + 1) * tailleCase;
            double coinY = (nouvY) * tailleCase;
            double r_c = Math.sqrt(Math.pow(cx - coinX, 2) +  Math.pow(cy - coinY, 2));
            if (r_c < r) {
                // if (r_c == 0) r_c = 0.0001;
                double dc_x = (cx - coinX) / r_c;
                double dc_y = (cy - coinY) / r_c;
                double v_coin = bille.vit.x * dc_x + bille.vit.y * dc_y;
                bille.vit.setVitesse(bille.vit.x - 2 * v_coin * dc_x, bille.vit.y - 2 * v_coin * dc_y);
                
            }
        } else if (caseBD != null && caseBD != courante && caseBD instanceof CaseIntraversable) {
            bille.deplacer();
            double coinX = (nouvX + 1) * tailleCase;
            double coinY = (nouvY + 1) * tailleCase;
            double r_c = Math.sqrt(Math.pow(cx - coinX, 2) + Math.pow(cy - coinY, 2));
            if (r_c < r) {
                // if (r_c == 0) r_c = 0.0001;
                double dc_x = (cx - coinX) / r_c;
                double dc_y = (cy - coinY) / r_c;
                double v_coin = bille.vit.x * dc_x + bille.vit.y * dc_y;
                bille.vit.setVitesse(bille.vit.x - 2 * v_coin * dc_x, bille.vit.y - 2 * v_coin * dc_y);
                
            }
        } else if (caseBG != null && caseBG != courante && caseBG instanceof CaseIntraversable) {
            bille.deplacer();
            double coinX = (nouvX) * tailleCase;
            double coinY = (nouvY + 1) * tailleCase;
            double r_c = Math.sqrt(Math.pow(cx - coinX, 2) + Math.pow(cy - coinY, 2));
            if (r_c < r) {
                // if (r_c == 0) r_c = 0.0001;
                double dc_x = (cx - coinX) / r_c;
                double dc_y = (cy - coinY) / r_c;
                double v_coin = bille.vit.x * dc_x + bille.vit.y * dc_y;
                bille.vit.setVitesse(bille.vit.x - 2 * v_coin * dc_x, bille.vit.y - 2 * v_coin * dc_y);
                
            }
        } else if (caseHG != null && caseHG != courante && caseHG instanceof CaseIntraversable) {
            bille.deplacer();
            double coinX = (nouvX) * tailleCase;
            double coinY = (nouvY) * tailleCase;
            double r_c = Math.sqrt(Math.pow(cx - coinX, 2) + Math.pow(cy - coinY, 2));
            if (r_c < r) {
                // if (r_c == 0) r_c = 0.0001;
                double dc_x = (cx - coinX) / r_c;
                double dc_y = (cy - coinY) / r_c;
                double v_coin = bille.vit.x * dc_x + bille.vit.y * dc_y;
                bille.vit.setVitesse(bille.vit.x - 2 * v_coin * dc_x, bille.vit.y - 2 * v_coin * dc_y);
                
            }
        } 
        else {
            bille.deplacer();
            return false;
        }
        return true;
    }

    // la méthode contientBille pour debuger et définir l'emplacement de la bille sur la grille est égale aux coordonnées passées en param
    public boolean contientBille(int x, int y) {
        Position p = this.bille.pos;
        // x is ligne (row), y is colonne (col) in callers; compare accordingly
        return (x == (int)p.y / tailleCase) && (y == (int)p.x / tailleCase);
    }

    // la méthode localiseBille qui renvoie l'emplacement de la bille sur la grille
    public Position localiseBille() {
        Position p = bille.pos;
        return new Position((int) p.x / tailleCase, (int) p.y / tailleCase);
    }

    // la méthode pour dessinner les murs
    public void afficheMurs(Graphics g, Case c) {
        g.setColor(new Color(100, 100, 100, 180));
        g.fillRect(c.colonne * tailleCase, c.ligne * tailleCase, tailleCase, tailleCase);
    }

    // la méthode paintComponent
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        rebondit();
        // bille.deplacer();

        for (Case[] ligne : terrain.carte) {
            for (Case c : ligne) {
                if (c == null) continue;
                if (c instanceof CaseIntraversable) afficheMurs(g, c);
                else if (contientBille(c.ligne, c.colonne)) {
                    g.setColor(new Color(20, 230, 170)); // just random numbers :)
                    g.fillRect(c.colonne * tailleCase, c.ligne * tailleCase, tailleCase, tailleCase);
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