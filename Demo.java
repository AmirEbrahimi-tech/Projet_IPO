public class Demo {
    
    public static void main(String[] args) {
        Jeu jeu = new Jeu("Maps/labyTrou.txt");
        FenetreJeu fj = new FenetreJeu(jeu);
        long debut = System.currentTimeMillis();
        try {
            while (true) {
                // System.out.println("dirH : " + grille.bille.getDirectionH() + " dirV : " + grille.bille.getDirectionV());
                fj.repaint();
                jeu.tour();
                if (jeu.getBille().estMeurt()) {
                    long fin = System.currentTimeMillis();
                    fj.ecranDefaite(fin - debut);
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