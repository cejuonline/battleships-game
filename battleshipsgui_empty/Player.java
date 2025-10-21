import java.io.Serializable;
import java.util.Random;

/**
 * This is the base class for human and computer players.
 * It handles common ship placement logic.
 */
public class Player implements Serializable 
{
    private static final long serialVersionUID = 1L;
    
    protected String name;
    protected Board board;
    protected String[] shipNames;
    
    /**
     * This creates a new player.
     * @param name Player name.
     * @param board Associated game board.
     * @param shipNames Names for ship types.
     */
    public Player(String name, Board board, String[] shipNames) 
    {
        this.name = name;
        this.board = board;
        this.shipNames = shipNames;
    }
    
    /** Places all ships randomly on the board. */
    public void placeShips() 
    {
        placeShip(Ship.Type.BATTLESHIP, shipNames[0]); // 1x Battleship
        placeShip(Ship.Type.CRUISER, shipNames[1]);    // 2x Cruisers
        placeShip(Ship.Type.CRUISER, shipNames[1]);
        placeShip(Ship.Type.DESTROYER, shipNames[2]);  // 3x Destroyers
        placeShip(Ship.Type.DESTROYER, shipNames[2]);
        placeShip(Ship.Type.DESTROYER, shipNames[2]);
        placeShip(Ship.Type.SUBMARINE, shipNames[3]);   // 3x Submarines
        placeShip(Ship.Type.SUBMARINE, shipNames[3]);
        placeShip(Ship.Type.SUBMARINE, shipNames[3]);
    }
    
    /** Attempts to place a single ship with random positioning. */
    private void placeShip(Ship.Type type, String name) 
    {
        Random rand = new Random();
        boolean placed = false;
        int attempts = 0;
        
        // Try random positions until successful or max attempts.
        while (!placed && attempts < 100) 
        {
            int row = rand.nextInt(Board.SIZE);
            int col = rand.nextInt(Board.SIZE);
            placed = board.placeShip(row, col, type, name);
            attempts++;
        }
    }
    
    // Getters.
    public String getName() 
    { 
        return name; 
    }
    public Board getBoard() 
    { 
        return board; 
    }
}