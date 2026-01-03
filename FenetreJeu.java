import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class FenetreJeu extends JPanel implements MouseMotionListener{
    private Grille grille;
    private int tailleCase = 32;
    private int hauteur, largeur;
    private JFrame frame;
    private Robot robot;

    public FenetreJeu(Grille g) {
        grille = g;
        hauteur = grille.terrain.getHauteur();
        largeur = grille.terrain.getLargeur();

        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(largeur * tailleCase, hauteur * tailleCase));
        frame = new JFrame("Fenetre jeu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
        addMouseMotionListener(this);

        try{robot = new Robot();}
        catch(Exception e){System.err.print(e);}

        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
        frame.setCursor(blankCursor);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        grille.repaint();
        if(grille.rebonditSurBord()){
            grille.bille.joueSon();
        }
        for(int y = 0;y<hauteur;y++){
            for(int x = 0;x<largeur;x++){
                Case c = grille.terrain.carte[y][x];
                if (c == null) continue;
                if (c instanceof CaseIntraversable) ((CaseIntraversable)c).affiche(g,grille,c);
                else if (c instanceof Trou) ((Trou)c).affiche(g,grille,c);
                else if (c instanceof CaseTraversable){
                    if (((Case)c).estVide()) ((CaseTraversable)c).affiche(g,grille,c);
                }
                // else if (grille.contientBille(ligne, colonne)) {
                //     g.setColor(new Color(20, 230, 170)); // just random numbers :)
                //     g.fillRect(colonne * tailleCase, ligne * tailleCase, tailleCase, tailleCase);
                // }
            }
        }
        grille.bille.affiche(g,grille);
    }

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        Point location = getLocationOnScreen();
        Point center = new Point(location.x + getWidth()/2,location.y + getHeight()/2);
        int dx = e.getXOnScreen() - center.x;
        int dy = e.getYOnScreen() - center.y;
        grille.bille.vit.setVitesse(grille.bille.vit.x + dx*0.005, grille.bille.vit.y + dy*0.005);
        robot.mouseMove(center.x, center.y);
    }


    // la méthode pour dessinner les obstacles
    // public void afficheObs(Graphics g, Case c) {
    //     Entite ent = ((CaseTraversable) c).getContenu();
    //     if (!(ent instanceof Obstacle)) return;
    //     switch(ent.resistance) {
    //         case 3 -> g.setColor(Color.BLACK);
    //         case 2 -> g.setColor(new Color(200,200,200));
    //         case 1 -> g.setColor(new Color(120,120,120));
    //     }
    //     g.fillOval(c.colonne*tailleCase, c.ligne*tailleCase, tailleCase, tailleCase);
    // }

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
