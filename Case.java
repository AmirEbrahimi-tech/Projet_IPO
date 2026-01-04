import java.awt.*;
public abstract class Case {
    /* Attributs */
    private final int x,y;
    protected double facAcceleration;
    protected double facFrottement;
    protected boolean contientBille = false;
    protected Image imImpact;

    /* Constructeur */
    public Case(int x, int y){
        this.x = x;
        this.y = y;
        facAcceleration = 0;
        facFrottement   = 0;
        imImpact = Toolkit.getDefaultToolkit().createImage("Media/Images/Bille/Impact.gif");
    }

    /* Getters */
    public int getX(){return this.x;}
    public int getY(){return this.y;}
    public double getFacFrottement() {return this.facFrottement;}
    public double getFacAcceleration() {return this.facAcceleration;}

    /* Méthodes Abstraites */
    public abstract void affiche(Graphics g, FenetreJeu fj, Case c);
    public abstract boolean estVide();
    public abstract boolean contientBille();
    public abstract void entre(Bille b);
    public abstract void sort(Bille b);
    public abstract void touche(Bille b);
}