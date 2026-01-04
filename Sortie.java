import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Sortie extends CaseTraversable {
    /* Attributs */
    private Image imSortie;
    
    /* Constructeur */
    public Sortie(int ligne, int colonne, Entite e) {
        super(ligne, colonne, e);
        imSortie = Toolkit.getDefaultToolkit().getImage("Media/Images/Cases/Sortie.png");
    }

    /* Méthodes */
    @Override
    public void affiche(Graphics g, FenetreJeu fj, Case c){
        g.drawImage(imSortie, c.getX()*Jeu.tailleCase , c.getY()*Jeu.tailleCase, fj);
    }
}