import java.awt.*;
// la classe PilierPierre un sous-type d'obstacle avec 5 points de vie
public class PilierPierre extends Obstacle {
    /* Attributs */
    private Image imPierre1;
    private Image imPierre2;
    private Image imPierre3;

    /* Constructeur */
    public PilierPierre() {
        // Initialisation de sa résistance
        super(3);
        // Chargement des images de texture
        imPierre1 = Toolkit.getDefaultToolkit().getImage("Media/Images/Obstacles/pilier_pierre_2.png");
        imPierre2 = Toolkit.getDefaultToolkit().getImage("Media/Images/Obstacles/pilier_pierre_1.png");
        imPierre3 = Toolkit.getDefaultToolkit().getImage("Media/Images/Obstacles/pilier_pierre_0.png");
        
    }

    /* Méthode */
    @Override
    public void affiche(Graphics g, FenetreJeu fj, Case c) {
        switch(getResistance()) {
            case 3 -> g.drawImage(imPierre3, c.getX()*Jeu.tailleCase , c.getY()*Jeu.tailleCase, fj);
            case 2 -> g.drawImage(imPierre2, c.getX()*Jeu.tailleCase , c.getY()*Jeu.tailleCase, fj);
            case 1 -> g.drawImage(imPierre1, c.getX()*Jeu.tailleCase , c.getY()*Jeu.tailleCase, fj);
        }
    }
}