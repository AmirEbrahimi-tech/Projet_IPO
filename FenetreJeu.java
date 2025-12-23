import java.awt.*;
import javax.swing.*;

public class FenetreJeu extends JPanel {
    private Terrain terrain;
    private int tailleCase = 24;
    private int hauteur, largeur;
    private JFrame frame;
    // protected Bille bille;

    public FenetreJeu(Terrain t) {
        this.hauteur = t.getHauteur();
        this.largeur = t.getLargeur();
        this.terrain = t;

        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(largeur * tailleCase, hauteur * tailleCase));

        this.frame = new JFrame("Donjon");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // this.bille = new Bille(new Position(hauteur*tailleCase/2, largeur*tailleCase/2), new Vitesse());
        this.frame.getContentPane().add(this);
        this.frame.pack();
        this.frame.setVisible(true);
    }

    // la méthode pour dessinner les loups
    public void afficheLoup(Graphics g, Case c) {
        Entite ent = ((CaseTraversable) c).getContenu();
        if (!(ent instanceof Monstre)) return;
        g.setColor(Color.RED);
        g.fillOval(c.colonne*tailleCase, c.ligne*tailleCase, tailleCase, tailleCase);
        g.setColor(Color.BLACK);
        switch(((Monstre) ent).direction) {
            case nord -> g.fillOval(c.colonne*tailleCase + tailleCase/2 - 2, c.ligne*tailleCase, 4, 4);
            case sud -> g.fillOval(c.colonne*tailleCase + tailleCase/2 - 2, (c.ligne+1)*tailleCase - 4, 4, 4);
            case est -> g.fillOval((c.colonne+1)*tailleCase - 4, c.ligne*tailleCase + tailleCase/2 - 2, 4, 4);
            case ouest -> g.fillOval(c.colonne*tailleCase, c.ligne*tailleCase + tailleCase/2 - 2, 4, 4);
        }
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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        /* À compléter */
        for (Case[] ligne : this.terrain.carte) {
            for (Case c : ligne) {
                try {
                    if (c instanceof CaseIntraversable) afficheMurs(g, c);
                    else if (c instanceof Sortie) afficheSortie(g, c);
                    else if (c instanceof Teleporteur) afficheTeleporteur(g, c);
                    else if (c instanceof CaseTraversable caseTraversable) {
                        Entite contenu = caseTraversable.getContenu();
                        if (contenu instanceof Monstre) afficheLoup(g, c);
                        else if (contenu instanceof Personnage) afficheMouton(g, c);
                        else if (contenu instanceof Obstacle) afficheObs(g, c);
                    }
                } catch (Exception e) {}
            }
        }
        // g.setColor(new Color(255, 0, 0, 127));
        // g.fillOval((int) bille.pos.x - bille.rayon, (int) bille.pos.y - bille.rayon, bille.rayon*2, bille.rayon*2);
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

    // @Override
    // public void mouseDragged(MouseEvent e) {
    //     System.out.println("ok!?");
    // }

    // @Override
    // public void mouseMoved(MouseEvent e) {
    //     System.out.println("huh?");
    //     this.bille.mouseMoved(e);
    // }
}
