import java.awt.*;
import javax.tools.Tool;

public class CaseIntraversable extends Case {
    private Image imMur;
    // Constructeur
    public CaseIntraversable(int ligne, int colonne) {
        super(ligne, colonne);
        imMur = Toolkit.getDefaultToolkit().getImage("Media/Images/Cases/Mur.png");
    }

    //Une case intraversable n'est jamais vide
    public boolean estVide() {return false;}

    @Override
    public void affiche(Graphics g,  Grille grille, int posLigne, int posColonne){
        g.drawImage(imMur, posColonne , posLigne, grille);
    }
}