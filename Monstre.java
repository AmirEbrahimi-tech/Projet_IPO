import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
// la classe Monstre
public class Monstre extends EntiteMobile {
    /* Attributs */
    private Image imMonstreB;
    private Image imMonstreH;
    private Image imMonstreD;
    private Image imMonstreG;
    
    /* Constructeur */
    public Monstre(int resistance, Direction dir) {
        super(resistance, dir);
        // Chargement des images de texture
        imMonstreB = Toolkit.getDefaultToolkit().getImage("Media/Images/Monstre/Monstre_0.png");
        imMonstreH = Toolkit.getDefaultToolkit().getImage("Media/Images/Monstre/Monstre_1.png");
        imMonstreD = Toolkit.getDefaultToolkit().getImage("Media/Images/Monstre/Monstre_2.png");
        imMonstreG = Toolkit.getDefaultToolkit().getImage("Media/Images/Monstre/Monstre_3.png");
    }

    /* Méthodes */

    // la méthode affiche
    public void affiche(Graphics g, FenetreJeu fj, Case c) {
        switch(getDirection()) {
            case sud -> g.drawImage(imMonstreB, c.getX()*Jeu.tailleCase , c.getY()*Jeu.tailleCase, fj);       
            case nord -> g.drawImage(imMonstreH, c.getX()*Jeu.tailleCase , c.getY()*Jeu.tailleCase, fj);       
            case est -> g.drawImage(imMonstreD, c.getX()*Jeu.tailleCase , c.getY()*Jeu.tailleCase, fj);       
            case ouest -> g.drawImage(imMonstreG, c.getX()*Jeu.tailleCase , c.getY()*Jeu.tailleCase, fj);       
        }
    }

    // la méthode action
    public void action(Case courante, Case cible) {
        if (!(courante instanceof CaseTraversable)) return;
        CaseTraversable src = (CaseTraversable) courante;

        if (!(cible instanceof CaseTraversable)) return;
        CaseTraversable dst = (CaseTraversable) cible;

        Entite tmp = dst.getContenu();

        if (dst.estVide()) {
            dst.entre(src.getContenu());
            src.vide();
            return;
        }

        if (tmp instanceof Obstacle) {
            tmp.decremente();
            if (tmp.getResistance() <= 0) {
                dst.vide();
                dst.entre(src.getContenu());
                src.vide();
            }
            return;
        }

        Entite e = src.getContenu();
        if (e instanceof Monstre) {
            ((Monstre) e).setDirection(Direction.random());
        }
    }

}