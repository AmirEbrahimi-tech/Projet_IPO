import java.awt.*;
public class Trou extends CaseTraversable{
    /* Attributs */
    private Image imTrou;

    /* Constructeur */
    public Trou(int x, int y){
        super(x, y, new Void());
        imTrou = Toolkit.getDefaultToolkit().getImage("Media/Images/Fonds/fond_trou.gif");
    }

    /* Méthodes */
    public boolean estVide(){
        return true;
    }

    @Override
    public void affiche(Graphics g,  FenetreJeu fj, Case c){
        g.drawImage(imTrou, c.getX()*Jeu.tailleCase , c.getY()*Jeu.tailleCase, fj);
    }
    @Override
    public void entre(Bille b){
    }
    @Override
    public void sort(Bille b){}
    @Override
    public void touche(Bille b){}
}
