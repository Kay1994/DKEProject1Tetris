import java.util.ArrayList;
import java.util.Collections;

public class Board {

    private int width;
    private int height;
    private int[][] board;
    final static int invisibleRows = 2;


    /**Creates board with set width and height (with 2 rows more for invisible rows)
     * , then fills some rows on bottom, then fills random elements over the filled rows */
    public Board(int width, int height, int fullRowsFromBottom, int randomElements){

        this.width= width;
        this.height = height;
        this.board = new int[width][height+invisibleRows];
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                board[i][j] = -1;
            }
        }

        for (int i = board[0].length-1; i > board[0].length - 1 - fullRowsFromBottom; i--){
            for (int j = 0; j < board.length; j++){
                board[j][i] = 1;
            }
        }
        int k = 1;
        while(k <= randomElements){
            int x = (int)(Math.random() * width);
            int y = (int)(Math.random() * ((height+invisibleRows)-fullRowsFromBottom));
            if(y>(height/2)){
                board[x][y] = 1;
                k++;
            }
        }

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
     @return ArrayList of indexes (from top) of full rows
     */
    public ArrayList<Integer> fullRowCheck(){

        ArrayList<Integer> fullRows = new ArrayList<Integer>();
        for (int i = 0; i < board[0].length; i++){
            int count = 0;
            for (int j = 0; j < board.length; j++){
                if (board[j][i] != -1){
                    count++;
                    if (count == board.length){
                        fullRows.add(i);
                        count = 0;
                    }
                }
            }
        }

        return fullRows;

    }
    /** Removes full rows and drops lower anything that was over the removed rows.
     * @return how many rows were removed
     */
    public int removeFullRows() {

        ArrayList<Integer> fullRows =fullRowCheck();
        Collections.sort(fullRows);
        int howManyRowsRemoved = fullRows.size();

        System.out.println(fullRows);

        if (fullRows.size() > 0){
            for (int k = 0 ; k < fullRows.size() ; k++){
                for (int i = 0 ; i < board.length ; i++){
                    for(int j=fullRows.get(k);j>0;j--)
                    {
                        board[i][j]=board[i][j-1];
                    }
                }
            }
        }
        fullRows.clear();

        return howManyRowsRemoved;
    }

    /**
     * Checks if the game is lost.
     * @return True if game is lost, false if game is not lost yet.
     */
    public boolean isTheGameLost(){
        for (int i = 0 ; i <  board.length; i++){
                if (board[i][0] != -1){
                    return true;
                }
        }
        return false;
    }

}