import java.awt.*;
import javax.swing.*;
// la classe Entite
public abstract class Entite {
    /* Attribut */ 
    private int resistance;

    /* Constructeur */ 
    public Entite(int resistance) {this.resistance = resistance;}

    /* Getter */
    public int getResistance(){return this.resistance;}

    /* Méthodes*/
    public void decremente(){resistance--;}
    public abstract void affiche(Graphics g, FenetreJeu fj ,Case c);
} 