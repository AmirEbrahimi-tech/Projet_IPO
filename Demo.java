public class Demo {
    
    public static void main(String[] args) {
        Jeu jeu = new Jeu("Maps/laby_test2.txt");
        FenetreJeu fj = new FenetreJeu(jeu);
        try {
            while (true) {
                // System.out.println("dirH : " + grille.bille.getDirectionH() + " dirV : " + grille.bille.getDirectionV());
                fj.repaint();
                Thread.sleep(16);       //On affiche 30 images par seconde
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}