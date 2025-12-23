public class Demo {
    public static void main(String[] args) {
        Jeu jeu = new Jeu("labyBille.txt");
        Grille grille = new Grille(jeu.terrain);
        try {
            while (true) {
            grille.bille.deplacer();
            grille.repaint();
            Thread.sleep(10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}