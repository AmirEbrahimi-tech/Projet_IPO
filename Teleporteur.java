public class Teleporteur extends CaseTraversable{
    private Teleporteur sortie;
    private Boolean active;
    public Teleporteur(int ligne, int colonne){
        super(ligne, colonne, new Void());
    }
    public void setSortie(Teleporteur sortie){
        this.sortie = sortie;
    }
    public Teleporteur getSortie(){
        return this.sortie;
    }
    public void deplace(){
        this.sortie.desactivation();
        this.sortie.entre(this.getContenu());
        this.vide();
    }
    public boolean estActive(){
        return this.active;
    }
    public void activation(){
        this.active = true;
    }
    public void desactivation(){
        this.active = false;
    }

}
