public class Vitesse {
    /* Attributs */
    private double x, y;

    /* Constructeurs */
    public Vitesse() {
        this.x = 0.0; this.y = 0.0;
    }

    public Vitesse(double x, double y) {
        this.x = x; this.y = y;
    }

    /* Getters */
    public double getX(){return this.x;}
    public double getY(){return this.y;}

    /* Setters */
    protected void setVitesse(double x, double y) {
        this.x = x; this.y = y;
    }

    /* Méthodes */
    public double vitesseAbsolue() {
        return Math.sqrt(x*x + y*y);
    }

    public void renverseV() {
        y *= -1;
    }
    
    public void renverseH() {
        x *= -1;
    }

    public void multiplier(double coeff){
        x*=coeff;
        y*=coeff;
    }
}