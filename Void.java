import java.awt.*;
import javax.swing.*;
public class Void extends Entite {
    /* Attribut */
    private Image imDalle;

    /* Constructeur */
    public Void() {
        super(1);
        imDalle = Toolkit.getDefaultToolkit().createImage("Media/Images/Fonds/fond_dalle.png");
    }

    /* Méthode */
    @Override
    public void affiche(Graphics g, FenetreJeu fj, Case c) {
        g.drawImage(imDalle, c.getX()*Jeu.tailleCase, c.getY()*Jeu.tailleCase, fj);
    }
}