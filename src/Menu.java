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

    UI ui;
    int selectedMenu=0;
    boolean enterPressed= false;

    public static void main(String[] args) {
        Menu menu = new Menu();
        UI ui = new UI(400,600);
        ui.setUI(menu);

        int nextStep=menu.run(ui);

        while(nextStep>0)
        {
            if(nextStep==1)
            {
                menu = new Menu();
                ui.setUI(menu);
                nextStep = menu.run(ui);
            }else if(nextStep ==2)
            {
                Game game = new Game();
                ui.setUI(game);
                nextStep=game.run(ui);
            }
        }

        System.exit(0);
    }

    @Override
    public void paint(Graphics2D localGraphics2D) {
        for(int i=0;i<3;i++) {
            if(selectedMenu==i)
            {
                localGraphics2D.setColor(Color.red);
            }
            else {
                localGraphics2D.setColor(Color.orange);
            }
            localGraphics2D.fill(new Rectangle2D.Double(32, 32+i*96, 256, 64));
        }
    }

    @Override
    public int run(UI ui) {

        this.ui=ui;

        while(!enterPressed)
        {

        }

        int returnValue=0;
        if(selectedMenu==0)
        {
            returnValue=2;
        }
        if(selectedMenu==1)
        {
            returnValue=0;
        }
        if(selectedMenu==2)
        {
            returnValue=0;
        }

        return returnValue;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER)
        {
            enterPressed=true;
        }else if(e.getKeyCode()==KeyEvent.VK_DOWN)
        {
            if(selectedMenu<2){
                selectedMenu++;
            }
        }else if(e.getKeyCode()==KeyEvent.VK_UP)
        {
            if(selectedMenu>0){
                selectedMenu--;
            }
        }
        ui.update();
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
