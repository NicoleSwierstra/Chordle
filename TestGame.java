import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import GUI.*;

public class TestGame {

    public class GraphicPanel extends JComponent implements Runnable {
        private static final long serialVersionUID = 1L;

        public GUI gui = new GUI();
        TestGUI tg = new TestGUI();

        float mousex, mousey;

        int panewidth = 480, paneheight = 720;

        GraphicPanel() {
            setFocusTraversalKeysEnabled(false);
            setFocusable(true);
            setPreferredSize(new Dimension(panewidth, paneheight));

            JComponent pane = this;
            pane.addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent componentEvent) {
                    paneheight = pane.getHeight();
                    panewidth = pane.getWidth();
                }
            });
            setFocusTraversalKeysEnabled(false);
            setFocusable(true);

            tg.setUpGUI(gui);
        }

        @Override
        public void paintComponent(Graphics g) {
            Point p = getMousePosition();
            if(p != null) {
                mousex = (float)p.getX()/panewidth; 
                mousey = (float)p.getY()/paneheight;
            }

            g.setColor(Color.BLACK);
            g.fillRect(0, 0, panewidth, paneheight);
            gui.render(g, panewidth, paneheight, mousex, mousey);

            tg.Render(g, this);
        }

        public void run() {
            long end;
            while(true) {
                end = System.nanoTime() + 16666666;
                this.repaint();
                while(System.nanoTime() < end);
            }
        }

        public int toIntH(float positionFloat){
            return (int)(positionFloat * panewidth);
        }

        public int toIntV(float positionFloat){
            return (int)(positionFloat * paneheight);
        }
    }

    JFrame mainwindow;
    GraphicPanel g;

    public TestGame(){
        mainwindow = new JFrame("Chordle");
        g = new GraphicPanel();
        mainwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainwindow.add(g);
        mainwindow.pack();
        mainwindow.setVisible(true);

        g.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                g.gui.onMouse(g.mousex, g.mousey);
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });

        start();
    }

    void start(){
        g.run();
    }
}