import java.awt.*;
import javax.swing.*;
// la classe Entite
public abstract class Entite {
    // Attributs
    protected int resistance;

    // Constructeur
    public Entite(int resistance) {
        this.resistance = resistance;
    }

    public abstract void affiche(Graphics g, Grille grille, int posLigne, int posColonne);
} 