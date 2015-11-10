import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Created by Philippe on 11/10/2015.
 */
public interface UIInterface {
    void paint(Graphics2D localGraphics2D);
    int run();
    void keyReleased(KeyEvent e);
    void keyPressed(KeyEvent e);
    void keyTyped(KeyEvent e);

    void mousePressed(MouseEvent e);
    void mouseReleased(MouseEvent e);
    void mouseEntered(MouseEvent e);
    void mouseExited(MouseEvent e);
    void mouseClicked(MouseEvent e);
}
