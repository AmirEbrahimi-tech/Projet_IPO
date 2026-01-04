import java.awt.*;

public class CaseDalle extends CaseTraversable {
    /* Attributs */
    private Image imDalle;

    /* Constructeur */
    public CaseDalle(int x, int y, Entite e) {
        super(x,y,e);
        facAcceleration = 0.003;
        facFrottement = 0.96;
        //Chargement de l'image de la dalle
        imDalle = Toolkit.getDefaultToolkit().getImage("Media/Images/Fonds/fond_dalle.png");   
    }

    /* Méthodes */
    @Override
    public void affiche(Graphics g, FenetreJeu fj, Case c) {
        g.drawImage(imDalle, c.getX()*Jeu.tailleCase, c.getY()*Jeu.tailleCase, fj);
        if (! (getContenu() instanceof Void)) getContenu().affiche(g, fj,c);
    }

    @Override
    public double getFacFrottement() {return facFrottement;}

    @Override
    public double getFacAcceleration() {return facAcceleration;}
}