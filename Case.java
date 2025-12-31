import java.awt.*;
import javax.swing.*;
public abstract class Case extends Carre {
    // Attributs
    protected final int ligne,colonne;

    // Constructeur
    public Case(int ligne, int colonne){
        this.ligne = ligne;
        this.colonne = colonne;
    }
    public abstract void affiche(Graphics g, Grille grille, int posLigne, int posColonne);
    public void entre(Bille b){}
    public void sort(Bille b){}
    public void touche(Bille b){}
}