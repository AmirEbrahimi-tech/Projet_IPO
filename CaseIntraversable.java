import java.awt.*;

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