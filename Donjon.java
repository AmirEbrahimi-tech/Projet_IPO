import java.awt.event.KeyEvent;

public class Donjon {
    public void keyTyped(KeyEvent e, Jeu j){
        j.deplacement(e);
    }
    public static void main(String[] args) {
        int tempo = 100;
        Jeu jeu = new Jeu("labyBille.txt");
        FenetreJeu graphic = new FenetreJeu(jeu.terrain);
        // Timer timer = new Timer(tempo, en -> {
        //     graphic.bille.deplacer();
        //     graphic.repaint();
        //     jeu.debug();
        //     // if (jeu.partieFinie()) { graphic.ecranFinal(jeu.sortis);}
        // });
        // timer.setInitialDelay(0);
        // timer.start();
        try {
            while (true) {
            // graphic.bille.deplacer();
            graphic.repaint();
            jeu.debug();
            Thread.sleep(10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
