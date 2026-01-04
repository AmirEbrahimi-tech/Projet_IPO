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
    // private Image imChute;
    private Image imBille;
    private Clip sonRebond;
    private Clip sonChute;

    /* Constructeurs */
    public Bille() {
        this.pos = new Position(0, 0);
        this.vit = new Vitesse();
        this.vies = 3;
        imBille = Toolkit.getDefaultToolkit().getImage("Media/Images/Bille/Bille.gif");
        // imChute = Toolkit.getDefaultToolkit().getImage("Media/Images/Bille/Chute.gif");
        try{
        AudioInputStream audioIn1 = AudioSystem.getAudioInputStream(new File("Media/Sons/Rebond.wav"));
        sonRebond = AudioSystem.getClip();
        sonRebond.open(audioIn1);
        }catch(Exception e){System.err.print(e);}
        try{
        AudioInputStream audioIn2 = AudioSystem.getAudioInputStream(new File("Media/Sons/Chute.wav"));
        sonChute = AudioSystem.getClip();
        sonChute.open(audioIn2);
        }catch(Exception e){System.err.print(e);}
    }

    public Bille(Position pos, Vitesse vit) {
        this.pos = pos; this.vit = vit;
        this.vies = 3;
        imBille = Toolkit.getDefaultToolkit().getImage("Media/Images/Bille/Bille.gif");
        try{
        AudioInputStream audioIn1 = AudioSystem.getAudioInputStream(new File("Media/Sons/Rebond.wav"));
        sonRebond = AudioSystem.getClip();
        sonRebond.open(audioIn1);
        }catch(Exception e){System.err.print(e);}
        try{
        AudioInputStream audioIn2 = AudioSystem.getAudioInputStream(new File("Media/Sons/Chute.wav"));
        sonChute = AudioSystem.getClip();
        sonChute.open(audioIn2);
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

    // On verifie si la bille est contenue dans les bornes X et Y
    // public boolean estDedans(double borneX, double borneY) {
    //     return ((0 < getPositionX() - rayon + getVitesseX() && getPositionX() + rayon + getVitesseX() < borneX) && (0 < getPositionY() - rayon + getVitesseY() && getPositionY() + rayon + getVitesseY() < borneY));
    // }

    // On déplace la bille grâce à sa vitesse en tenant compte du frottement
    public void deplacer(Jeu jeu, Graphics g, FenetreJeu fj) {
        // System.out.println(pos.x + " , " + pos.y);
        // if (estDedans(Jeu.largeur * Jeu.tailleCase, Jeu.hauteur * Jeu.tailleCase)) 
        pos.set(getPositionX() + getVitesseX(),getPositionY() + getVitesseY()); // 400 est temporaire!!!!! il faut ajouter une variable static a la classe fenetreJeu qui contient la taille du fenetre et les utiliser ici
        frottement();
        
        Case courante  = jeu.getCase((int) getPositionY() / FenetreJeu.tailleCase, (int) getPositionX() / FenetreJeu.tailleCase);
        Case cible  = jeu.getCase((int) (getPositionY() + getVitesseY()) / FenetreJeu.tailleCase, (int) (getPositionX() + getVitesseX()) / FenetreJeu.tailleCase);
        if (courante != cible) {
            courante.sort(jeu.getBille(), g, fj);
            cible.entre(jeu.getBille(),g ,fj);
        }
    }

    public void frottement() {
        if (vit.vitesseAbsolue() > 0) {
            getVitesse().setVitesse(getVitesseX()*0.98, getVitesseY()*0.98);
            // 0.98 doit est remplacé par une variable qui est donnée en fonction de la case qu'on est dessus
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

    public void chute(Graphics g, FenetreJeu fj) {
        sonChute.setFramePosition(0);
        sonChute.start();
        pos.set((int)getPositionX(),(int)getPositionY());
        g.drawImage(imBille, (int)getPositionX(), (int)getPositionY(), fj);
        vies = 0;
    }

    public boolean estMeurt() {
        return vies <= 0;
    }
}