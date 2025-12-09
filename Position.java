// la classe Position
public class Position {
    // les attributs
    protected double x, y, borneX, borneY;

    // le constructeur
    public Position(double x, double y, double bX, double bY) {
        this.x = x; this.y = y;
        this.borneX = bY; this.borneY = bY;
    }

    // la méthode x
    public double getX() {
        return x;
    }

    // la méthode y
    public double getY() {
        return y;
    }

    // la méthode set(x, y)
    protected void set(double x, double y) {
        if ( (x > 0 && x < this.borneX) && (y > 0 && y < this.borneY)) {
            this.x = x; this.y = y;
        }
    }
}