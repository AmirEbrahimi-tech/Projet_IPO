import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;


public class Terrain {

    private int hauteur, largeur;
    private Case[][] carte;

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

    /* Getters */
    public int getHauteur(){return this.hauteur;}    
    public int getLargeur(){return this.largeur;}

    public Case[][] getCarte(){return this.carte;}

    /* Constructeurs */
    public Terrain(String file) {
        try {
            Scanner sc = new Scanner(new FileInputStream(file));
            this.hauteur = sc.nextInt();
            this.largeur = sc.nextInt();
            sc.nextLine();
            this.carte = new Case[hauteur][largeur];
            Teleporteur temp = null;
            for (int y=0; y<hauteur; y++) {
                String line = sc.nextLine();
                for (int x=0; x<largeur; x++) {
                    Case cc;
                    Character ch = line.charAt(x);
                    switch (ch) {
                        case '#': cc = new CaseIntraversable(x, y); break;
                        case ' ': cc = new CaseTraversable(x, y, new Void()); break;
                        case 'S': cc = new Sortie(x, y,new Void()); break;
                        case '@': cc = new CaseTraversable(x,y, new PilierPierre()); break;
                        case 'T': cc = new Teleporteur(x, y); 
                                   if (temp==null){temp = (Teleporteur)cc;} 
                                   else {
                                    ((Teleporteur)cc).setSortie(temp);
                                    temp.setSortie((Teleporteur)cc);
                                    temp = null;
                                   }break;
                        case 'M': case '3': case 'W': case 'E':
                            cc = new CaseTraversable(x, y, new Monstre(5, Direction.ofChar(ch)));
                            break;
                        case '0': cc = new Trou(x, y);break;
                        default:  cc = null; break;
                    }
                    carte[y][x] = cc;
                }
            }
            sc.close();
            if(temp!=null) throw new Error("Le nombre de téléporteurs n'est pas pair !");
        }
        catch (IOException e) { e.printStackTrace(); }
    }
}