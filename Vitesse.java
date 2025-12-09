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

    // la méthode getVitesseH
    public double getVitesseH() {
        return x;
    }
    
    // la méthode getVitesseV
    public double getVitesseV() {
        return y;
    }

    // la méthode setVitesse
    protected void setVitesse(double x, double y) {
        this.x = x; this.y = y;
    }

    // la méthode setVitesseH
    protected void setVitesseH(double x) {
        setVitesse(x, this.y);
    }

    // la méthode setVitesseV
    protected void setVitesseV(double y) {
        setVitesse(this.x, y);
    }

    // la méthode vitesseAbsolue
    public double vitesseAbsolue() {
        return Math.abs(x*x + y*y);
    }
}