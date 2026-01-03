import java.awt.*;
import javax.swing.*;
public class EntiteMobile extends Entite {
    // Attributs
    protected Direction direction;

    // Constucteur
    public EntiteMobile(int resistance, Direction dir) {
        super(resistance);
        this.direction = dir;
    }

    public void affiche(Graphics g, Grille grille,Case c) {}
}