import java.awt.*;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
public class Trou extends CaseTraversable{
    /* Attributs */
    private Image imTrou;
    private Clip sonChute;
    

    /* Constructeur */
    public Trou(int x, int y){
        super(x, y, new Void());
        imTrou = Toolkit.getDefaultToolkit().getImage("Media/Images/Fonds/fond_trou.gif");
        try{
        AudioInputStream audioIn2 = AudioSystem.getAudioInputStream(new File("Media/Sons/Chute.wav"));
        sonChute = AudioSystem.getClip();
        sonChute.open(audioIn2);
        }catch(Exception e){System.err.print(e);}
    }

    /* Méthodes */
    public boolean estVide(){
        return true;
    }

    @Override
    public void affiche(Graphics g,  FenetreJeu fj, Case c){
        g.drawImage(imTrou, c.getX()*Jeu.tailleCase , c.getY()*Jeu.tailleCase, fj);
    }

    @Override
    public void entre(Bille b, Graphics g, FenetreJeu fj){
        fj.ecranDefaite("Vous êtes tombé dans le vide !");
        sonChute.setFramePosition(0);
        sonChute.start();
    }

    @Override
    public void sort(Bille b, Graphics g, FenetreJeu fj){}

    @Override
    public void touche(Bille b, Graphics g, FenetreJeu fj){}
    
    @Override
    public double getFacAcceleration() {return 0.0;}

    @Override
    public double getFacFrottement() {return 0.0;}
}
