// la classe abstrait Case
public abstract class Case{
    // les attributs
    private final int ligne,colonne;

    // le constructeur
    public Case(int ligne, int colonne){
        this.ligne=ligne;
        this.colonne=colonne;
    }

    // la méthode astrait estLibre
    public abstract boolean estLibre();
}