import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

/**
 * Created by Philippe on 11/10/2015.
 */
public class Menu extends UIInterface{

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
                GameControl gameControl = new GameControl();
                ui.setUI(gameControl);
                nextStep=gameControl.run(ui);
            }else if(nextStep ==3)
            {
                GameBot gameBot = new GameBot();
                ui.setUI(gameBot);
                nextStep=gameBot.run(ui);
            }
            else if(nextStep ==4)
            {
                Highscore highscore = new Highscore();
                ui.setUI(highscore);
                nextStep=highscore.run(ui);
            }
            else if(nextStep == 5)
            {
                Settings settings = new Settings();
                ui.setUI(settings);
                nextStep=settings.run(ui);
            }else
            {
                //unknow action
                nextStep=0;
            }
        }

        System.exit(0);
    }

    public void paint(Graphics2D localGraphics2D) {
        for(int i=0;i<5;i++) {
            if(selectedMenu==i)
            {
                localGraphics2D.setColor(Color.red);
            }
            else {
                localGraphics2D.setColor(Color.orange);
            }
            localGraphics2D.fill(new Rectangle2D.Double(32, 32+i*96, 256, 64));
        }
        localGraphics2D.setColor(Color.BLACK);
		localGraphics2D.drawString("Play", 32+8, 32+0*96+16);
        localGraphics2D.drawString("Bot", 32+8, 32+1*96+16);
        localGraphics2D.drawString("Highscore", 32+8, 32+2*96+16);
        localGraphics2D.drawString("Settings", 32+8, 32+3*96+16);
        localGraphics2D.drawString("Exit", 32+8, 32+4*96+16);

    }

    public int run(UI ui) {

        this.ui=ui;

        while(!enterPressed)
        {
            //slow down procces
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int returnValue=0;
        if(selectedMenu==0)
        {
            returnValue=2;
        }
        else if(selectedMenu==1)
        {
            returnValue=3;
        }
        else if(selectedMenu==2)
        {
            returnValue=4;
        }
        else if(selectedMenu==3)
        {
            returnValue=5;
        }

        return returnValue;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER)
        {
            enterPressed=true;
        }else if(e.getKeyCode()==KeyEvent.VK_DOWN)
        {
            if(selectedMenu<4){
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
}
