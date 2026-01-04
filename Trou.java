import java.awt.*;
public class Trou extends CaseTraversable{
    /* Attributs */
    private Image imTrou;
    private Image imChute;

    /* Constructeur */
    public Trou(int x, int y){
        super(x, y, new Void());
        imChute = Toolkit.getDefaultToolkit().getImage("Media/Images/Bille/Chute.gif");
        imTrou = Toolkit.getDefaultToolkit().getImage("Media/Images/Fonds/fond_trou.gif");
    }

    /* Méthodes */
    public boolean estVide(){
        return true;
    }

    @Override
    public void affiche(Graphics g,  FenetreJeu fj, Case c){
        g.drawImage(imTrou, c.getX()*Jeu.tailleCase , c.getY()*Jeu.tailleCase, fj);
        if(contientBille) g.drawImage(imChute, c.getX()*Jeu.tailleCase, c.getY()*Jeu.tailleCase, fj);
    }
    @Override
    public void entre(Bille b, Graphics g, FenetreJeu fj){
        contientBille = true;
        // b.chute(g,fj);
    }
    @Override
    public void sort(Bille b, Graphics g, FenetreJeu fj){}
    @Override
    public void touche(Bille b, Graphics g, FenetreJeu fj){}
}
