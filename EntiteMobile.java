// la classe EntiteMobile
public class EntiteMobile extends Entite {
    // les attributs
    protected Direction direction;

    // le constrcuteur
    public EntiteMobile(int resistance, Direction dir) {
        super(resistance);
        this.direction = dir;
    }
}