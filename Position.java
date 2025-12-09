// la classe Position
public class Position {
    // les attributs
    protected double x, y;

    // le constructeur
    public Position(double x, double y) {
        this.x = x; this.y = y;
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
        this.x = x; this.y = y;
    }

    // la méthode setX(x, _)
    protected void setX(double x) {
        set(x, this.y);
    }

    // la méthode setY(_, y)
    protected void setY(double y) {
        set(this.x, y);
    }
}