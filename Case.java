// la classe abstrait Case
public abstract class Case{
    // les attributs
    private final int ligne,colonne;

    // le constructeur
    public Case(int ligne, int colonne){
        this.ligne=ligne;
        this.colonne=colonne;
    }
    public abstract boolean estLibre();
}