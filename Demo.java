public class Demo {
    public static void main(String[] args) {
        Jeu jeu = new Jeu("Maps/labyTrou.txt"); //On charge le fichier text
        Grille grille = new Grille(jeu.terrain);
        try {
            while (true) {
                // System.out.println("dirH : " + grille.bille.getDirectionH() + " dirV : " + grille.bille.getDirectionV());
                // grille.bille.deplacer();
                grille.repaint();
                // int t = grille.touche();
                // if (t == 1 || t == 3) grille.bille.vit.renverseH();
                // else if (t == 2 || t == 4) grille.bille.vit.renverseV();
                Thread.sleep(16);       //On affiche 30 images par seconde
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}