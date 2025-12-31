public class CaseIntraversable extends Case {
    // Constructeur
    public CaseIntraversable(int ligne, int colonne) {
        super(ligne, colonne);
    }

    //Une case intraversable n'est jamais vide
    public boolean estVide() {return false;}
}