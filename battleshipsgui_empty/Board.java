import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class manages the game grid and ship positions.
 * It handles shot recording.
 * It tracks game state.
 */
public class Board implements Serializable 
{
    private static final long serialVersionUID = 1L;
    public static final int SIZE = 10; // 10x10 grid.
    
    private char[][] grid; // Game grid state.
    private ArrayList<Ship> ships; // List of ships.
    
     /** Initializes empty board. */
    public Board() 
    {
        grid = new char[SIZE][SIZE];
        ships = new ArrayList<>();
        initializeGrid();
    }
    
    /** Fills grid with water tiles. */
    private void initializeGrid() 
    {
        for (int row = 0; row < SIZE; row++) 
        {
            for (int col = 0; col < SIZE; col++) 
            {
                grid[row][col] = '~'; // '~' represents water.
            }
        }
    }
    
    /**
     * Places a ship on the board.
     * @param row Starting row.
     * @param col Starting column.
     * @param type Ship type.
     * @param name Ship name.
     * @return true if placement succeeded.
     */
    public boolean placeShip(int row, int col, Ship.Type type, String name) 
    {
        int size = type.size;
        boolean vertical = new Random().nextBoolean(); // Random orientation.
        
         // Validate placement bounds.
        if (vertical && row + size > SIZE) return false;
        if (!vertical && col + size > SIZE) return false;
        
        // Check for overlapping ships.
        for (int i = 0; i < size; i++) 
        {
            int r = vertical ? row + i : row;
            int c = vertical ? col : col + i;
            if (grid[r][c] != '~') return false;
        }
        
        // Place ship segment.
        ArrayList<int[]> segments = new ArrayList<>();
        for (int i = 0; i < size; i++) 
        {
            int r = vertical ? row + i : row;
            int c = vertical ? col : col + i;
            grid[r][c] = type.symbol;
            segments.add(new int[]{r, c});
        }
        
        ships.add(new Ship(type, name, segments));
        return true;
    }
    
    /**
     * Processes a shot at given coordinates.
     * @return true if shot hit a ship.
     */

    public boolean recordShot(int row, int col) 
    {
        if (!isValidShot(row, col)) return false;
        
        boolean hit = grid[row][col] != '~';
        grid[row][col] = hit ? 'X' : 'O'; // 'X'=hit, 'O'=miss.
        
        if (hit) 
        {
            // Find and damage the hit ship.
            for (Ship ship : ships) 
            {
                if (ship.isAt(row, col)) 
                {
                    ship.recordHit(row, col);
                    break;
                }
            }
        }
        return hit;
    }
    
     /** This gets display state for a cell. */
    public char getCellState(int row, int col, boolean showShips) 
    {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) return '~';
        char cell = grid[row][col];
        if (cell == 'X' || cell == 'O') return cell; // Always show shots.
        return showShips ? cell : '~'; // Hide enemy ships during gameplay.
    }
    
    /** This checks if all ships are sunk. */
    public boolean allShipsSunk() 
    {
        for (Ship ship : ships) 
        {
            if (!ship.isSunk()) return false;
        }
        return true;
    }
    
    /** This sets ship at specific coordinates. */
    public Ship getShipAt(int row, int col) 
    {
        for (Ship ship : ships) 
        {
            if (ship.isAt(row, col)) return ship;
        }
        return null;
    }
    
     /** This validates if the coordinates can be shot at. */
    public boolean isValidShot(int row, int col) 
    {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE 
               && grid[row][col] != 'X' && grid[row][col] != 'O';
    }
}