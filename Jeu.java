import java.util.ArrayList;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Jeu {

    Terrain terrain;
    static int sortis;

    /* Initialisation d'un jeu avec le terrain initial décrit dans
       le fichier [f] donné en paramètre */
    public Jeu(String f) {
        terrain = new Terrain(f);
        sortis = 0;
    }

    // la méthode pour debugging
    public void debug() {
        ArrayList<CaseTraversable> mobiles = new ArrayList<>();

        for (int i = 0; i < terrain.carte.length; i++) {
            for (int j = 0; j < terrain.carte[0].length; j++) {
                Case c = terrain.carte[i][j];
                if (c instanceof CaseTraversable) {
                    Entite e = ((CaseTraversable) c).getContenu();
                    if (e instanceof EntiteMobile) {
                        mobiles.add((CaseTraversable) c);
                    }
                }
            }
        }

        for (int i = 0; i < mobiles.size(); i++) {
            CaseTraversable c = mobiles.get(i);
            String type;
            if (c.getContenu() instanceof Personnage) {
                type = "personnage";
            } else if (c.getContenu() instanceof Monstre) {
                type = "monstre";
            } else type = "???";
            System.out.println("case : (" + c.y + "," + c.x + ") type : " + type + " résistance : " + c.getContenu().resistance);
        }
    }

    // la méthode partieFinie
    public boolean partieFinie() {
        ArrayList<CaseTraversable> personnages = new ArrayList<>();

        for (int i = 0; i < terrain.carte.length; i++) {
            for (int j = 0; j < terrain.carte[0].length; j++) {
                Case c = terrain.carte[i][j];
                if (c instanceof CaseTraversable) {
                    Entite e = ((CaseTraversable) c).getContenu();
                    if (e instanceof Personnage) {
                        personnages.add((CaseTraversable) c);
                    }
                }
            }
        }

        return sortis == 2 || personnages.isEmpty();
    }

    // la méthode tour
    // public void tour() {
    //     Random rnd = new Random();

    //     ArrayList<CaseTraversable> mobiles = new ArrayList<>();
    //     for (int i = 0; i < terrain.carte.length; i++) {
    //         for (int j = 0; j < terrain.carte[0].length; j++) {
    //             Case c = terrain.carte[i][j];
    //             if (c instanceof CaseTraversable) {
    //                 Entite e = ((CaseTraversable) c).getContenu();
    //                 if (e instanceof EntiteMobile) {
    //                     mobiles.add((CaseTraversable)c);
    //                 }
    //             }
    //         }
    //     }

    //     if (mobiles.isEmpty()) return; // nothing to do

    //     CaseTraversable src = mobiles.get(rnd.nextInt(mobiles.size()));
    //     EntiteMobile ent = (EntiteMobile) src.getContenu();

    //     int newLig = src.ligne;
    //     int newCol = src.colonne;

    //     switch (ent.direction) {
    //         case nord:
    //             newLig = src.ligne - 1;
    //             break;
    //         case sud:
    //             newLig = src.ligne + 1;
    //             break;
    //         case est:
    //             newCol = src.colonne + 1;
    //             break;
    //         case ouest:
    //             newCol = src.colonne - 1;
    //             break;
    //         default:
    //             return;
    //     }

    //     if (newLig < 0 || newLig >= terrain.carte.length || newCol < 0 || newCol >= terrain.carte[0].length) return;

    //     Case target = terrain.carte[newLig][newCol];
    //     // ent.action(src, target);
    // }

    // public void deplacement(KeyEvent e){
    //     CaseTraversable perso = null;
    //     for (int i = 0; i < terrain.carte.length; i++) {
    //         for (int j = 0; j < terrain.carte[0].length; j++) {
    //             Case c = terrain.carte[i][j];
    //             if (c instanceof CaseTraversable) {
    //                 Entite en = ((CaseTraversable) c).getContenu();
    //                 if (en instanceof Personnage) {
    //                     perso = (CaseTraversable)c;
    //                     break;
    //                 }
    //             }
    //         }
    //     }
    //     Personnage p = (Personnage) perso.getContenu();

    //     int newLig = perso.ligne;
    //     int newCol = perso.colonne;

    //     switch (e.getKeyCode()) {
    //         case KeyEvent.VK_UP    : 
    //             if(perso.ligne-1>=0) newLig = perso.ligne - 1;break;
    //         case KeyEvent.VK_DOWN  :
    //             if(perso.ligne+1<=terrain.carte.length) newLig = perso.ligne + 1;break;
    //         case KeyEvent.VK_RIGHT :
    //             if(perso.colonne-1>=0) newCol = perso.colonne + 1;break;
    //         case KeyEvent.VK_LEFT  :
    //             if(perso.colonne+1<=terrain.carte[0].length) newCol = perso.colonne - 1;break;
    //         default:
    //             return;
    //     }
    //     Case target = terrain.carte[newLig][newCol];
    //     p.deplacement(perso, target);
    // }

    // public static void main(String[] args) {
    //     Integer score = 0;
    //     Jeu j = new Jeu("laby1.txt");
    //     j.terrain.print();
    //     j.debug();
    //     boolean ok = true;
    //     while (ok) {
    //         try {
    //             Thread.sleep(1000);
    //             j.tour();
    //             j.terrain.print();
    //             j.debug();
    //             if (j.partieFinie()) ok = false;
    //         } catch (InterruptedException e) {
    //             Thread.currentThread().interrupt();   // restore flag
    //             System.out.println("Sleep was interrupted");
    //         }
    //     }
    // }
}
