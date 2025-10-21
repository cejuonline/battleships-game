import java.io.*;

/**
 * This class is the main game controller.
 * It manages turns, win conditions, and game state.
 */
public class BattleshipsGame implements Serializable 
{
    private static final long serialVersionUID = 1L;
    
    // Game boards
    private Board playerBoard;
    private Board computerBoard;
    
     // Players
    private Player humanPlayer;
    private ComputerPlayer computerPlayer;
    
    // Game state
    private boolean isPlayerTurn;
    private int shotsFired;
    private boolean gameOver;
    
    // Ship names with personalization
    private final String[] PLAYER_SHIP_NAMES = 
    {
        "Apollo Belmont (Battleship)",
        "Peloton Cruiser",
        "Derailleur Destroyer",
        "Peanut Butter Sub"
    };

    
    private final String[] COMPUTER_SHIP_NAMES = 
    {
        "Enemy Flagship",
        "Enemy Cruiser",
        "Enemy Destroyer",
        "Enemy Submarine"
    };
    
    /** Initializes new game. */
    public BattleshipsGame() 
    {
        initializeGame();
    }
    
    /** Sets up fresh game state. */
    private void initializeGame() 
    {
        playerBoard = new Board();
        computerBoard = new Board();
        humanPlayer = new Player("Player", playerBoard, PLAYER_SHIP_NAMES);
        computerPlayer = new ComputerPlayer("Computer", computerBoard, COMPUTER_SHIP_NAMES);
        isPlayerTurn = true;
        shotsFired = 0;
        gameOver = false;
    }
    
    /** Starts new game with random ship placement. */
    public void startNewGame() 
    {
        initializeGame();
        humanPlayer.placeShips();
        computerPlayer.placeShips();
        System.out.println("New game started!");
    }
    
    /**
     * Processes player's shot.
     * @return true if shot was valid.
     */
    public boolean processPlayerShot(int row, int col) 
    {
        if (gameOver || !isPlayerTurn) return false;
        
        if (computerBoard.isValidShot(row, col)) 
        {
            shotsFired++;
            boolean isHit = computerBoard.recordShot(row, col);
            
            if (isHit) 
            {
                Ship hitShip = computerBoard.getShipAt(row, col);
                System.out.println("You hit " + hitShip.getName() + "!");
                
                if (computerBoard.allShipsSunk()) 
                {
                    gameOver = true;
                    System.out.println("You won in " + shotsFired + " shots!");
                }
            } 
            else 
            {
                System.out.println("Miss! Computer's turn...");
                isPlayerTurn = false;
                computerTurn();
            }
            return true;
        }
        return false;
    }
    
    /** Handles computer's turn. */
    private void computerTurn() 
    {
        System.out.println("Computer is thinking...");
        
        // Generate and execute computer's move
        int[] shot = computerPlayer.makeMove(playerBoard);
        boolean isHit = playerBoard.recordShot(shot[0], shot[1]);
        computerPlayer.recordShotResult(shot[0], shot[1], isHit);
        
         // Print results
        System.out.println("Computer shoots at " + (char)('A'+shot[0]) + (shot[1]+1) + 
                         ": " + (isHit ? "HIT!" : "miss"));
        
        if (isHit) 
        {
            Ship hitShip = playerBoard.getShipAt(shot[0], shot[1]);
            System.out.println("It hit your " + hitShip.getName() + "!");
            
            if (playerBoard.allShipsSunk()) 
            {
                gameOver = true;
                System.out.println("Computer wins!");
            }
        }
        
        isPlayerTurn = true; // Return control to player
    }
    
    /** Gets cell state for display. */
    public char getCellState(int row, int col, boolean isPlayerBoard) 
    {
        Board board = isPlayerBoard ? playerBoard : computerBoard;
        return board.getCellState(row, col, isPlayerBoard);
    }
    
    /** Saves game state to file. */
    public boolean saveGame(String filename) 
    {
        try (ObjectOutputStream out = new ObjectOutputStream(
             new FileOutputStream(filename))) 
             {
            out.writeObject(this);
            return true;
        } 
        catch (IOException e) 
        {
            System.err.println("Error saving: " + e.getMessage());
            return false;
        }
    }
    
     /** Loads game state from file. */
    public static BattleshipsGame loadGame(String filename) 
    {
        try (ObjectInputStream in = new ObjectInputStream(
             new FileInputStream(filename))) 
             {
            return (BattleshipsGame) in.readObject();
        } 
        catch (Exception e) 
        {
            System.err.println("Error loading: " + e.getMessage());
            return null;
        }
    }
    
    // Getters
    public boolean isGameOver() 
    { 
        return gameOver; 
    }
    public boolean isPlayerTurn() 
    { 
        return isPlayerTurn; 
    }
    public int getShotsFired() 
    { 
        return shotsFired; 
    }
    public String getWinner() 
    {
        if (playerBoard.allShipsSunk()) return "Computer";
        if (computerBoard.allShipsSunk()) return "Player";
        return null;
    }
}