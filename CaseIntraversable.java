// la classe CaseIntraversable
public class CaseIntraversable extends Case {
    // les attributs

    // le constructeur
    public CaseIntraversable(int ligne, int colonne) {
        super(ligne, colonne);
    }
    public boolean estLibre(){
        return false;
    }
}