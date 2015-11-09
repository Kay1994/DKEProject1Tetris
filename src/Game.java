import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Point;
import java.lang.Math;
import java.awt.event.*;

public class Game
{
	
	private static int score;
	private static Pentomino activePentomino;
	private static Point pentominoLocation;
	/** General testing class
	* @param args not used
	*/
	public static void main(String[] args)
	{
		newGame(13,24,0);
	}
	
	public static class PentominoUI extends JPanel{

        private JFrame window;
        private int[][] matrix;
        private int squaresize;
        private String[] text;
        
        /*START-OF-UI-CLASS*/
        /**
         * make a new window for displaying pentominos
         * @param x amount of blocks horizontaly
         * @param y amount of blocks verticaly
         * @param size size of blocks
         * @param debugSpace size of reserved space for debug info
         */
        public PentominoUI(int x, int y, int size, int debugSpace)
        {
            //store squaresize
            squaresize=size;

            //make new frame
            window = new JFrame("Pentomino");
            setPreferredSize(new Dimension(x*squaresize,y*squaresize+debugSpace));

            //set some parameters
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);

            //place in the center of the screen
            Dimension localDimension = Toolkit.getDefaultToolkit().getScreenSize();
            window.setLocation((localDimension.width - x*squaresize) / 2, (localDimension.height - y*squaresize) / 2);

            //add the PaintComponent to the window
            window.add(this);

            //fit size
            window.pack();

            //make visible
            window.setVisible(true);

            //make matrix
            matrix = new int[x][y];

            //fill matrix with -1 (= empty)
            for(int i=0;i<x;i++)
            {
                for(int j=0;j<y;j++)
                {
                    matrix[i][j]=-1;
                }
            }

            //make start text;
            text=new String[1];
            text[0]="No debug info availeble yet...";
        }

        /**
         * paint the window
         * @param g graphical adapter
         */
        protected void paintComponent(Graphics g)
        {
            //cast grapics to a 2D graphics
            Graphics2D localGraphics2D = (Graphics2D)g;

            //clear everything
            localGraphics2D.setColor(Color.LIGHT_GRAY);
            localGraphics2D.fill(getVisibleRect());

            //draw lines
            localGraphics2D.setColor(Color.GRAY);
            for (int i = 0; i <= matrix.length; i++)
            {
                localGraphics2D.drawLine(i * squaresize, 0, i * squaresize, matrix[0].length * squaresize);
            }
            for (int i = 0; i <= matrix[0].length; i++)
            {
                localGraphics2D.drawLine(0, i * squaresize, matrix.length * squaresize, i * squaresize);
            }

            //draw blocks
            for (int i = 0; i < matrix.length; i++)
            {
                for (int j = 0; j < matrix[0].length; j++)
                {
                    localGraphics2D.setColor(GetColorOfID(matrix[i][j]));
                    localGraphics2D.fill(new Rectangle2D.Double(i * squaresize + 1, j * squaresize + 1, squaresize - 1, squaresize - 1));
                }
            }

            //draw text
            localGraphics2D.setColor(Color.BLACK);
            for (int i = 0; i < text.length; i++)
            {
                localGraphics2D.drawString(text[i], 0, matrix[0].length * squaresize+i*16+16);
            }
        }

        /**
         * update the data to display
         * @param data 2dim array of data of the matrix
         */
        public void update(int data[][]) {

            update(data,text);
        }

        /**
         * update the data to display
         * @param text array of text to display under the matrix
         */
        public void update(String text[]) {

            update(matrix,text);
        }

        /**
         * update the data to display
         * @param data 2dim array of data of the matrix
         * @param text array of text to display under the matrix
         */
        public void update(int data[][], String[] text)
        {
            //check if data and matix have the same dimentions, with assumtion that the length of the second dimention is the same
            if(data.length==matrix.length && data[0].length == matrix[0].length+2)
            {
                //make a copy of Data in matrix
                for (int i = 0; i < matrix.length; i++) {
                    for (int j = 0; j < matrix[0].length; j++) {
                        matrix[i][j] = data[i][j+2];
                    }
                }
            }
            
            this.text=text;

            //repaint
            repaint();
        }

        /**
         * return the color of a certain block
         * @param i the block id
         * @return the color
         */
        private Color GetColorOfID(int i)
        {
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
    }
    /*END-OF-UI-CLASS*/
	
	/** Initializes the game window, board, pentomino and timers.
	* @param width The width of the game board and window.
	* @param height The height of the game board and window.
	* @param randomBlocks The number of random blocks generated at the start of the game.
	*/
	public static void newGame(int width, int height, int randomBlocks)
	{
		//Setup for the board, UI and the first pentomino.
		Board gameBoard = new Board(width,height,0,randomBlocks);
		PentominoUI UI = new PentominoUI(width,height,20,100);
		UI.update(gameBoard.getBoard());
		pentominoLocation = new Point(width/2, 2);
		activePentomino = new Pentomino((int)(Math.random()*12));
		
		/** Makes the active pentomino fall one step each time it's called.
		*
		*/
		/*START-OF-BLOCKFALL-CLASS*/
		class BlockFall implements ActionListener
		{
			/** Executes the fall.
			*/
			public void actionPerformed(ActionEvent e)
			{
				if (activePentomino == null)
				{
					pentominoLocation = new Point(gameBoard.getWidth()/2, 2);
					activePentomino = new Pentomino((int)(Math.random()*12));
				}
				//If the next drop is legal we move the piece and make a tmpBoard, which contains the changes.
				//This way the original board remains unchanged and we can change the UI without changing the gameBoard.
				if (nextDropLegal(activePentomino, gameBoard.getBoard()))
				{
					//Move pentomino
					pentominoLocation.setLocation(pentominoLocation.getX(), pentominoLocation.getY()+1);
					int[][] tmpBoard = new int[gameBoard.getBoard().length][gameBoard.getBoard()[0].length];
					//Copy the old board to the tmp
					for (int i = 0; i < gameBoard.getBoard().length; i++)
					{
						for (int j = 0; j < gameBoard.getBoard()[i].length; j++)
						{
							tmpBoard[i][j] = gameBoard.getBoard()[i][j];
						}
					}
					//Add the pentomino to the tmpBoard
					for (Point p : activePentomino.getLocation())
					{
						int newX = (int)(pentominoLocation.getX() + p.getX());
						int newY = (int)(pentominoLocation.getY() + p.getY());
						tmpBoard[newX][newY] = activePentomino.getID();
					}
					
					UI.update(tmpBoard);
				}
				//If not, lock the piece to gameBoard, erase the full rows and add score accordingly.
				else
				{
					for (Point p : activePentomino.getLocation())
						{
							int newX = (int)(pentominoLocation.getX() + p.getX());
							int newY = (int)(pentominoLocation.getY() + p.getY());
							gameBoard.getBoard()[newX][newY] = activePentomino.getID();
						}
					/*This part also needs to update the score display*/
					//Something weird happens with this command:
					//addScore(gameBoard.removeFullRows());
					/*Something to do with the Board class maybe?*/
					String[] scoreText = {Integer.toString(score)};
					UI.update(gameBoard.getBoard(),scoreText);
					activePentomino = null;
				}
			}
		}
		/*END-OF-BLOCKFALL-CLASS*/
		//Setup for the method to make the pentomino fall and the timer which controls it. I.E. this makes the game run.
		BlockFall blockFall = new BlockFall();
		int tickLength = 500;
		Timer t = new Timer(tickLength, blockFall);
		t.start();
	}
		
	
	/** Checks if the falling pentomino can fall further.
	* @param pentomino The pentomino piece which is currently falling.
	* @param board The board in which the pentomino currently is.
	*/
	public static boolean nextDropLegal(Pentomino pentomino, int[][] board)
	{
		boolean legalMove = true;
		//Self-explanatory. Just checks the next block to 'fall' to for all blocks of the pentomino
		for (Point p : pentomino.getLocation())
		{
			int newX = (int)(p.getX() + pentominoLocation.getX());
			int newY = (int)(p.getY() + pentominoLocation.getY()+1);
			if (newY >= board[0].length || board[newX][newY] != -1) legalMove = false;
		}
		return legalMove;
	}
	
	/** Checks if the score is high enough to get to the high scores list, adds the name and score and organizes the list.
	*  If HighScores.dat is not found, the method generates a blank one.
	* @param name The nickname of the person getting to the list.
	* @param score The score gained.
	*/
	public static void addHighScore(String name, int score)
	{
		
		//If we don't yet have a high scores table, we create a blank (and let the user know about it)
		if (!new File("HighScores.dat").exists())
		{
			System.out.println("HighScores.dat not found. Creating a blank.");
			//This object matrix actually stores the information of the high scores list
			Object[][] highScores = new Object[10][3];
			//We fill the high scores list with blank entries:  #.    " "    0
			for (int i = 0; i < highScores.length; i++)
			{
				highScores[i][0] = (i+1) + ".";
				highScores[i][1] = " ";
				highScores[i][2] = 0;
			}
			//This actually writes and makes the high scores file
			try 
			{
				ObjectOutputStream o=new ObjectOutputStream(new FileOutputStream("HighScores.dat"));
				o.writeObject(highScores);
				o.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		//We read the file to check if we have a new high score, and then rewrite the highscore
		//This is done even if we didn't previously have a high scores list
		try 
		{
			ObjectInputStream o=new ObjectInputStream(new FileInputStream("HighScores.dat"));
			//The object matrix does the same as the previous one.
			//Here we just take what we read from the HighScores.dat to the Object[][] HighScores.
			Object[][] highScores = (Object[][]) o.readObject();
			//Then we start searching for an entry for which the score is smaller than the achieved score
			for (int i = 0; i < highScores.length; i++)
			{
				if ((int)highScores[i][2] < score)
				{
					//Once found we start to move entries, which are below the score we had, downwards.
					//I.e. 10. becomes whatever 9. was. 9. becomes what 8. was etc...
					for (int j = 9; j > i; j--)
					{
						highScores[j][0] = (j+1) + ".";
						highScores[j][1] = highScores[j-1][1];
						highScores[j][2] = highScores[j-1][2];
					}
					//Then we write the score and the name we just got to the correct place
					highScores[i][0] = (i+1) + ".";
					highScores[i][1] = name;
					highScores[i][2] = score;
					//And break the loop.
					/*Maybe this could be avoided somehow? I haven't been able to come up with an easy way yet.*/
					break;
				}
			}
			try
			{
				//And finally we overwrite the HighScores.dat with our highScores object matrix
				ObjectOutputStream n = new ObjectOutputStream(new FileOutputStream("HighScores.dat"));
				n.writeObject(highScores);
				n.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/** A getter for the high scores list. Reads it directly from file and throws an error if the file is not found (!working on this!).
	* @return Object[][] where [i][0] is the rank (String), [i][1] is the name (String) and [i][2] is the score (Integer).
	*/
	public static Object[][] getHighScore()
	{
		try
		{
			//Read and return the read object matrix
			ObjectInputStream o = new ObjectInputStream(new FileInputStream("HighScores.dat"));
			Object[][] highScores = (Object[][]) o.readObject();
			o.close();
			return highScores;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/** Increases the current score based on how many rows were cleared. The formula is numberOfRows squared times 100.
	* @param numberOfRows The number of rows cleared in one instance.
	*/
	public static void addScore(int numberOfRows)
	{
		score += numberOfRows*numberOfRows*100;
	}
	
}