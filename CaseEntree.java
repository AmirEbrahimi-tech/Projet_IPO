import java.awt.*;

public class CaseEntree extends CaseTraversable {
    /* Attributs */
    private Image imEntree;

    /* Constructeur */
    public CaseEntree(int x, int y, Entite e) {
        super(x,y,e);
        facAcceleration = 0.003;
        facFrottement = 0.96;
        imEntree = Toolkit.getDefaultToolkit().getImage("Media/Images/Cases/Entree.png");   
    }

    /* Méthodes */
    @Override
    public void affiche(Graphics g, FenetreJeu fj, Case c) {
        g.drawImage(imEntree, c.getX()*Jeu.tailleCase, c.getY()*Jeu.tailleCase, fj);
        if (! (getContenu() instanceof Void)) getContenu().affiche(g, fj,c);
    }

    @Override
    public double getFacFrottement() {return facFrottement;}

    @Override
    public double getFacAcceleration() {return facAcceleration;}
}