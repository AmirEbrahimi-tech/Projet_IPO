public class Teleporteur extends CaseTraversable{
    /* Attributs */
    private Teleporteur sortie;
    private Boolean active;

    /* Constructeur */
    public Teleporteur(int ligne, int colonne){
        super(ligne, colonne, new Void());
    }

    /* Setter */
    public void setSortie(Teleporteur sortie){
        this.sortie = sortie;
    }

    /* Getter */
    public Teleporteur getSortie(){
        return this.sortie;
    }

    /* Méthodes */
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

    @Override
    public double getFacAcceleration() {return 0.0;}

    @Override
    public double getFacFrottement() {return 0.0;}
}
