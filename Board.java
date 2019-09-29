/**
 * This class defines an abstract Board to be used in games requiring a two-dimensional board.
 * 
 * @author Chris Bennett
 * 
 * 
 */

public abstract class Board {

   protected int [][] board;

	/**
	 * Get the value of a particular (i,j) cell in the board.
	 * 
	 * @param i Value of the column 
	 * @param j Value of the row 
    *
	 */
	protected int getCell(int i, int j) {
		return board[i][j];
	}

	/**
	 * Check to see if the board is in a winning state.
	 * 
	 * @return True if board is in winning state
	 */
	
	protected abstract boolean checkForWin();


	/**
	 * Populate board with appropriate values to begin game.
	 * 
	 */
	protected abstract void initBoard();


}
