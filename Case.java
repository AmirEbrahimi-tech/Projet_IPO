public abstract class Case extends Carre {
    // Attributs
    protected final int ligne,colonne;

    // Constructeur
    public Case(int ligne, int colonne){
        this.ligne = ligne;
        this.colonne = colonne;
    }
    public void entre(Bille b){}
    public void sort(Bille b){}
    public void touche(Bille b){}
}