import java.awt.*;
public class CaseTraversable extends Case {
    // Attributs
    protected Entite contenu;
    private Image imDalle;
    // Constructeur
    public CaseTraversable(int x, int y, Entite e) {
        super(x,y);
        this.contenu = e;
        imDalle = Toolkit.getDefaultToolkit().getImage("Media/Images/Fonds/fond_dalle.png");
    }

    public Entite getContenu(){
        return this.contenu;
    }

  
    public void vide(){
        this.contenu = new Void();
    }


    public void entre(Entite e){
        this.contenu = e;
    }

    @Override
    public void affiche(Graphics g,  FenetreJeu fj, Case c){
        g.drawImage(imDalle, c.x*Jeu.tailleCase, c.y*Jeu.tailleCase, fj);
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
        if (!(contenu instanceof Void) && (b.getVitesse().vitesseAbsolue() >= 3)) {
            if (--contenu.resistance == 0) {
                this.vide();
            }
        }
    }
}
