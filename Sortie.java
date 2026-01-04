// la classe Sortie
public class Sortie extends CaseTraversable {
    // les attributs

    // le constructeur
    public Sortie(int ligne, int colonne, Entite e) {
        super(ligne, colonne, e);
    }

    @Override
    public double getFacAcceleration() {return 0.0;}

    @Override
    public double getFacFrottement() {return 0.0;}
}