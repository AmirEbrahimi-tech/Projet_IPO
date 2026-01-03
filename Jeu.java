import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.sound.sampled.*;
import java.io.File;

// la classe de la grille du jeu contenant la bille
public class Jeu {
    // Attributs
    protected Terrain terrain;
    protected static int tailleCase = 32;
    protected static int hauteur, largeur;
    protected Bille bille;

    //Constructeur
    public Jeu(String f) {
        terrain = new Terrain(f);
        hauteur = terrain.getHauteur();
        largeur = terrain.getLargeur();
        bille = new Bille(new Position(largeur*tailleCase/2, hauteur*tailleCase/2), new Vitesse());
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
        int x = (int) bille.getPositionX() / tailleCase;
        int y = (int) bille.getPositionY() / tailleCase;
        // le rayon de la bille
        int r = bille.rayon;
        // position suivant de la bille d'après sa vitesse(et bien sa direction)
        /* Nouvelles coordonnées de la bille */
        int nouvX = (int) (bille.getPositionX() + bille.vit.x) / tailleCase;  
        int nouvY = (int) (bille.getPositionY() + bille.vit.y) / tailleCase;
        int xd = (int) (bille.getPositionX() + bille.vit.x + r) / tailleCase; // droite
        int xg = (int) (bille.getPositionX() + bille.vit.x - r) / tailleCase; // gauche
        int yh = (int) (bille.getPositionY() + bille.vit.y - r) / tailleCase; // haut
        int yb = (int) (bille.getPositionY() + bille.vit.y + r) / tailleCase; // bas

        Case courante = getCase(y, x);
        if (courante == null) throw new NullPointerException("bille n'est pas bien localisée sur la grille!");

        // DEBUG
        System.out.println("[rebondit] pos(px):(" + bille.getPositionX() + "," + bille.getPositionY() + ") (lig,col):(" + y + "," + x + ") nouv(lig,col):(" + nouvY + "," + nouvX + ")");

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
            caseD.touche(bille);
            bille.getPosition().set(wallX - (bille.rayon + eps), bille.getPositionY());
            bille.vit.multiplier(0.70);
            return true;
        } else if (caseB != null && caseB != courante && ! caseB.estVide()) {
            bille.vit.renverseV();
            double eps = 1.0;
            double wallY = yb * tailleCase;
            caseB.touche(bille);
            bille.getPosition().set(bille.getPositionX(), wallY - (bille.rayon + eps));
            bille.vit.multiplier(0.70);
            return true;
        } else if (caseG != null && caseG != courante && ! caseG.estVide()) {
            bille.vit.renverseH();
            double eps = 1.0;
            double wallX = (xg + 1) * tailleCase;
            caseG.touche(bille);
            bille.getPosition().set(wallX + (bille.rayon + eps), bille.getPositionY());
            bille.vit.multiplier(0.70);
            return true; 
        } else if (caseH != null && caseH != courante && ! caseH.estVide()) {
            bille.vit.renverseV();
            double eps = 1.0;
            double wallY = (yh + 1) * tailleCase;
            caseH.touche(bille);
            bille.getPosition().set(bille.getPositionX(), wallY + (bille.rayon + eps));
            bille.vit.multiplier(0.70);
            return true; 
        } else {
            return rebonditSurCoin();
        }
    }

    // la méthode rebonditSurCoin
    private boolean rebonditSurCoin() {
        // position actuel de la bille(pour vérifier que la bille est bien placée sur la grille)
        int x = (int) bille.getPositionX() / tailleCase;
        int y = (int) bille.getPositionY() / tailleCase;
        // le rayon de la bille
        int r = bille.rayon;
        // position suivant de la bille d'après sa vitesse(et bien sa direction)
        int nouvX = (int) (bille.getPositionX() + bille.vit.x) / tailleCase;  // emplacement de la bille après deplacement sur la grille
        int nouvY = (int) (bille.getPositionY() + bille.vit.y) / tailleCase;

        Case courante = getCase(y, x);

        // nouvelle position de la bille sur la fenêtre
        double cx = bille.getPositionX() + bille.vit.x;
        double cy = bille.getPositionY() + bille.vit.y;

        Case caseHD = getCase(nouvY - 1, nouvX + 1);    // case en haut à droite
        Case caseBD = getCase(nouvY + 1, nouvX + 1);    // case en bas à droite
        Case caseBG = getCase(nouvY + 1, nouvX - 1);    // case en bas à gauche
        Case caseHG = getCase(nouvY - 1, nouvX - 1);    // case en haut à gauche
        
        // DEBUG: print per-frame diagnostic to help trace collision detection
        System.out.println("[rebondit] pos(px):(" + bille.getPositionX() + "," + bille.getPositionY() + ") grille(x,y):(" + x + "," + y + ") nouv(grille):(" + nouvX + "," + nouvY + ") suivante(px):(" + cx + "," + cy + ")");
        System.out.println("[rebondit] voisins -> HD:" + (caseHD instanceof CaseIntraversable) + " BD:" + (caseBD instanceof CaseIntraversable) + " BG:" + (caseBG instanceof CaseIntraversable) + " HG:" + (caseHG instanceof CaseIntraversable));

        // 1er = 1h30, 2nd = 4h30, 3eme = 7h30, 4eme = 10h30 (Position Aiguille Heure)
        Double coinX_ = Double.MAX_VALUE;
        Double coinY_ = Double.MAX_VALUE;
        Double r_c = Double.MAX_VALUE;
        Case plusProche = null;
        if (caseHD != null && caseHD != courante && ! caseHD.estVide()) {
            double coinX = (nouvX + 1) * tailleCase;
            double coinY = (nouvY) * tailleCase;
            double distance = Math.sqrt(Math.pow(cx - coinX, 2) +  Math.pow(cy - coinY, 2));
            if (distance <= r_c) {
                coinX_ = coinX;
                coinY_ = coinY;
                r_c = distance;
                plusProche = caseHD;
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
                plusProche = caseBD;
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
                plusProche = caseBG;
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
                plusProche = caseHG;
            }
        }

        if (r_c < r) {
            plusProche.touche(bille);
            double dc_x, dc_y;
            if (r_c == 0) {
                // fallback normal: opposite to velocity direction
                double speed = bille.vit.vitesseAbsolue();
                if (speed == 0) {
                    dc_x = 1.0; dc_y = 0.0;
                } else {
                    dc_x = -bille.vit.x / speed; 
                    dc_y = -bille.vit.y / speed;
                }
            } else {
                dc_x = (cx - coinX_) / r_c;
                dc_y = (cy - coinY_) / r_c;
            }
            double v_coin = bille.vit.x * dc_x + bille.vit.y * dc_y;
            bille.vit.setVitesse(bille.vit.x - 2 * v_coin * dc_x, bille.vit.y - 2 * v_coin * dc_y);
            // push the ball outside the corner along the normal to avoid re-collision
            double eps = 0.1;
            bille.getPosition().set(coinX_ + dc_x * (bille.rayon + eps), coinY_ + dc_y * (bille.rayon + eps));
            bille.vit.multiplier(0.70);
            return true;
        } else {
            bille.deplacer();
            return false;
        }
    }
}
    