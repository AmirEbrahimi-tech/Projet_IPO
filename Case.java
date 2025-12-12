// la classe abstrait Case
public abstract class Case extends Carre {
    // les attributs
    protected final int ligne,colonne;

    // le constructeur
    public Case(int ligne, int colonne){
        this.ligne = ligne;
        this.colonne = colonne;
    }
    //Méthodes
    @Override
    public void enter(Bille b){
        
    }

    @Override
    public void leave(Bille b){

    }

    @Override
    public void touche(Bille b){

    }
    public abstract boolean estVide();
}