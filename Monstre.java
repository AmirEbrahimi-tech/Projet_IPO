import java.awt.*;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Monstre extends EntiteMobile {
    /* Attributs */
    private Image imMonstreB;
    private Image imMonstreH;
    private Image imMonstreD;
    private Image imMonstreG;
    private Clip sonMonstre;
    
    /* Constructeur */
    public Monstre(int resistance, Direction dir) {
        super(resistance, dir);
        // Chargement des images de texture
        imMonstreB = Toolkit.getDefaultToolkit().getImage("Media/Images/Monstre/Monstre_0.png");
        imMonstreH = Toolkit.getDefaultToolkit().getImage("Media/Images/Monstre/Monstre_1.png");
        imMonstreD = Toolkit.getDefaultToolkit().getImage("Media/Images/Monstre/Monstre_2.png");
        imMonstreG = Toolkit.getDefaultToolkit().getImage("Media/Images/Monstre/Monstre_3.png");

        try{
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("Media/Sons/Monstre.wav"));
        sonMonstre = AudioSystem.getClip();
        sonMonstre.open(audioIn);
        }catch(Exception error){System.err.print(error);}
    }

    /* Méthodes */

    public void affiche(Graphics g, FenetreJeu fj, Case c) {
        switch(getDirection()) {
            case sud -> g.drawImage(imMonstreB, c.getX()*Jeu.tailleCase , c.getY()*Jeu.tailleCase, fj);       
            case nord -> g.drawImage(imMonstreH, c.getX()*Jeu.tailleCase , c.getY()*Jeu.tailleCase, fj);       
            case est -> g.drawImage(imMonstreD, c.getX()*Jeu.tailleCase , c.getY()*Jeu.tailleCase, fj);       
            case ouest -> g.drawImage(imMonstreG, c.getX()*Jeu.tailleCase , c.getY()*Jeu.tailleCase, fj);       
        }
    }

    public void joueSon(){
        sonMonstre.setFramePosition(0);
        sonMonstre.start();
    }

    public void action(Case courante, Case cible) {
        if (!(courante instanceof CaseTraversable)) return;
        CaseTraversable src = (CaseTraversable) courante;

        if (cible instanceof CaseIntraversable || !cible.estVide() || ((CaseTraversable)cible).contientBille()||cible instanceof Sortie||cible instanceof Trou) { 
            this.setDirection(Direction.random()); 
            return; 
        }
        CaseTraversable dst = (CaseTraversable) cible;

        Entite tmp = dst.getContenu();

        if (dst.estVide()) {
            dst.entre(src.getContenu());
            src.vide();
            return;
        }

        if (tmp instanceof Obstacle) {
            tmp.decremente();
            if (tmp.getResistance() <= 0) {
                dst.vide();
                dst.entre(src.getContenu());
                src.vide();
            }
            return;
        }

        Entite e = src.getContenu();
        if (e instanceof Monstre) {
            ((Monstre) e).setDirection(Direction.random());
        }
    }

}