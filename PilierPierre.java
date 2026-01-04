import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
// la classe PilierPierre un sous-type d'obstacle avec 5 points de vie
public class PilierPierre extends Obstacle {
    /* Attributs */
    private Image imPierre1;
    private Image imPierre2;
    private Image imPierre3;
    private Clip imPilFrappe;
    private Clip imPilDetruit;

    /* Constructeur */
    public PilierPierre() {
        // Initialisation de sa résistance
        super(3);
        // Chargement des images de texture
        imPierre1 = Toolkit.getDefaultToolkit().getImage("Media/Images/Obstacles/pilier_pierre_1.png");
        imPierre2 = Toolkit.getDefaultToolkit().getImage("Media/Images/Obstacles/pilier_pierre_2.png");
        imPierre3 = Toolkit.getDefaultToolkit().getImage("Media/Images/Obstacles/pilier_pierre_3.png");
        // Chargement des sons
        try{
        AudioInputStream audioIn1 = AudioSystem.getAudioInputStream(new File("Media/Sons/pillier_frappe.wav"));
        AudioInputStream audioIn2 = AudioSystem.getAudioInputStream(new File("Media/Sons/pillier_detruit.wav"));
        imPilFrappe = AudioSystem.getClip();
        imPilDetruit = AudioSystem.getClip();
        imPilFrappe.open(audioIn1);
        imPilDetruit.open(audioIn2);
        } catch(Exception e){System.err.print(e);}
    }

    /* Méthode */
    public void joueFrappe(){
        imPilFrappe.setFramePosition(0);
        imPilFrappe.start();
    }

    public void joueDetruit(){
        imPilDetruit.setFramePosition(0);
        imPilDetruit.start();
    }


    @Override
    public void interagit(){
        if(getResistance()==0){
            joueDetruit();
        }else{
            joueFrappe();
        }
    }
    @Override
    public void affiche(Graphics g, FenetreJeu fj, Case c) {
        switch(getResistance()) {
            case 3 -> g.drawImage(imPierre3, c.getX()*Jeu.tailleCase , c.getY()*Jeu.tailleCase, fj);
            case 2 -> g.drawImage(imPierre2, c.getX()*Jeu.tailleCase , c.getY()*Jeu.tailleCase, fj);
            case 1 -> g.drawImage(imPierre1, c.getX()*Jeu.tailleCase , c.getY()*Jeu.tailleCase, fj);
        }
    }
}