import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sortie extends CaseTraversable {
    /* Attributs */
    private Image imSortie;
    private Clip sonSortie;
    
    /* Constructeur */
    public Sortie(int ligne, int colonne, Entite e) {
        super(ligne, colonne, e);
        imSortie = Toolkit.getDefaultToolkit().getImage("Media/Images/Cases/Sortie.png");
        try{
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("Media/Sons/Sortie.wav"));
        sonSortie = AudioSystem.getClip();
        sonSortie.open(audioIn);
        }catch(Exception error){System.err.print(error);}
    }

    /* Méthodes */
    @Override
    public void affiche(Graphics g, FenetreJeu fj, Case c){
        g.drawImage(imSortie, c.getX()*Jeu.tailleCase , c.getY()*Jeu.tailleCase, fj);
    }

    @Override
    public void entre(Bille b, Graphics g, FenetreJeu fj){
        fj.ecranVictoire(Demo.temps);
        sonSortie.setFramePosition(0);
        sonSortie.start();
    }

    @Override
    public void sort(Bille b, Graphics g, FenetreJeu fj){}

    @Override
    public void touche(Bille b, Graphics g,FenetreJeu fj){}
    
    @Override
    public double getFacAcceleration() {return 0.0;}

    @Override
    public double getFacFrottement() {return 0.0;}
}