// la classe CaseTraversable
public class CaseTraversable extends Case {
    // les attributs
    protected Entite contenu;

    // le constructeur
    public CaseTraversable(int ligne, int colonne, Entite e){
        super(ligne,colonne);
        this.contenu = e;
    }

    // la méthode estLibre
    public boolean estLibre(){
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
}
