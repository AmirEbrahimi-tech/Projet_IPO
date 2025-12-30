
public class Bille2 {
    // les attributs
    protected Position pos;
    protected Position positionPrec;
    protected Vitesse vit;
    protected final int rayon = 8;
    // à voir ...

    // le constructeur ar défaut
    public Bille2() {
        this.pos = new Position(0, 0);
        this.positionPrec = null;
        this.vit = new Vitesse();
    }

    // le constructeur
    public Bille2(Position pos, Vitesse vit) {
        this.pos = pos; this.vit = vit;
        this.positionPrec = null;
    }

    // la méthode getDirectionH
    public double getDirectionH() {
        return vit.x / vit.vitesseAbsolue();
    }

    // la méthode getDirectionH
    public double getDirectionV() {
        return vit.y / vit.vitesseAbsolue();
    }

    // la méthode estDedans
    public boolean estDedans(double borneX, double borneY) {
        return ((0 < pos.x - rayon + vit.x && pos.x + rayon + vit.x < borneX) && (0 < pos.y - rayon + vit.y && pos.y + rayon + vit.y < borneY));
    }

    // la méthode deplacer
    public void deplacer() {
        // System.out.println(pos.x + " , " + pos.y);
        if (estDedans(Grille.largeur * Grille.tailleCase, Grille.hauteur * Grille.tailleCase)) pos.set(pos.x + vit.x,pos.y + vit.y); // 400 est temporaire!!!!! il faut ajouter une variable static au class fenetreJeu qui contient la taille du fenetre et les utiliser ici
        frottement();
    }

    // la méthode frottement
    public void frottement() {
        if (vit.vitesseAbsolue() > 0) {
            vit.x *= 0.98; vit.y *= 0.98; // 0.98 doit est remplacer par une variable qui est donnée en fonction de la case qu'on est dessus
        }
    }
}