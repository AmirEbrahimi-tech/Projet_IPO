import java.awt.*;
import javax.tools.Tool;

public class CaseIntraversable extends Case {
    private Image imMur;
    // Constructeur
    public CaseIntraversable(int x, int y) {
        super(x, y);
        imMur = Toolkit.getDefaultToolkit().getImage("Media/Images/Cases/Mur.png");
    }

    //Une case intraversable n'est jamais vide
    public boolean estVide() {return false;}

    @Override
    public void affiche(Graphics g,  Grille grille, Case c){
        // System.out.print("On dessine le mur\n");
        g.drawImage(imMur, c.x*grille.tailleCase , c.y*grille.tailleCase, grille);
    }
}