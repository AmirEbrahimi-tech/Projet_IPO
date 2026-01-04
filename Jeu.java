import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Jeu {
    /* Variable Static */
    protected final static int tailleCase = 32;

    // Attributs
    private Terrain terrain;
    private Bille bille;

    protected static Position impact;

    //Constructeur
    public Jeu(String f) {
        terrain = new Terrain(f);
        Position entree = getEntree();
        bille = new Bille(new Position(entree.getX()*tailleCase*3/2, entree.getY()*tailleCase*3/2), new Vitesse());
    }

    /* Getter */
    public Terrain getTerrain(){return this.terrain;}
    public Bille getBille(){return this.bille;}

    /* Méthodes */
    public Case getCase(int ligne, int colonne) {
        try {
            return this.terrain.getCarte()[ligne][colonne];
        } catch (Exception e) {
            return null;
        }
    }

    private Position getEntree() {
        for (Case[] ligne : terrain.getCarte()) {
            for (Case c : ligne) {
                if (c instanceof CaseEntree) return new Position(c.getX(), c.getY());
            }
        }
        return null;
    }

    public boolean rebonditSurBord(Graphics g, FenetreJeu fj) {
        // position actuel de la bille(pour vérifier que la bille est bien placée sur la grille)
        int x = (int) bille.getPositionX() / tailleCase;
        int y = (int) bille.getPositionY() / tailleCase;
        // le rayon de la bille
        int r = bille.getRayon();
        // position suivant de la bille d'après sa vitesse(et bien sa direction)
        /* Nouvelles coordonnées de la bille */
        int nouvX = (int) (bille.getPositionX() + bille.getVitesseX()) / tailleCase;  
        int nouvY = (int) (bille.getPositionY() + bille.getVitesseY()) / tailleCase;
        int xd = (int) (bille.getPositionX() + bille.getVitesseX() + r) / tailleCase; // droite
        int xg = (int) (bille.getPositionX() + bille.getVitesseX() - r) / tailleCase; // gauche
        int yh = (int) (bille.getPositionY() + bille.getVitesseY() - r) / tailleCase; // haut
        int yb = (int) (bille.getPositionY() + bille.getVitesseY() + r) / tailleCase; // bas

        Case courante = getCase(y, x);
        if (courante == null) throw new NullPointerException("bille n'est pas bien localisée sur la grille!");

        // DEBUG
        // System.out.println("[rebondit] pos(px):(" + bille.getPositionX() + "," + bille.getPositionY() + ") (lig,col):(" + y + "," + x + ") nouv(lig,col):(" + nouvY + "," + nouvX + ")");

        Case caseD = getCase(nouvY,xd);    // case à droite
        Case caseH = getCase(yh,nouvX);    // case en haut
        Case caseG = getCase(nouvY,xg);    // case à gauche
        Case caseB = getCase(yb,nouvX);    // case en bas

        // 1er = droite, 2nd = dessous, 3eme = gauche, 4eme = dessus
        if (caseD != null && caseD != courante && ! caseD.estVide()) {
            bille.getVitesse().renverseH();
            // push the ball just outside the blocking cell to avoid sticking
            double eps = 1.0;
            double wallX = xd * tailleCase;
            // set position to be clearly outside the wall (do not call deplacer here)
            caseD.touche(bille,g ,fj);
            signale(wallX, bille.getPositionY());
            bille.getPosition().set(wallX - (bille.getRayon() + eps), bille.getPositionY());
            bille.getVitesse().multiplier(0.70);
            return true;
        } else if (caseB != null && caseB != courante && ! caseB.estVide()) {
            bille.getVitesse().renverseV();
            double eps = 1.0;
            double wallY = yb * tailleCase;
            caseB.touche(bille, g, fj);
            signale(bille.getPositionX(), wallY);
            bille.getPosition().set(bille.getPositionX(), wallY - (bille.getRayon() + eps));
            bille.getVitesse().multiplier(0.70);
            return true;
        } else if (caseG != null && caseG != courante && ! caseG.estVide()) {
            bille.getVitesse().renverseH();
            double eps = 1.0;
            double wallX = (xg + 1) * tailleCase;
            caseG.touche(bille, g, fj);
            signale(wallX, bille.getPositionY());
            bille.getPosition().set(wallX + (bille.getRayon() + eps), bille.getPositionY());
            bille.getVitesse().multiplier(0.70);
            return true; 
        } else if (caseH != null && caseH != courante && ! caseH.estVide()) {
            bille.getVitesse().renverseV();
            double eps = 1.0;
            double wallY = (yh + 1) * tailleCase;
            caseH.touche(bille,g ,fj);
            signale(bille.getPositionX(), wallY);
            bille.getPosition().set(bille.getPositionX(), wallY + (bille.getRayon() + eps));
            bille.getVitesse().multiplier(0.70);
            return true; 
        } else {
            return rebonditSurCoin(g, fj);
        }
    }

    // la méthode rebonditSurCoin
    private boolean rebonditSurCoin(Graphics g, FenetreJeu fj) {
        // position actuel de la bille(pour vérifier que la bille est bien placée sur la grille)
        int x = (int) bille.getPositionX() / tailleCase;
        int y = (int) bille.getPositionY() / tailleCase;
        // le rayon de la bille
        int r = bille.getRayon();
        // position suivant de la bille d'après sa vitesse(et bien sa direction)
        int nouvX = (int) (bille.getPositionX() + bille.getVitesseX()) / tailleCase;  // emplacement de la bille après deplacement sur la grille
        int nouvY = (int) (bille.getPositionY() + bille.getVitesseY()) / tailleCase;

        Case courante = getCase(y, x);

        // nouvelle position de la bille sur la fenêtre
        double cx = bille.getPositionX() + bille.getVitesseX();
        double cy = bille.getPositionY() + bille.getVitesseY();

        Case caseHD = getCase(nouvY - 1, nouvX + 1);    // case en haut à droite
        Case caseBD = getCase(nouvY + 1, nouvX + 1);    // case en bas à droite
        Case caseBG = getCase(nouvY + 1, nouvX - 1);    // case en bas à gauche
        Case caseHG = getCase(nouvY - 1, nouvX - 1);    // case en haut à gauche
        
        // DEBUG: print per-frame diagnostic to help trace collision detection
        // System.out.println("[rebondit] pos(px):(" + bille.getPositionX() + "," + bille.getPositionY() + ") grille(x,y):(" + x + "," + y + ") nouv(grille):(" + nouvX + "," + nouvY + ") suivante(px):(" + cx + "," + cy + ")");
        // System.out.println("[rebondit] voisins -> HD:" + (caseHD instanceof CaseIntraversable) + " BD:" + (caseBD instanceof CaseIntraversable) + " BG:" + (caseBG instanceof CaseIntraversable) + " HG:" + (caseHG instanceof CaseIntraversable));

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
            plusProche.touche(bille, g, fj);
            // bille.afficheImpact(g, fj, plusProche);
            if(plusProche == caseHD){
                signale(coinX_ + 1, coinY_);
            } else if (plusProche == caseBD) {
                signale(coinX_ + 1, coinY_ + 1);
            } else if (plusProche == caseBG) {
                signale(coinX_ , coinY_ + 1);
            } else if (plusProche == caseHG) {
                signale(coinX_ , coinY_);
            }
            double dc_x, dc_y;
            if (r_c == 0) {
                // fallback normal: opposite to velocity direction
                double speed = bille.getVitesse().vitesseAbsolue();
                if (speed == 0) {
                    dc_x = 1.0; dc_y = 0.0;
                } else {
                    dc_x = -bille.getVitesseX() / speed; 
                    dc_y = -bille.getVitesseY() / speed;
                }
            } else {
                dc_x = (cx - coinX_) / r_c;
                dc_y = (cy - coinY_) / r_c;
            }
            double v_coin = bille.getVitesseX() * dc_x + bille.getVitesseY() * dc_y;
            bille.getVitesse().setVitesse(bille.getVitesseX() - 2 * v_coin * dc_x, bille.getVitesseY() - 2 * v_coin * dc_y);
            // push the ball outside the corner along the normal to avoid re-collision
            double eps = 0.1;
            bille.getPosition().set(coinX_ + dc_x * (bille.getRayon() + eps), coinY_ + dc_y * (bille.getRayon() + eps));
            bille.getVitesse().multiplier(0.70);
            return true;
        } else {
            bille.deplacer(this, g, fj);
            return false;
        }
    }

    // la méthode signale
    public void signale(double x, double y) {
        impact = new Position(x, y);
    }
    
    
    // la méthode tour
    public void tour() {
        Random rnd = new Random();

        ArrayList<CaseTraversable> mobiles = new ArrayList<>();
        for (int i = 0; i < terrain.getCarte().length; i++) {
            for (int j = 0; j < terrain.getCarte()[0].length; j++) {
                Case c = terrain.getCarte()[i][j];
                if (c instanceof CaseTraversable) {
                    Entite e = ((CaseTraversable) c).getContenu();
                    if (e instanceof EntiteMobile) {
                        mobiles.add((CaseTraversable)c);
                    }
                }
            }
        }

        if (mobiles.isEmpty()) return;

        CaseTraversable src = mobiles.get(rnd.nextInt(mobiles.size()));
        EntiteMobile ent = (EntiteMobile) src.getContenu();

        int newY = src.getY();
        int newX = src.getX();

        switch (ent.getDirection()) {
            case nord:
                newY = newY - 1;
                break;
            case sud:
                newY = newY + 1;
                break;
            case est:
                newX = newX + 1;
                break;
            case ouest:
                newX = newX - 1;
                break;
            default:
                return;
        }

        if (newY < 0 || newY >= terrain.getCarte().length || newX < 0 || newX >= terrain.getCarte()[0].length) return;

        Case target = terrain.getCarte()[newY][newX];
        
        // réduire les mouvements des monstres
        int moveThrottle = 50;
        if (rnd.nextInt(moveThrottle) == 0) {
            ent.action(src, target);
        }
    }

}
    