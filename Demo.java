public class Demo {
    public static long temps;
    public static void main(String[] args) {
        Jeu jeu = new Jeu("Maps/labyTrou.txt");
        FenetreJeu fj = new FenetreJeu(jeu);
        long debut = System.currentTimeMillis();
        try {
            while (true) {
                fj.repaint();
                jeu.tour();
                long fin = System.currentTimeMillis();
                temps = fin-debut;
                Thread.sleep(16);       //On affiche 30 images par seconde
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}