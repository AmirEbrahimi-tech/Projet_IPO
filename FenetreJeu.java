import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.lang.instrument.ClassDefinition;

import javax.swing.*;

public class FenetreJeu extends JPanel implements MouseMotionListener{
    private Jeu jeu;
    private int tailleCase = 32;
    private int hauteur, largeur;
    private JFrame frame;
    private Robot robot;

    public FenetreJeu(Jeu j) {
        jeu = j;
        hauteur = jeu.terrain.getHauteur();
        largeur = jeu.terrain.getLargeur();

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
        if(jeu.rebonditSurBord()){
            jeu.bille.joueSon();
        }
        for(int y = 0;y<hauteur;y++){
            for(int x = 0;x<largeur;x++){
                Case c = jeu.terrain.carte[y][x];
                if (c == null) continue;
                if (c instanceof CaseIntraversable) ((CaseIntraversable)c).affiche(g,this,c);
                else if (c instanceof Trou) ((Trou)c).affiche(g,this,c);
                else if (c instanceof CaseTraversable) ((CaseTraversable)c).affiche(g,this,c);
            }
        }
        jeu.bille.affiche(g,this);
    }

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        Point location = getLocationOnScreen();
        Point center = new Point(location.x + getWidth()/2,location.y + getHeight()/2);
        int dx = e.getXOnScreen() - center.x;
        int dy = e.getYOnScreen() - center.y;
        jeu.bille.getVitesse().setVitesse(jeu.bille.getVitesseX() + dx*0.005, jeu.bille.getVitesseY() + dy*0.005);
        robot.mouseMove(center.x, center.y);
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
