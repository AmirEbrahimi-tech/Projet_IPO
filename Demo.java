public class Demo {
    
    public static void main(String[] args) {
        Jeu jeu = new Jeu("Maps/labyTrou.txt"); //On charge le fichier text
        Grille grille = new Grille(jeu.terrain);
        FenetreJeu fj = new FenetreJeu(grille);
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