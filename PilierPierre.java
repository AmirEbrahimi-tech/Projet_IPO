import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
// la classe PilierPierre un sous-type d'obstacle avec 5 points de vie
public class PilierPierre extends Obstacle {
    // les attributs
    // à voir
    private Image imPierre1;
    private Image imPierre2;
    private Image imPierre3;
    private Image imPierre4;
    private Image imPierre5;

    // le constructeur
    public PilierPierre() {
        // Initialisation de sa résistance
        super(5);
        // Chargement des images de texture
        imPierre1 = Toolkit.getDefaultToolkit().getImage("Media/Images/Obstacles/pilier_pierre_0.png");
        imPierre2 = Toolkit.getDefaultToolkit().getImage("Media/Images/Obstacles/pilier_pierre_1.png");
        imPierre3 = Toolkit.getDefaultToolkit().getImage("Media/Images/Obstacles/pilier_pierre_2.png");
        imPierre4 = Toolkit.getDefaultToolkit().getImage("Media/Images/Obstacles/pilier_pierre_3.png");
        imPierre5 = Toolkit.getDefaultToolkit().getImage("Media/Images/Obstacles/pilier_pierre_4.png");
        
    }

    @Override
    public void affiche(Graphics g, FenetreJeu fj, Case c) {
        switch(resistance) {
            case 5 -> g.drawImage(imPierre5, c.x*Jeu.tailleCase , c.y*Jeu.tailleCase, fj);
            case 4 -> g.drawImage(imPierre4, c.x*Jeu.tailleCase , c.y*Jeu.tailleCase, fj);
            case 3 -> g.drawImage(imPierre3, c.x*Jeu.tailleCase , c.y*Jeu.tailleCase, fj);
            case 2 -> g.drawImage(imPierre2, c.x*Jeu.tailleCase , c.y*Jeu.tailleCase, fj);
            case 1 -> g.drawImage(imPierre1, c.x*Jeu.tailleCase , c.y*Jeu.tailleCase, fj);
        }
    }
}