// la classe Vitesse
public class Vitesse {
    // les attributs
    protected double x, y;

    // le constructeur
    public Vitesse(double x, double y) {
        this.x = x; this.y = y;
    }
    
    // le constructeur par défaut
    public Vitesse() {
        this.x = 0.0; this.y = 0.0;
    }

    // la méthode setVitesse
    protected void setVitesse(double x, double y) {
        this.x = x; this.y = y;
    }

    // la méthode vitesseAbsolue
    public double vitesseAbsolue() {
        return Math.sqrt(x*x + y*y);
    }

    // la méthode renverseV
    public void renverseV() {
        y *= -1;
    }

    // la méthode renverseH
    public void renverseH() {
        x *= -1;
    }
}