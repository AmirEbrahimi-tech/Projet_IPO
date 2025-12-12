// la classe CaseIntraversable
public class CaseIntraversable extends Case {
    // les attributs

    // le constructeur
    public CaseIntraversable(int ligne, int colonne) {
        super(ligne, colonne);
    }

    // la méthode estLibre
    public boolean estVide() {
        return false;
    }
}