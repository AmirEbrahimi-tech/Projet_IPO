public class EntiteMobile extends Entite {
    // Attributs
    protected Direction direction;

    // Constucteur
    public EntiteMobile(int resistance, Direction dir) {
        super(resistance);
        this.direction = dir;
    }
}