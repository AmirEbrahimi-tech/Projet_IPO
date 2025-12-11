import javax.swing.Timer;
import java.awt.event.KeyEvent;

public class Donjon {
    public void keyTyped(KeyEvent e, Jeu j){
        j.deplacement(e);
    }
    public static void main(String[] args) {
        int tempo = 100;
        Jeu jeu = new Jeu("laby_test.txt");
        FenetreJeu graphic = new FenetreJeu(jeu.terrain);
        Timer timer = new Timer(tempo, en -> {
            graphic.repaint();
            if (jeu.partieFinie()) { graphic.ecranFinal(jeu.sortis);}
        });
        timer.setInitialDelay(0);
        timer.start();
    }
}
