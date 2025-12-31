import java.awt.*;
public class Trou extends CaseTraversable{
    private Image imTrou;
    public Trou(int ligne, int colonne){
        super(ligne, colonne, new Void());
        imTrou = Toolkit.getDefaultToolkit().getImage("Media/Images/Fonds/fond_trou.gif");
    }
    public boolean estVide(){
        return true;
    }

    @Override
    public void affiche(Graphics g,  Grille grille, int posLigne, int posColonne){
        g.drawImage(imTrou, posColonne, posLigne, grille);
    }
    @Override
    public void entre(Bille b){
    }
}
