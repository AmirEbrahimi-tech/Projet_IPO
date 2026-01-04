import java.awt.*;
import javax.swing.*;

public abstract class Obstacle extends Entite {
    /* Constructeur */
    public Obstacle(int resistance){
        super(resistance);
    }

    /* Méthode */
    public abstract void affiche(Graphics g, FenetreJeu fj, Case c);
    public abstract void interagit();
}