import java.util.ArrayList;
import java.util.Collections;

public class Board {
	
    private int width;
    private int height;
    private int[][] board;
    private int[][] board2;
    
    /*public static void main(String[] args) {
        Board newBoard = new Board(13, 24, 1, 7);
        newBoard.printBoard();
        System.out.println(newBoard.removeFullRows());
        newBoard.printBoard();
    }*/

    /**Creates board with set width and height (with 2 rows more for invisible rows)
     * , then fills some rows on bottom, then fills random elements over the filled rows */
    public Board(int width, int height, int fullRowsFromBottom, int randomElements) {
        this.width = width;
        this.height = height;
        this.board = new int[width][height + 2];
        for (int i = 0; i < board.length; i++)
        {
        	for (int j = 0; j < board[i].length; j++)
        	{
        		board[i][j] = -1;
        	}
        }
        for (int i = board[0].length - 1; i > board[0].length - 1 - fullRowsFromBottom; --i) {
            for (int j = 0; j < board.length; ++j) {
                board[j][i] = 1;
            }
        }
       for(int k = 0; k <= randomElements; k++){
    	   int x = (int)(Math.random() * width);
    	   int y = (int)(Math.random() * (height-fullRowsFromBottom));
    	   board[x][y] = 1;
        }
    }
    
    /**Creates a new board that's a copy of the another board */
    public Board(Board another){
    	this.board = another.board;
    }
    /**Gets board
     *@return array of integers
     */
    public int[][] getBoard() {
        return board;
    }
    /**Gets width
   * @return integer- width of the board
    */
    public int getWidth() {
    	return width;
    }
    /**Gets height
    @return integer- height of the board
    */
    public int getHeight() {
    	return height;
    }
    /**Prints out the board
     * 
     */
    public void printBoard() {
        for (int i = 0; i < board[0].length; ++i) {
            System.out.println("");
            for (int j = 0; j < board.length; ++j) {
                System.out.print(board[j][i]);
            }
        }
    }
    /**Checks which rows are full
     @return ArrayList of numbers (from top) of full rows
     */
    public ArrayList<Integer> fullRowCheck() {
        board = getBoard();
        ArrayList<Integer> fullRows = new ArrayList<Integer>();
        for (int i = 0; i < board[0].length; ++i) {
            int count = 0;
            for (int j = 0; j < board.length; ++j) {
                if (board[j][i] == -1 || ++count != board.length) continue;
                fullRows.add(i);
            }
        }
        return fullRows;
    }
    /** Removes full rows
     * @return how many rows were removed
     */
    public int removeFullRows() {
        board = getBoard();
        this.height = getHeight();
        this.width = getWidth();
        Board replaceableBoard = new Board(width, height, 0, 0);
        board2 = replaceableBoard.getBoard();
        
        
        ArrayList<Integer> fullRows =fullRowCheck();
        Collections.sort(fullRows);
        
        int count = 0;
        if (fullRows.size() > 0) {
            for (int i = 0; i < board[0].length; ++i) {
                for (int j = 0; j < board.length; ++j) {
                    for (int k = 0; k < fullRows.size(); ++k) {
                        if (fullRows.get(k) != i) continue;
                        board[j][i] = 1;
                        ++count;
                    }
                }
            }
            for (int i = 5; i < fullRows.get(0); ++i) {
                for (int j = 0; j < board.length; ++j) {
                	board2[j][i] = board[j][i-fullRows.size()];
                }
            }
        }
        board = board2;
        return count / board.length;
    }
    
}