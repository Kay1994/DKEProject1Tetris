import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

/**
 * Created by Philippe on 11/10/2015.
 */
public class Menu implements UIInterface{

    int selectedMenu=0;
    boolean enterPressed= false;

    public static void main(String[] args) {
        Menu menu = new Menu();
        UI ui = new UI(320,500);
        ui.setUI(menu);

        int nextStep=menu.run();

        while(nextStep>0)
        {
            if(nextStep==1)
            {
                menu = new Menu();
                ui.setUI(menu);
                nextStep = menu.run();
            }else if(nextStep ==2)
            {
                //do something
            }
        }

        System.exit(0);
    }

    @Override
    public void paint(Graphics2D localGraphics2D) {
        localGraphics2D.setColor(Color.orange);
        localGraphics2D.fill(new Rectangle2D.Double(32,32,256,64));
        localGraphics2D.fill(new Rectangle2D.Double(32,128,256,64));
        localGraphics2D.fill(new Rectangle2D.Double(32,256-32,256,64));
    }

    @Override
    public int run() {

        while(!enterPressed)
        {

        }

        return 0;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER)
        {
            enterPressed=true;
        }else if(e.getKeyCode()==KeyEvent.VK_DOWN)
        {
            if(selectedMenu<3){
                selectedMenu++;
            }
        }else if(e.getKeyCode()==KeyEvent.VK_UP)
        {
            if(selectedMenu>0){
                selectedMenu--;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
}
