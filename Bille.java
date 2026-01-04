import java.awt.*;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Bille {
    /* Attributs */
    private int vies;
    private Position pos;
    private Vitesse vit;
    private final int rayon = 12;
    private Image imBille;
    private Clip sonRebond;

    /* Constructeurs */
    public Bille(Position pos, Vitesse vit) {
        this.pos = pos; this.vit = vit;
        this.vies = 3;
        //Chargement de l'image de la bille
        imBille = Toolkit.getDefaultToolkit().getImage("Media/Images/Bille/Bille.gif");
        //Chargement du son du rebon
        try{
        AudioInputStream audioIn1 = AudioSystem.getAudioInputStream(new File("Media/Sons/Rebond.wav"));
        sonRebond = AudioSystem.getClip();
        sonRebond.open(audioIn1);
        }catch(Exception e){System.err.print(e);}
    }

    /* Getters */
    public Position getPosition(){return this.pos;}
    public double getPositionX(){return this.pos.getX();}
    public double getPositionY(){return this.pos.getY();}
    public double getVitesseX(){return this.vit.getX();}
    public double getVitesseY(){return this.vit.getY();}
    public Vitesse getVitesse(){return this.vit;}
    public int getRayon(){return this.rayon;}
    public int getVies(){return this.vies;}

    /* Méthodes */
    // On retourne la direction de la bille
    public double getDirectionH() {
        return getVitesseX() / vit.vitesseAbsolue();
    }

    public double getDirectionV() {
        return getVitesseY() / vit.vitesseAbsolue();
    }

    // On déplace la bille grâce à sa vitesse en tenant compte du frottement
    public void deplacer(Jeu jeu, Graphics g, FenetreJeu fj) {
        // System.out.println(pos.x + " , " + pos.y);
        pos.set(getPositionX() + getVitesseX(),getPositionY() + getVitesseY());
        frottement(jeu);
        
        Case courante  = jeu.getCase((int) getPositionY() / FenetreJeu.tailleCase, (int) getPositionX() / FenetreJeu.tailleCase);
        Case cible  = jeu.getCase((int) (getPositionY() + getVitesseY()) / FenetreJeu.tailleCase, (int) (getPositionX() + getVitesseX()) / FenetreJeu.tailleCase);
        if (courante != cible) {    //Si la bille change de case
            courante.sort(jeu.getBille(), g, fj);
            cible.entre(jeu.getBille(),g ,fj);
        }
    }

    public void frottement(Jeu jeu) {
        if (vit.vitesseAbsolue() > 0) {
            CaseTraversable courante  = (CaseTraversable) jeu.getCase((int) getPositionY() / FenetreJeu.tailleCase, (int) getPositionX() / FenetreJeu.tailleCase);
            double f = courante.getFacFrottement();
            getVitesse().setVitesse(getVitesseX() * f, getVitesseY() * f);
        }
    }

    public void affiche(Graphics g, FenetreJeu fj){
        g.drawImage(imBille, (int)getPositionX() - rayon , (int)getPositionY() - rayon, fj);
    }

    public void joueSon(){
        sonRebond.setFramePosition(0);
        sonRebond.start();
    }

    public void decremente(){
        vies--;
    }

    public boolean estMort() {
        return vies <= 0;
    }
}