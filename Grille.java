import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.sound.sampled.*;
import java.io.File;

// la classe de la grille du jeu contenant la bille
public class Grille extends JPanel{
    // Attributs
    protected Terrain terrain;
    protected static int tailleCase = 32;
    protected static int hauteur, largeur;
    // private JFrame frame;
    // private Robot robot;
    protected Bille bille;
    
    // private Image imBille;

    //Constructeur
    public Grille(Terrain t) {
        terrain = t;
        hauteur = t.getHauteur();
        largeur = t.getLargeur();
        /* Mise en place de la fenêtre */
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(largeur * tailleCase, hauteur *  tailleCase));
        // frame = new JFrame("Enigma");
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setResizable(false);
        bille = new Bille(new Position(largeur*tailleCase/2, hauteur*tailleCase/2), new Vitesse());
        // addMouseMotionListener(this);
        // frame.getContentPane().add(this);
        // frame.pack();
        // frame.setVisible(true);
        // try{robot = new Robot();}
        // catch(Exception e){System.err.print(e);}
        // BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        // Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
        // frame.setCursor(blankCursor);
        /* Chargement des images de texture */
        // imBille   = Toolkit.getDefaultToolkit().getImage("Media/Images/Bille/Bille.gif");
        
    }

    /* ------------------------------ Méthodes ------------------------------------ */
    public Case getCase(int ligne, int colonne) {
        try {
            return this.terrain.carte[ligne][colonne];
        } catch (Exception e) {
            return null;
        }
    }

    public boolean rebonditSurBord() {
        // position actuel de la bille(pour vérifier que la bille est bien placée sur la grille)
        int x = (int) bille.pos.x / tailleCase;
        int y = (int) bille.pos.y / tailleCase;
        // le rayon de la bille
        int r = bille.rayon;
        // position suivant de la bille d'après sa vitesse(et bien sa direction)
        /* Nouvelles coordonnées de la bille */
        int nouvX = (int) (bille.pos.x + bille.vit.x) / tailleCase;  
        int nouvY = (int) (bille.pos.y + bille.vit.y) / tailleCase;
        int xd = (int) (bille.pos.x + bille.vit.x + r) / tailleCase; // droite
        int xg = (int) (bille.pos.x + bille.vit.x - r) / tailleCase; // gauche
        int yh = (int) (bille.pos.y + bille.vit.y - r) / tailleCase; // haut
        int yb = (int) (bille.pos.y + bille.vit.y + r) / tailleCase; // bas

        Case courante = getCase(y, x);
        if (courante == null) throw new NullPointerException("bille n'est pas bien localisée sur la grille!");

        // DEBUG
        System.out.println("[rebondit] pos(px):(" + bille.pos.x + "," + bille.pos.y + ") (lig,col):(" + y + "," + x + ") nouv(lig,col):(" + nouvY + "," + nouvX + ")");

        Case caseD = getCase(nouvY,xd);    // case à droite
        Case caseH = getCase(yh,nouvX);    // case en haut
        Case caseG = getCase(nouvY,xg);    // case à gauche
        Case caseB = getCase(yb,nouvX);    // case en bas

        // 1er = droite, 2nd = dessous, 3eme = gauche, 4eme = dessus
        if (caseD != null && caseD != courante && ! caseD.estVide()) {
            bille.vit.renverseH();
            // push the ball just outside the blocking cell to avoid sticking
            double eps = 1.0;
            double wallX = xd * tailleCase;
            // set position to be clearly outside the wall (do not call deplacer here)
            bille.pos.set(wallX - (bille.rayon + eps), bille.pos.y);
            bille.vit.multiplier(0.70);
            return true;
        } else if (caseB != null && caseB != courante && ! caseB.estVide()) {
            bille.vit.renverseV();
            double eps = 1.0;
            double wallY = yb * tailleCase;
            bille.pos.set(bille.pos.x, wallY - (bille.rayon + eps));
            bille.vit.multiplier(0.70);
            return true;
        } else if (caseG != null && caseG != courante && ! caseG.estVide()) {
            bille.vit.renverseH();
            double eps = 1.0;
            double wallX = (xg + 1) * tailleCase;
            bille.pos.set(wallX + (bille.rayon + eps), bille.pos.y);
            bille.vit.multiplier(0.70);
            return true; 
        } else if (caseH != null && caseH != courante && ! caseH.estVide()) {
            bille.vit.renverseV();
            double eps = 1.0;
            double wallY = (yh + 1) * tailleCase;
            bille.pos.set(bille.pos.x, wallY + (bille.rayon + eps));
            bille.vit.multiplier(0.70);
            return true; 
        } else {
            return rebonditSurCoin();
        }
    }

    // la méthode rebonditSurCoin
    private boolean rebonditSurCoin() {
        // position actuel de la bille(pour vérifier que la bille est bien placée sur la grille)
        int x = (int) bille.pos.x / tailleCase;
        int y = (int) bille.pos.y / tailleCase;
        // le rayon de la bille
        int r = bille.rayon;
        // position suivant de la bille d'après sa vitesse(et bien sa direction)
        int nouvX = (int) (bille.pos.x + bille.vit.x) / tailleCase;  // emplacement de la bille après deplacement sur la grille
        int nouvY = (int) (bille.pos.y + bille.vit.y) / tailleCase;

        Case courante = getCase(y, x);

        // nouvelle position de la bille sur la fenêtre
        double cx = bille.pos.x + bille.vit.x;
        double cy = bille.pos.y + bille.vit.y;

        Case caseHD = getCase(nouvY - 1, nouvX + 1);    // case en haut à droite
        Case caseBD = getCase(nouvY + 1, nouvX + 1);    // case en bas à droite
        Case caseBG = getCase(nouvY + 1, nouvX - 1);    // case en bas à gauche
        Case caseHG = getCase(nouvY - 1, nouvX - 1);    // case en haut à gauche
        
        // DEBUG: print per-frame diagnostic to help trace collision detection
        System.out.println("[rebondit] pos(px):(" + bille.pos.x + "," + bille.pos.y + ") grille(x,y):(" + x + "," + y + ") nouv(grille):(" + nouvX + "," + nouvY + ") suivante(px):(" + cx + "," + cy + ")");
        System.out.println("[rebondit] voisins -> HD:" + (caseHD instanceof CaseIntraversable) + " BD:" + (caseBD instanceof CaseIntraversable) + " BG:" + (caseBG instanceof CaseIntraversable) + " HG:" + (caseHG instanceof CaseIntraversable));

        // 1er = 1h30, 2nd = 4h30, 3eme = 7h30, 4eme = 10h30 (Position Aiguille Heure)
        Double coinX_ = Double.MAX_VALUE;
        Double coinY_ = Double.MAX_VALUE;
        Double r_c = Double.MAX_VALUE;
        if (caseHD != null && caseHD != courante && ! caseHD.estVide()) {
            double coinX = (nouvX + 1) * tailleCase;
            double coinY = (nouvY) * tailleCase;
            double distance = Math.sqrt(Math.pow(cx - coinX, 2) +  Math.pow(cy - coinY, 2));
            if (distance <= r_c) {
                coinX_ = coinX;
                coinY_ = coinY;
                r_c = distance;
            }
        }
        if (caseBD != null && caseBD != courante && ! caseBD.estVide()) {
            // bille.deplacer();
            double coinX = (nouvX + 1) * tailleCase;
            double coinY = (nouvY + 1) * tailleCase;
            double distance = Math.sqrt(Math.pow(cx - coinX, 2) +  Math.pow(cy - coinY, 2));
            if (distance <= r_c) {
                coinX_ = coinX;
                coinY_ = coinY;
                r_c = distance;
            }
        }
        if (caseBG != null && caseBG != courante && ! caseBG.estVide()) {
            // bille.deplacer();
            double coinX = (nouvX) * tailleCase;
            double coinY = (nouvY + 1) * tailleCase;
            double distance = Math.sqrt(Math.pow(cx - coinX, 2) +  Math.pow(cy - coinY, 2));
            if (distance <= r_c) {
                coinX_ = coinX;
                coinY_ = coinY;
                r_c = distance;
            }
        }
        if (caseHG != null && caseHG != courante && ! caseHG.estVide()) {
            // bille.deplacer();
            double coinX = (nouvX) * tailleCase;
            double coinY = (nouvY) * tailleCase;
            double distance = Math.sqrt(Math.pow(cx - coinX, 2) +  Math.pow(cy - coinY, 2));
            if (distance <= r_c) {
                coinX_ = coinX;
                coinY_ = coinY;
                r_c = distance;
            }
        }

        if (r_c < r) {
            double dc_x, dc_y;
            if (r_c == 0) {
                // fallback normal: opposite to velocity direction
                double speed = bille.vit.vitesseAbsolue();
                if (speed == 0) {
                    dc_x = 1.0; dc_y = 0.0;
                } else {
                    dc_x = -bille.vit.x / speed; dc_y = -bille.vit.y / speed;
                }
            } else {
                dc_x = (cx - coinX_) / r_c;
                dc_y = (cy - coinY_) / r_c;
            }
            double v_coin = bille.vit.x * dc_x + bille.vit.y * dc_y;
            bille.vit.setVitesse(bille.vit.x - 2 * v_coin * dc_x, bille.vit.y - 2 * v_coin * dc_y);
            // push the ball outside the corner along the normal to avoid re-collision
            double eps = 0.1;
            bille.pos.set(coinX_ + dc_x * (bille.rayon + eps), coinY_ + dc_y * (bille.rayon + eps));
            bille.vit.multiplier(0.70);
            return true;
        } else {
            bille.deplacer();
            return false;
        }
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
    // public void afficheMurs(Graphics g, Case c) {
    //     // g.setColor(new Color(100, 100, 100, 180));
    //     // g.fillRect(c.colonne * tailleCase, c.ligne * tailleCase, tailleCase, tailleCase);
    //     ((CaseIntraversable)c).affiche(g, this, c.ligne*tailleCase, c.colonne*tailleCase);

    // }

    // la méthode paintComponent
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        }
//         for (Case[] ligne : terrain.carte) {
//             for (Case c : ligne) {
//                 if (c == null) continue;
// ;
//                 if (c instanceof CaseIntraversable) afficheMurs(g, c);
//                 else if (contientBille(c.ligne, c.colonne)) {
//                     // g.setColor(new Color(20, 230, 170)); // just random numbers :)
//                     // g.fillRect(c.colonne * tailleCase, c.ligne * tailleCase, tailleCase, tailleCase);
//                     g.drawImage(imHerbe,c.colonne * tailleCase, c.ligne * tailleCase,this);
                    
//                 }
//                     // else if (c instanceof Sortie) afficheSortie(g, c);
//                     // else if (c instanceof Teleporteur) afficheTeleporteur(g, c);
//                     // else if (c instanceof CaseTraversable caseTraversable) {
//                     //     Entite contenu = caseTraversable.getContenu();
//                     //     if (contenu instanceof Monstre) afficheLoup(g, c);
//                     //     else if (contenu instanceof Personnage) afficheMouton(g, c);
//                     //     else if (contenu instanceof Obstacle) afficheObs(g, c);
//                     // }                    
//             }
//         }
        // g.setColor(new Color(255, 0, 0, 127));
        // g.fillOval((int) bille.pos.x - bille.rayon, (int) bille.pos.y - bille.rayon, bille.rayon*2, bille.rayon*2);
        // g.drawImage(imBille,(int) bille.pos.x - bille.rayon, (int) bille.pos.y - bille.rayon,this);

    

    private Position mousePosition(java.awt.event.MouseEvent e) {
        return new Position(e.getX(), e.getY());
    }

    // @Override
    // public void mouseDragged(MouseEvent e) {
    //     System.out.println("Not supported yet.");
    // }

    // @Override
    // public void mouseMoved(MouseEvent e) {
    //     Point location = getLocationOnScreen();
    //     Point center = new Point(location.x + getWidth()/2,location.y + getHeight()/2);
    //     int dx = e.getXOnScreen() - center.x;
    //     int dy = e.getYOnScreen() - center.y;
    //     bille.vit.setVitesse(bille.vit.x + dx*0.005, bille.vit.y + dy*0.005);
    //     robot.mouseMove(center.x, center.y);
    // }
}