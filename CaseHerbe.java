
import java.awt.*;

public class CaseHerbe extends CaseTraversable {
    /* Attributs */
    private Image imHerbe;

    public CaseHerbe(int x, int y, Entite e) {
        super(x,y,e);
        facAcceleration = 0.001;
        facFrottement = 0.90;
        imHerbe = Toolkit.getDefaultToolkit().getImage("Media/Images/Fonds/fond_herbe.png");   
    }

    @Override
    public void affiche(Graphics g, FenetreJeu fj, Case c) {
        g.drawImage(imHerbe, c.getX()*Jeu.tailleCase, c.getY()*Jeu.tailleCase, fj);
        if (! (getContenu() instanceof Void)) getContenu().affiche(g, fj,c);
    }

    @Override
    public double getFacFrottement() {return facFrottement;}

    @Override
    public double getFacAcceleration() {return facAcceleration;}
}