import java.awt.*;

public class CaseTraversable extends Case {
    /* Attributs */
    private Entite contenu;
    private boolean contientBille=false;

    /* Constructeur */
    public CaseTraversable(int x, int y, Entite e) {
        super(x,y);
        this.contenu = e;
    }

    /* Méthodes */
    public Entite getContenu(){return this.contenu;}
    public void vide(){this.contenu = new Void();}
    public void entre(Entite e){this.contenu = e;}

    @Override
    public void affiche(Graphics g,  FenetreJeu fj, Case c){}

    @Override 
    public boolean estVide(){return (this.contenu instanceof Void);}

    public boolean contientBille(){return this.contientBille;}

    @Override
    public void entre(Bille b, Graphics g, FenetreJeu fj){contientBille = true;}

    @Override
    public void sort(Bille b, Graphics g, FenetreJeu fj){contientBille = false;}

    @Override
    public void touche(Bille b, Graphics g, FenetreJeu fj) {
        if (contenu instanceof Void) {return;}

        // Si la bille touche un obsctacle à une vitesse minimal, 
        // on décrémente la résistance de l'obstacle
        else if (contenu instanceof Obstacle && (b.getVitesse().vitesseAbsolue() >= 3)) {
            contenu.decremente();
            ((Obstacle)contenu).interagit();
            if (contenu.getResistance() == 0) this.vide();
        } 

        // Si la bille touche un monstre, on décrémente les vies de la bille 
        else if (contenu instanceof Monstre) {
            ((Monstre)contenu).joueSon();
            b.decremente();

            // Si les vies de la bille atteignent 0, on perd
            if(b.estMort()){
                fj.ecranDefaite("Le monstre vous a tué !");
            }
        }
    }
}
