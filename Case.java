import java.awt.*;
import javax.swing.*;
public abstract class Case extends Carre {
    // Attributs
    protected final int x,y;

    // Constructeur
    public Case(int x, int y){
        this.x = x;
        this.y = y;
    }
    public abstract void affiche(Graphics g, Grille grille, Case c);
    public void entre(Bille b){}
    public void sort(Bille b){}
    public void touche(Bille b){}
}