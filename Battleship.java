import java.util.Scanner;

public class Battleship {

   public static void main(String [] args) {
   
      
      Scanner sc = new Scanner(System.in);

      System.out.println("\nWelcome to a game of Battleship.");
      System.out.println("The goal of this game is to sink all 5 hidden ships.");
      System.out.println("Fire at the ships on the board by guessing their coordinates.");
      System.out.println("Once all the ships have sunk, you win.\n");
      
      int dimensions = 15;
      
      do{
         System.out.println("Enter a size for the game board from 5 to 45 (15 is recommended): ");
         dimensions = sc.nextInt();
      } while(dimensions < 5 || dimensions > 45);

      BattleshipBoard gameBoard = new BattleshipBoard(dimensions, dimensions);
      
      int rowInput = 0;
      int colInput = 0;
      int boardSpace = 0;
      boolean choseQuit = false;
      
      while(!gameBoard.checkForWin() && !choseQuit){
         System.out.println(gameBoard.toString());
         
         do{
            System.out.println("Enter a row coordinate, or enter 0 to quit: ");
            rowInput = sc.nextInt();
         } while (rowInput > dimensions || rowInput < 0);
         
         
         do{
            System.out.println("Enter a column coordinate, or enter 0 to quit: ");
            colInput = sc.nextInt();
         } while (colInput > dimensions || colInput < 0);
         
         if(rowInput == 0 || colInput == 0){
            choseQuit = true;
            System.out.println("\nYou have quit the game. Here are the ships' locations:");
         }
         
         else{
            gameBoard.makeMove(rowInput, colInput);
         }
      }
      
      if (gameBoard.checkForWin())
         System.out.println("\nThe last ship sunk. You won!");
         
      System.out.println(gameBoard.showSolution());
   }
}