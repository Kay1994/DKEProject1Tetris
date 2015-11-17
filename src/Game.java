import java.io.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Point;
import java.lang.Math;
import java.awt.event.*;

public class Game
{
	private int score;
	private Pentomino activePentomino;
	private Pentomino storagedPentomino;
	private Pentomino nextPentomino1;
	private Point pentominoLocation;
	private Board gameBoard;
    private int[][] matrix=null;

	Game(int width, int height, int randomBlocks, int tick){
		//Setup for the board, UI and the first pentomino.
		gameBoard = new Board(width,height,3,randomBlocks);
		pentominoLocation = new Point(width/2, 0);
		activePentomino = new Pentomino((int)(Math.random()*12));
		nextPentomino1 = new Pentomino((int)(Math.random()*12));

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
					pentominoLocation = new Point(gameBoard.getWidth()/2, 0);
					activePentomino = nextPentomino1;
					nextPentomino1 = new Pentomino((int)(Math.random()*12));
				}
				//If the next drop is legal we move the piece and make a tmpBoard, which contains the changes.
				//This way the original board remains unchanged and we can change the UI without changing the gameBoard.
				if (nextDropLegal(activePentomino, gameBoard.getBoard()))
				{
					//Move pentomino
					pentominoLocation.setLocation(pentominoLocation.getX(), pentominoLocation.getY() + 1);
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
						if (newY >= 0)
							tmpBoard[newX][newY] = activePentomino.getID();
					}

					matrix = tmpBoard;
				}
				//If not, lock the piece to gameBoard, erase the full rows and add score accordingly.
				else
				{
					for (Point p : activePentomino.getLocation())
					{
						int newX = (int)(pentominoLocation.getX() + p.getX());
						int newY = (int)(pentominoLocation.getY() + p.getY());
						if (newY >= 0 && newY < gameBoard.getBoard()[0].length)
							gameBoard.getBoard()[newX][newY] = activePentomino.getID();
					}
					/*This part also needs to update the score display*/
					//Something weird happens with this command:
					addScore(gameBoard.removeFullRows());
					/*Something to do with the Board class maybe?*/
					String[] scoreText = {Integer.toString(score)};
					matrix=gameBoard.getBoard();
					activePentomino = null;
				}
			}
		}
		/*END-OF-BLOCKFALL-CLASS*/
		//Setup for the method to make the pentomino fall and the timer which controls it. I.E. this makes the game run.
		BlockFall blockFall = new BlockFall();
		int tickLength = tick;
		Timer t = new Timer(tickLength, blockFall);
		t.start();
	}

	public void keyPressed(KeyEvent e) {
		int direction = 0;
		if (e.getKeyCode() == KeyEvent.VK_LEFT) direction = -1;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) direction = 1;
		//+1 right, -1 left
		boolean legalMove = true;
		for (Point p : activePentomino.getLocation())
		{
			int newX = (int)(p.getX() + pentominoLocation.getX() + direction);
			int newY = (int)(p.getY() + pentominoLocation.getY());
			if (newX < 0 || newX >= gameBoard.getBoard().length) legalMove = false;
		}
		if (legalMove) pentominoLocation.setLocation((pentominoLocation.getX() + direction), pentominoLocation.getY());

		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			activePentomino.rotate();
		}
	}

	public int[][] getMatrix()
	{
		return matrix;
	}

	public void sideMove(int direction) {
		//+1 right, -1 left
		boolean legalMove = true;
		for (Point p : activePentomino.getLocation())
		{
			int newX = (int)(p.getX() + pentominoLocation.getX() + direction);
			int newY = (int)(p.getY() + pentominoLocation.getY());
			if (newX < 0 || newX >= gameBoard.getBoard().length) legalMove = false;
		}
		if (legalMove) pentominoLocation.setLocation((pentominoLocation.getX() + direction), pentominoLocation.getY());
	}

	public void downMove() {
		//Drop the pentomino
	}

	public void transform(int mutation) {
		//+1 rotate, -1 mirror
	}

	/** Checks if the falling pentomino can fall further.
	 * @param pentomino The pentomino piece which is currently falling.
	 * @param board The board in which the pentomino currently is.
	 */
	public boolean nextDropLegal(Pentomino pentomino, int[][] board) {
		boolean legalMove = true;
		//Self-explanatory. Just checks the next block to 'fall' to for all blocks of the pentomino
		for (Point p : pentomino.getLocation())
		{
			int newX = (int)(p.getX() + pentominoLocation.getX());
			int newY = (int)(p.getY() + pentominoLocation.getY()+1);
			if (newY >= 0 &&(newY >= board[0].length || board[newX][newY] != -1)) legalMove = false;
		}
		return legalMove;
	}

	public boolean gameFinished(){return !gameBoard.isTheGameLost();}

	/** Checks if the score is high enough to get to the high scores list, adds the name and score and organizes the list.
	 *  If HighScores.dat is not found, the method generates a blank one.
	 * @param name The nickname of the person getting to the list.
	 * @param score The score gained.
	 */
	public static void addHighScore(String name, int score) {

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
	public static Object[][] getHighScore() {
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
	public void addScore(int numberOfRows)
	{
		score += numberOfRows*numberOfRows*100;
	}

}