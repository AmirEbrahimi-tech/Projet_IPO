import java.awt.*;
public class Trou extends CaseTraversable{
    private Image imTrou;
    public Trou(int x, int y){
        super(x, y, new Void());
        imTrou = Toolkit.getDefaultToolkit().getImage("Media/Images/Fonds/fond_trou.gif");
    }
    public boolean estVide(){
        return true;
    }

    @Override
    public void affiche(Graphics g,  FenetreJeu fj, Case c){
        g.drawImage(imTrou, c.x*Jeu.tailleCase , c.y*Jeu.tailleCase, fj);
    }
    @Override
    public void entre(Bille b){
    }
}
