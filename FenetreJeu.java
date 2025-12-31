import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class FenetreJeu extends JPanel {
    private Grille grille;
    private int tailleCase = 32;
    private int hauteur, largeur;
    private JFrame frame;
    private Robot robot;

    public FenetreJeu(Grille grille) {
        this.hauteur = grille.terrain.getHauteur();
        this.largeur = grille.terrain.getLargeur();
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(largeur * tailleCase, hauteur * tailleCase));
        frame = new JFrame("Fenetre jeu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
        try{robot = new Robot();}
        catch(Exception e){System.err.print(e);}
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
        frame.setCursor(blankCursor);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(grille.rebonditSurBord()){
            grille.bille.joueSon();
        }
        for(int ligne = 0;ligne<grille.terrain.carte.length;ligne++){
            for(int colonne = 0;colonne<grille.terrain.carte[0].length;colonne++){
                Case c = grille.terrain.carte[ligne][colonne];
                if (c == null) continue;
                if (c instanceof CaseIntraversable) afficheMurs(g, c);
                else if (c instanceof Trou){((Trou)c).affiche(g,grille,ligne*tailleCase,colonne*tailleCase);}
                else if (c instanceof CaseTraversable){
                    if (((Case)c).estVide()) ((CaseTraversable)c).affiche(g,grille,ligne*tailleCase,colonne*tailleCase);
                }
                else if (grille.contientBille(ligne, colonne)) {
                    g.setColor(new Color(20, 230, 170)); // just random numbers :)
                    g.fillRect(colonne * tailleCase, ligne * tailleCase, tailleCase, tailleCase);
                }
            }
        }
        grille.bille.affiche(g,grille,(int) grille.bille.pos.x - grille.bille.rayon, (int) grille.bille.pos.y - grille.bille.rayon);
    }

    // la méthode pour dessinner les moutons
    public void afficheMouton(Graphics g, Case c) {
        Entite ent = ((CaseTraversable) c).getContenu();
        if (!(ent instanceof Personnage)) return;
        g.setColor(Color.GREEN);
        g.fillOval(c.colonne*tailleCase, c.ligne*tailleCase, tailleCase, tailleCase);
        g.setColor(Color.BLACK);
        switch(((Personnage) ent).direction) {
            case nord -> g.fillOval(c.colonne*tailleCase + tailleCase/2 - 2, c.ligne*tailleCase, 4, 4);
            case sud -> g.fillOval(c.colonne*tailleCase + tailleCase/2 - 2, (c.ligne+1)*tailleCase - 4, 4, 4);
            case est -> g.fillOval((c.colonne+1)*tailleCase - 4, c.ligne*tailleCase + tailleCase/2 - 2, 4, 4);
            case ouest -> g.fillOval(c.colonne*tailleCase, c.ligne*tailleCase + tailleCase/2 - 2, 4, 4);
        }
    }

    // la méthode pour dessinner les obstacles
    public void afficheObs(Graphics g, Case c) {
        Entite ent = ((CaseTraversable) c).getContenu();
        if (!(ent instanceof Obstacle)) return;
        switch(ent.resistance) {
            case 3 -> g.setColor(Color.BLACK);
            case 2 -> g.setColor(new Color(200,200,200));
            case 1 -> g.setColor(new Color(120,120,120));
        }
        g.fillOval(c.colonne*tailleCase, c.ligne*tailleCase, tailleCase, tailleCase);
    }

    // la méthode pour dessinner les murs
    public void afficheMurs(Graphics g, Case c) {
        g.setColor(Color.BLACK);
        g.fillRect(c.colonne*tailleCase, c.ligne*tailleCase, tailleCase, tailleCase);
    }

    // la méthode pour dessinner la sortie
    public void afficheSortie(Graphics g, Case c) {
        g.setColor(Color.BLUE);
        g.fillOval(c.colonne*tailleCase - 1, c.ligne*tailleCase - 1, tailleCase+2, tailleCase+2);
    }

    public void afficheTeleporteur(Graphics g, Case c){
        g.setColor(new Color(255,0,255));
        g.fillOval(c.colonne*tailleCase - 1, c.ligne*tailleCase - 1, tailleCase+2, tailleCase+2);
    }

    public void ecranFinal(int n) {
        frame.remove(this);
        JLabel label = new JLabel("Score " + n);
        label.setFont(new Font("Verdana", 1, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setSize(this.getSize());
        frame.getContentPane().add(label);
        frame.repaint();
    }
}
