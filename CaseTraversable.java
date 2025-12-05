public class CaseTraversable extends Case {
    protected Entite contenu;
    public CaseTraversable(int ligne, int colonne, Entite e){
        super(ligne,colonne);
        this.contenu = e;
    }
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
