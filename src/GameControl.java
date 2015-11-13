import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

/**
 * Created by Philippe on 11/13/2015.
 */
public class GameControl extends UIInterface {

    private UI ui;
    private Game game;

    @Override
    void paint(Graphics2D localGraphics2D) {

        if(game!=null) {
            int[][] matrix = game.getMatrix();

            if (matrix != null) {
                localGraphics2D.setColor(Color.LIGHT_GRAY);
                localGraphics2D.fill(ui.getVisibleRect());

                int squaresize = 20;

                //draw lines
                localGraphics2D.setColor(Color.GRAY);
                for (int i = 0; i <= matrix.length; i++) {
                    localGraphics2D.drawLine(i * squaresize, 0, i * squaresize, matrix[0].length * squaresize);
                }
                for (int i = 0; i <= matrix[0].length; i++) {
                    localGraphics2D.drawLine(0, i * squaresize, matrix.length * squaresize, i * squaresize);
                }

                //draw blocks
                for (int i = 0; i < matrix.length; i++) {
                    for (int j = 0; j < matrix[0].length; j++) {
                        localGraphics2D.setColor(GetColorOfID(matrix[i][j]));
                        localGraphics2D.fill(new Rectangle2D.Double(i * squaresize + 1, j * squaresize + 1, squaresize - 1, squaresize - 1));
                    }
                }
            }
            //draw text
		/*localGraphics2D.setColor(Color.BLACK);
		for (int i = 0; i < text.length; i++)
		{
			localGraphics2D.drawString(text[i], 0, matrix[0].length * squaresize+i*16+16);
		}*/
        }
    }

    private Color GetColorOfID(int i) {
        if(i==0) {return Color.BLUE;}
        else if(i==1) {return Color.ORANGE;}
        else if(i==2) {return Color.CYAN;}
        else if(i==3) {return Color.GREEN;}
        else if(i==4) {return Color.MAGENTA;}
        else if(i==5) {return Color.PINK;}
        else if(i==6) {return Color.RED;}
        else if(i==7) {return Color.YELLOW;}
        else if(i==8) {return new Color(0, 0, 0);}
        else if(i==9) {return new Color(0, 0, 100);}
        else if(i==10) {return new Color(100, 0,0);}
        else if(i==11) {return new Color(0, 100, 0);}
        else {return Color.LIGHT_GRAY;}
    }

    @Override
    int run(UI ui) {
        this.ui = ui;
        game = new Game(13,24,0,500);

        while(!game.gameFinished())
        {
            ui.update();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return 1;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        game.keyPressed(e);
    }
}
