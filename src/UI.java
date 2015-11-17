import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

class UI extends JPanel {

    private JFrame window;

    UIInterface uiInterface;

    public UI(int x, int y) {
        //make new frame
        window = new JFrame("Pentomino");
        setPreferredSize(new Dimension(x,y));

        //set some parameters
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        //place in the center of the screen
        Dimension localDimension = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation((localDimension.width - x) / 2, (localDimension.height - y) / 2);

        //add the PaintComponent to the window
        window.add(this);

        //fit size
        window.pack();

        //make visible
        window.setVisible(true);

        uiInterface=null;

        window.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                if(uiInterface!=null)
                    uiInterface.keyTyped(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(uiInterface!=null)
                    uiInterface.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(uiInterface!=null)
                    uiInterface.keyReleased(e);
            }
        });

        window.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(uiInterface!=null)
                    uiInterface.mouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(uiInterface!=null)
                    uiInterface.mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(uiInterface!=null)
                    uiInterface.mouseReleased(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if(uiInterface!=null)
                    uiInterface.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(uiInterface!=null)
                    uiInterface.mouseExited(e);
            }
        });
    }

    protected void paintComponent(Graphics g)
    {
        Graphics2D localGraphics2D = (Graphics2D)g;

        localGraphics2D.setColor(Color.LIGHT_GRAY);
        localGraphics2D.fill(getVisibleRect());

        if(uiInterface!=null)
            uiInterface.paint(localGraphics2D);
    }

    public void setUI(UIInterface ui)
    {
        uiInterface=ui;
    }

    public void update()
    {
        repaint();
    }
}