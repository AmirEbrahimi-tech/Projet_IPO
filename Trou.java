public class Trou extends CaseTraversable{
    public Trou(int ligne, int colonne){
        super(ligne, colonne, new Void());
    }
    public boolean estVide(){
        return true;
    }

    @Override
    public void entre(Bille b){
        

    }
}
