import java.awt.*;
import javax.swing.*;
public abstract class Case {
    // Attributs
    protected final int x,y;

    // Constructeur
    public Case(int x, int y){
        this.x = x;
        this.y = y;
    }
    public abstract void affiche(Graphics g, FenetreJeu fj, Case c);
    public abstract boolean estVide();
    public abstract void entre(Bille b);
    public abstract void sort(Bille b);
    public abstract void touche(Bille b);
}