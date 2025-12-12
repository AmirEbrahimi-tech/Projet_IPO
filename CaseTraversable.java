// la classe CaseTraversable
public class CaseTraversable extends Case {
    // les attributs
    protected Entite contenu;

    // le constructeur
    public CaseTraversable(int ligne, int colonne, Entite e) {
        super(ligne,colonne);
        this.contenu = e;
    }

    // la méthode estLibre
    @Override
    public boolean estVide(){
        return this.contenu instanceof Void;    
    }

    // la méthode getContenu
    public Entite getContenu(){
        return this.contenu;
    }

    // la méthode vide
    public void vide(){
        this.contenu = new Void();
    }

    // la méthod entre
    public void entre(Entite e){
        this.contenu = e;
    }
}
