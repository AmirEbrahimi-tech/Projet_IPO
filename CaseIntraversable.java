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
    public void affiche(Graphics g,  FenetreJeu fj, Case c){
        // System.out.print("On dessine le mur\n");
        g.drawImage(imMur, c.x*Jeu.tailleCase , c.y*Jeu.tailleCase, fj);
    }

	@Override
	public void entre(Bille b) {
	}

	@Override
	public void sort(Bille b) {
	}

	@Override
	public void touche(Bille b) {
	}
}