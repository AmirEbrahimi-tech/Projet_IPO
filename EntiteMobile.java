import java.awt.*;
import javax.swing.*;
public abstract class EntiteMobile extends Entite {
    /* Attribut */
    private Direction direction;

    /* Constructeur */
    public EntiteMobile(int resistance, Direction dir) {
        super(resistance);
        this.direction = dir;
    }

    /* Méthodes */
    public Direction getDirection() {return direction;}
    public void setDirection(Direction dir) {direction = dir;} 
    public void affiche(Graphics g, FenetreJeu fj,Case c) {}
    public abstract void action(Case courante, Case cible);
}