public class Demo {
    public static long temps;
    public static void main(String[] args) {
        Jeu jeu = new Jeu("Maps/laby_test2.txt");
        FenetreJeu fj = new FenetreJeu(jeu);
        long debut = System.currentTimeMillis();
        try {
            while (true) {
                // System.out.println("dirH : " + grille.bille.getDirectionH() + " dirV : " + grille.bille.getDirectionV());
                fj.repaint();
                jeu.tour();
                long fin = System.currentTimeMillis();
                temps = fin-debut;
                if (jeu.getBille().estMort()) {
                    Thread.sleep(10000);
                    break;
                }
                Thread.sleep(16);       //On affiche 30 images par seconde
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}