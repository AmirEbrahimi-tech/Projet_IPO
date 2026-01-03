import java.awt.*;
import javax.swing.*;
// la classe Void
public class Void extends Entite {
    // les attributs
    private Image imDalle;

    // la contructeur par défaut
    public Void() {
        super(1);
        imDalle = Toolkit.getDefaultToolkit().createImage("Media/Images/Fonds/fond_dalle.png");
    }

    @Override
    public void affiche(Graphics g, FenetreJeu fj, Case c) {
        g.drawImage(imDalle, c.x*Jeu.tailleCase, c.y*Jeu.tailleCase, fj);
    }
}