// la classe Personnage
public class Personnage extends EntiteMobile {
    // les attributs

    // le constructeur
    public Personnage(int resistance, Direction dir) {
        super(resistance, dir);
    }
    public void deplacement(Case courante,  Case cible){
        if (!(courante instanceof CaseTraversable)) return;
            CaseTraversable src = (CaseTraversable) courante;

        if (!(cible instanceof CaseTraversable)) return;
        CaseTraversable dst = (CaseTraversable) cible;

        Entite tmp = dst.getContenu();

        if (src instanceof Sortie) {
            src.vide();
            Jeu.sortis++;
            return;
        }

        if (dst.estVide()) {
            dst.entre(src.getContenu());
            src.vide();
            return;
        }

        if (tmp instanceof Obstacle) {
            tmp.resistance -= 1;
            if (tmp.resistance <= 0) {
                dst.vide();
                dst.entre(src.getContenu());
                src.vide();
            }
            return;
        }
    }
}