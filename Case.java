public abstract class Case{
    private final int ligne,colonne;
    public Case(int ligne, int colonne){
        this.ligne=ligne;this.colonne=colonne;
    }
    public abstract boolean estLibre();
}