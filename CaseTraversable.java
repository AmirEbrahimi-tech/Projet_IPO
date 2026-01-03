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

    //Méthodes
    @Override
    public boolean estVide(){
        return this.contenu instanceof Void;    
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
    public void affiche(Graphics g,  Grille grille, Case c){
        contenu.affiche(g, grille,c);
    }
}
