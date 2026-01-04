import java.awt.*;
import javax.tools.Tool;

public class CaseIntraversable extends Case {
    /* Attribut */
    private Image imMur;
    
    /* Constructeur */
    public CaseIntraversable(int x, int y) {
        super(x, y);
        imMur = Toolkit.getDefaultToolkit().getImage("Media/Images/Cases/Mur.png");
    }

    /* Méthodes */
    //Une case intraversable n'est jamais vide
    public boolean estVide() {return false;}

    @Override
    public void affiche(Graphics g,  FenetreJeu fj, Case c){
        // System.out.print("On dessine le mur\n");
        Position p = Jeu.impact;
        if (Jeu.impact != null) {
            g.drawImage(imImpact, (int)p.getX() - 16, (int)p.getY() - 16,fj);
            // réinitialisation de la position de l'impact
            Jeu.impact = null;
        }
        g.drawImage(imMur, c.getX()*Jeu.tailleCase , c.getY()*Jeu.tailleCase, fj);
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