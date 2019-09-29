/**
* An extension of the 2D Board abstract class made for Battleship games.
* This class can run randomized games intended for a board of about 15 by 15 spaces.
*
* @author Brian Tibbetts
*
*/

import java.util.Random;

public class BattleshipBoard extends Board {

   protected final int EMPTYWATER = 0;
   protected final int MISS = 1;
   protected final int SHIP = 2;
   protected final int HIT = 3;
   
   protected int hitCount;
   
   /** Constructor for Battleship boards of at least 5 x 5 size
    *
    * @param x number of rows in the board
    * @param y number of columns in the board
    */
   
   public BattleshipBoard(int x, int y){
      // The minimum board size allowed is 17, the total number of ship spaces generated 
      if (x * y >= 17) {
         board = new int[x][y];
      }
      else{
         board = new int[5][5];
      }
     
      initBoard();
      
   }

   protected boolean checkForWin(){
      
      if (hitCount == 17){    // 17 total ship spaces can be hit
         return true;
      }
      
      return false;
   }
   
   protected void initBoard(){
      hitCount = 0;
      for (int i = 0; i < board.length; i++){      
         for (int j = 0; j < board[i].length; j++){
            board[i][j] = EMPTYWATER;
         }
      }
      
      // Places the largest ship first: 5-length, 4-length, two 3-length, and 2-length
      buildShip(5);
      buildShip(4);
      buildShip(3);
      buildShip(3);
      buildShip(2);
   }
   
   /**
    * Generates a valid location to place a ship on the board, then places one there.
    * 
    * @param size How many spaces the ship occupies
    *  
    */
   protected void buildShip(int size){
      Random rand = new Random(System.currentTimeMillis());

      boolean shipHasRoom = false;
      int alignment = rand.nextInt(2);                      // 0 is horizontal, 1 is vertical
      int startRow = rand.nextInt(board.length);            
      int startCol = rand.nextInt(board[startRow].length);  // generated column depends on generated row
      int emptySpaces = 0;
      
      // This loop finds a suitable space to place the ship by checking for valid spaces on the board
      while(!shipHasRoom){
        
         try {
            // For the horizontal alignment, spaces to the right of the starting coordinates are checked for valid indices
            if (alignment == 0){
            
               for (int i = 0; i < size; i++){      
                  if(getCell(startRow, startCol+i) == EMPTYWATER)
                     emptySpaces++;
               }
               
            }
            // For the vertical alignment, spaces below the starting coordinates are checked for valid indices
            else if (alignment == 1){
            
               for (int i = 0; i < size; i++){      
                  if(getCell(startRow+i, startCol) == EMPTYWATER)
                     emptySpaces++;
               }
               
            }
         }
         
         // Generates new coordinates if there aren't enough valid array indices to place the ship
         catch(ArrayIndexOutOfBoundsException e){
            alignment = rand.nextInt(2);                      // 0 is horizontal, 1 is vertical
            startRow = rand.nextInt(board.length);            
            startCol = rand.nextInt(board[startRow].length);  // generated column depends on generated row
            emptySpaces = 0;
         }
         
         // Once enough empty space to fit the ship has been found, the loop ends
         if(emptySpaces == size){
            shipHasRoom = true;
         }
         // Generates new coordinates if there isn't enough empty water to place the ship
         else{
            alignment = rand.nextInt(2);                      // 0 is horizontal, 1 is vertical
            startRow = rand.nextInt(board.length);            
            startCol = rand.nextInt(board[startRow].length);  // generated column depends on generated row
            emptySpaces = 0;
         }
      }
      
      /* These loops place the ship spaces once all spaces needed to place them are confirmed to be in bounds and not contain ships.
         Spaces are set downward vertically or to the right horizontally. */
      if (alignment == 0){
            
         for (int i = 0; i < size; i++){      
            board[startRow][startCol+i] = SHIP;
         }
               
      }
            
      else if (alignment == 1){
            
         for (int i = 0; i < size; i++){      
            board[startRow+i][startCol] = SHIP;
         }
               
      } 
   }
   
   /** Processes input coordinates by converting empty water spaces to misses and ship spaces to hits.
    *
    * @param i row coordinate on board
    * @param j column coordinate on board
    *
    * @return Returns the integer representing whichever space type was replaced.
    */
   protected int makeMove(int i, int j){
      // Converts user input to array indices
      i--;
      j--;
      
      if (getCell(i, j) == EMPTYWATER){
         board[i][j] = MISS;
         System.out.println("You missed the ships.");
         return EMPTYWATER;
      }
      else if (getCell(i, j) == SHIP){
         board[i][j] = HIT;
         System.out.println("You hit a ship!");
         hitCount++;
         return SHIP;
      }
      else if (getCell(i, j) == HIT){
         System.out.println("You already hit a ship on that space.");
         return HIT;
      }
      else{
         System.out.println("You already missed a ship on that space.");
         return MISS;
      }
   }
   
   /** Converts the game board to a String grid for the user to see during normal gameplay.
    * Integers are used to represent the rows and columns of the board.
    *
    * Board symbols used:
    * ~: untested water
    * X: hit ship
    * 0: missed ship
    *
    * @return A coordinate grid that hides ship locations
    */
   
   public String toString(){
   
      String str = "";
      
      str += "    ";                                    // empty space in the top left corner
      for (int i = 0; i < board[0].length; i++)       // as the first row of the board, adds the column half of the board coordinates to the string
         str += String.format("%-3s", i+1);             // formats each column label into a uniform, 3-space piece
         
      str += "\n";
      
      // Adds the symbol representations of the board's spaces to the string
      for (int i = 0; i < board.length; i++){
         str += String.format("%-3s", i + 1);
               
         for (int j = 0; j < board[i].length; j++){
            if(getCell(i, j) == EMPTYWATER || board[i][j] == SHIP)
               str += " ~ ";
            else if (getCell(i, j) == MISS)
               str += " 0 ";
            else if (getCell(i, j) == HIT)
               str += " X ";
         }
         str += "\n";
      }
      
      return str;
   
   }
   
   /** Converts the game board to a complete String grid for the user to check if they decide to quit.
    *
    * Board symbols used:
    * ~: Empty Water
    * S: Ship
    * X: hit ship
    * 0: missed ship
    *
    * @return A coordinate grid that reveals ship locations
    */
    
   public String showSolution(){
      String str = "";
      
      str += "    ";                                    // empty space in the top left corner
      for (int i = 0; i < board[0].length; i++)       // as the first row of the board, adds the column half of the board coordinates to the string
         str += String.format("%-3s", i+1);             // formats each column label into a uniform, 3-space piece
         
      str += "\n";
      
      for (int i = 0; i < board.length; i++){      
         str += String.format("%-3s", i + 1);
      
         for (int j = 0; j < board[i].length; j++){
            if(getCell(i, j) == EMPTYWATER)
               str += " ~ ";
            else if (getCell(i, j) == SHIP)
               str += " S ";
            else if (getCell(i, j) == MISS)
               str += " 0 ";
            else if (getCell(i, j) == HIT)
               str += " X ";
         }
         str += "\n";
      }
      
      return str;
   
   }
}