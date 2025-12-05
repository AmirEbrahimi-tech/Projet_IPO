// la classe CaseTraversable
public class CaseTraversable extends Case {
    // les attributs
    protected Entite contenu;

    // le constructeur
    public CaseTraversable(int ligne, int colonne){
        super(ligne,colonne);
        this.contenu = new Void();
    }

    // la méthode estLibre
    public boolean estLibre(){
        return this.contenu instanceof Void;    
    }
}
