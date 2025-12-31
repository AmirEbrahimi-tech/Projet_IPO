public class Bille {
    // Attributs
    private int vies;
    protected Position pos;
    protected Position positionPrec;
    protected Vitesse vit;
    protected final int rayon = 12;
    // à voir ...

    // Constructeurs
    public Bille() {
        this.pos = new Position(0, 0);
        this.positionPrec = null;
        this.vit = new Vitesse();
        this.vies = 3;
    }

    public Bille(Position pos, Vitesse vit) {
        this.pos = pos; this.vit = vit;
        this.positionPrec = null;
        this.vies = 3;
    }

    // On retourne la direction de la bille
    public double getDirectionH() {
        return vit.x / vit.vitesseAbsolue();
    }

    public double getDirectionV() {
        return vit.y / vit.vitesseAbsolue();
    }

    // On verifie si la bille est contenue dans les bornes X et Y
    public boolean estDedans(double borneX, double borneY) {
        return ((0 < pos.x - rayon + vit.x && pos.x + rayon + vit.x < borneX) && (0 < pos.y - rayon + vit.y && pos.y + rayon + vit.y < borneY));
    }

    // On déplace la bille grâce à sa vitesse en tenant compte du frottement
    public void deplacer() {
        // System.out.println(pos.x + " , " + pos.y);
        if (estDedans(Grille.largeur * Grille.tailleCase, Grille.hauteur * Grille.tailleCase)) pos.set(pos.x + vit.x,pos.y + vit.y); // 400 est temporaire!!!!! il faut ajouter une variable static a la classe fenetreJeu qui contient la taille du fenetre et les utiliser ici
        frottement();
    }

    public void frottement() {
        if (vit.vitesseAbsolue() > 0) {
            vit.x *= 0.98; vit.y *= 0.98; // 0.98 doit est remplacé par une variable qui est donnée en fonction de la case qu'on est dessus
        }
    }
}