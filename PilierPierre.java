import java.awt.*;
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
        super(3);
        // Chargement des images de texture
        imPierre1   = Toolkit.getDefaultToolkit().getImage("Media/Images/Obstacles/Pilier_Pierre/pilier_pierre_0.png");
        imPierre2   = Toolkit.getDefaultToolkit().getImage("Media/Images/Obstacles/Pilier_Pierre/pilier_pierre_1.png");
        imPierre3   = Toolkit.getDefaultToolkit().getImage("Media/Images/Obstacles/Pilier_Pierre/pilier_pierre_2.png");
        imPierre4   = Toolkit.getDefaultToolkit().getImage("Media/Images/Obstacles/Pilier_Pierre/pilier_pierre_3.png");
        imPierre5   = Toolkit.getDefaultToolkit().getImage("Media/Images/Obstacles/Pilier_Pierre/pilier_pierre_4.png");
    }

    @Override
    public void affiche(Graphics g, Grille grille,int posLigne, int posColonne) {
        if (resistance == 5) {
            g.drawImage(imPierre5, posColonne , posLigne, grille);
        } else if (resistance == 4) {
            g.drawImage(imPierre4, posColonne , posLigne, grille);
        } else if (resistance == 3) {
            g.drawImage(imPierre3, posColonne , posLigne, grille);
        } else if (resistance == 2) {
            g.drawImage(imPierre2, posColonne , posLigne, grille);
        } else {
            g.drawImage(imPierre1, posColonne , posLigne, grille);
        }
    }
}