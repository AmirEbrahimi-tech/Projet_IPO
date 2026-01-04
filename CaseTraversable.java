import java.awt.*;
public class CaseTraversable extends Case {
    /* Attributs */
    private Entite contenu;
    private Image imDalle;

    /* Constructeur */
    public CaseTraversable(int x, int y, Entite e) {
        super(x,y);
        this.contenu = e;
        imDalle = Toolkit.getDefaultToolkit().getImage("Media/Images/Fonds/fond_dalle.png");
    }

    /* Méthodes */
    public Entite getContenu(){return this.contenu;}
    public void vide(){this.contenu = new Void();}
    public void entre(Entite e){this.contenu = e;}

    @Override
    public void affiche(Graphics g,  FenetreJeu fj, Case c){
        g.drawImage(imDalle, c.getX()*Jeu.tailleCase, c.getY()*Jeu.tailleCase, fj);
        contenu.affiche(g, fj,c);
    }
    @Override 
    public boolean estVide(){
        return this.contenu instanceof Void;
    }

    @Override
    public void entre(Bille b) {
        
    }

    @Override
    public void sort(Bille b) {
        
    }

    @Override
    public void touche(Bille b) {
        if (contenu instanceof Void) {return;}
        else if (contenu instanceof Obstacle && (b.getVitesse().vitesseAbsolue() >= 3)) {
            contenu.decremente();
            if (contenu.getResistance() == 0) this.vide();
        } else if (contenu instanceof Monstre) {
            b.decremente();
        }
    }
}
