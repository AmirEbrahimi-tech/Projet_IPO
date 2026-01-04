import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;


public class Terrain {
    /* Attributs */
    private int hauteur, largeur;
    private Case[][] carte;

    /* Initialisation d'un terrain à  partir de la description donnée par
       un fichier texte. Format du fichier de description :
       - hauteur et largeur respectivement sur la première et la deuxième ligne
       - puis dessin du terrain (un caractère == une case) avec le codage
         suivant
         '#' pour un mur
         ' ' (espace) pour une case traversable de type Dalle
         '_' pour une case traversable de type Herbe
         'T' pour une case traversable de type Trou
         'S' pour une sortie
         '@' pour une case traversable contenant un obstacle
         'W'/'M'/'E'/'3' pour une case traversable contenant un monstre
         'e' pour une case traversable contenant la bille (point d'apparition)
    */

    /* Constructeurs */
    public Terrain(String file) {
        try {
            Scanner sc = new Scanner(new FileInputStream(file));
            this.hauteur = sc.nextInt();
            this.largeur = sc.nextInt();
            sc.nextLine();
            this.carte = new Case[hauteur][largeur];
            for (int y=0; y<hauteur; y++) {
                String line = sc.nextLine();
                for (int x=0; x<largeur; x++) {
                    Case cc;
                    Character ch = line.charAt(x);
                    switch (ch) {
                        case '#': cc = new CaseIntraversable(x, y); break;      
                        case 'e': cc = new CaseEntree(x, y, new Void()); break; 
                        case ' ': cc = new CaseDalle(x, y, new Void()); break;  
                        case '_': cc = new CaseHerbe(x, y, new Void()); break;
                        case 'S': cc = new Sortie(x, y,new Void()); break;
                        case '@': cc = new CaseDalle(x,y, new PilierPierre()); break;
                        case 'W' : case 'M' : case 'E' : case '3' :
                            cc = new CaseDalle(x, y, new Monstre(5, Direction.ofChar(ch)));
                            break;
                        case 'T': cc = new Trou(x, y);break;
                        default:  cc = null; break;
                    }
                    carte[y][x] = cc;
                }
            }
            sc.close();
        }
        catch (IOException e) { e.printStackTrace(); }
    }
    
    /* Getters */
    public int getHauteur(){return this.hauteur;}    
    public int getLargeur(){return this.largeur;}
    public Case[][] getCarte(){return this.carte;}
}