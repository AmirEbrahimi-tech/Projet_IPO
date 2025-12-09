import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Terrain {

    private int hauteur, largeur;
    protected Case[][] carte;

    /* Initialisation d'un terrain Ã  partir de la description donnÃ©e par
       un fichier texte. Format du fichier de description :
       - hauteur et largeur sur la premiÃ¨re ligne
       - puis dessin du terrain (un caractÃ¨re == une case) avec le codage
         suivant
         '#' pour un mur
         ' ' (espace) pour une case libre
         'S' pour une sortie
         '@' pour une case libre contenant un obstacle
         'O' pour une case libre contenant la bille
    */
    public int getHauteur(){
        return this.hauteur;
    }    
    public int getLargeur(){
        return this.largeur;
    }
    public Terrain(String file) {
        try {
            Scanner sc = new Scanner(new FileInputStream(file));
            this.hauteur = sc.nextInt();
            this.largeur = sc.nextInt();
            sc.nextLine();
            this.carte = new Case[hauteur][largeur];
            Teleporteur temp = null;
            for (int l=0; l<hauteur; l++) {
                String line = sc.nextLine();
                for (int c=0; c<largeur; c++) {
                    Case cc;
                    Character ch = line.charAt(c);
                    switch (ch) {
                        case '#': cc = new CaseIntraversable(l, c); break;
                        case ' ': cc = new CaseTraversable(l, c, new Void()); break;
                        case 'O': cc = new Sortie(l, c,new Void()); break;
                        case '@': cc = new CaseTraversable(l, c, new Obstacle()); break;
                        case 'T' : cc = new Teleporteur(l, c); 
                                   if (temp==null){temp = (Teleporteur)cc;} 
                                   else {
                                    ((Teleporteur)cc).setSortie(temp);
                                    temp.setSortie((Teleporteur)cc);
                                    temp = null;
                                   }break;
                        case 'M': case '3': case 'W': case 'E':
                            cc = new CaseTraversable(l, c, new Monstre(5, Direction.ofChar(ch)));
                            break;
                        default:  cc = null; break;
                    }
                    carte[l][c] = cc;
                }
            }
            sc.close();
            if(temp!=null) throw new Error("Le nombre de téléporteurs n'est pas pair !");
        }
        catch (IOException e) { e.printStackTrace(); }
    }
}