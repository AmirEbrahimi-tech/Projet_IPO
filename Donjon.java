import java.awt.event.KeyEvent;
import javax.swing.Timer;

public class Donjon {
    public void keyTyped(KeyEvent e, Jeu j){
        j.deplacement(e);
    }
    public static void main(String[] args) {
        int tempo = 100;
        Jeu jeu = new Jeu("labyBille.txt");
        FenetreJeu graphic = new FenetreJeu(jeu.terrain);
        Timer timer = new Timer(tempo, en -> {
            graphic.repaint();
            // if (jeu.partieFinie()) { graphic.ecranFinal(jeu.sortis);}
        });
        timer.setInitialDelay(0);
        timer.start();
    }
}
