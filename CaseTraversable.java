public class CaseTraversable extends Case {
    protected Entite contenu;
    public CaseTraversable(int ligne, int colonne){
        super(ligne,colonne);
        this.contenu = new Void();
    }
    public boolean estLibre(){
        return this.contenu instanceof Void;    
    }
}
