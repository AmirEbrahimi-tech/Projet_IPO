// la classe Position
public class Position {
    /* Attributs */
    private double x, y;

    /* Constructeur */
    public Position(double x, double y) {
        this.x = x; this.y = y;
    }

    /* Getters */
    public double getX(){return this.x;}
    public double getY(){return this.y;}

    /* Setters */
    protected void set(double x, double y) {
            this.x = x; this.y = y;
        
    }
}