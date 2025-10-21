import java.io.Serializable;
import java.util.Random;

/**
 * This class is the computer player with basic hunting AI.
 * It tracks last hit to improve targeting.
 */
public class ComputerPlayer extends Player implements Serializable 
{
    private static final long serialVersionUID = 1L;
    
    private Random random = new Random();
    private int lastHitRow = -1; // Last successful hit row
    private int lastHitCol = -1; // Last successful hit column
    private boolean isHunting = false; // Hunting mode flag
    
    public ComputerPlayer(String name, Board board, String[] shipNames) 
    {
        super(name, board, shipNames);
    }
    
     /**
     * Generates computer's move
     * - Hunts adjacent cells after hits
     * - Random shots otherwise
     */
    public int[] makeMove(Board opponentBoard) 
    {
        // Hunt adjacent cells if in hunting mode.
        if (isHunting && lastHitRow != -1) 
        {
            int[][] directions = {{0,1},{1,0},{0,-1},{-1,0}}; // N,E,S,W
            for (int[] dir : directions) 
            {
                int newRow = lastHitRow + dir[0];
                int newCol = lastHitCol + dir[1];
                if (opponentBoard.isValidShot(newRow, newCol)) 
                {
                    return new int[]{newRow, newCol};
                }
            }
            isHunting = false;  // Exit hunting if no valid adjacent shots.
        }
        // Default to random valid shot.
        int row, col;
        do 
        {
            row = random.nextInt(Board.SIZE);
            col = random.nextInt(Board.SIZE);
        } while (!opponentBoard.isValidShot(row, col));
        
        return new int[]{row, col};
    }
    /** Updates AI state after each shot */
    public void recordShotResult(int row, int col, boolean wasHit) 
    {
        if (wasHit) 
        {
            lastHitRow = row;
            lastHitCol = col;
            isHunting = true; // Enter hunting mode after hit.

        }
    }
}