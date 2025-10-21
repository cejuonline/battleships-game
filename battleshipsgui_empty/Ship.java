import java.io.Serializable;
import java.util.ArrayList;
/**
 * This class represents a ship in the Battleship game.
 * It tracks their type, position, and the damage state.
 * Serializable for game saving/loading.
 */
public class Ship implements Serializable 
{
    private static final long serialVersionUID = 1L;
    
    /** The Ship types with their sizes and display symbols */
    public enum Type 
    {
        BATTLESHIP(4, 'B'),  // 4 tiles.
        CRUISER(3, 'C'),     // 3 tiles.
        DESTROYER(2, 'D'),   // 2 tiles.
        SUBMARINE(1, 'S');   // 1 tile.
        
        public final int size;
        public final char symbol;
        
        Type(int size, char symbol) 
        {
            this.size = size;
            this.symbol = symbol;
        }
    }
    
    private Type type;
    private String name;
    private ArrayList<int[]> segments; // Coordinates occupied.
    private boolean[] hits;            // Damage state.
    
    
    /**
     * This creates a new ship.
     * @param type Ship classification.
     * @param name Display name.
     * @param segments Board coordinates the ship occupies.
     */
    public Ship(Type type, String name, ArrayList<int[]> segments) 
    {
        this.type = type;
        this.name = name;
        this.segments = segments;
        this.hits = new boolean[type.size];
    }
    
    /** This checks if the ship occupies a given coordinate. */
    public boolean isAt(int row, int col) 
    {
        for (int[] segment : segments) 
        {
            if (segment[0] == row && segment[1] == col) 
            {
                return true;
            }
        }
        return false;
    }
    
     /** This records a hit at a specific coordinate. */
    public boolean recordHit(int row, int col) 
    {
        for (int i = 0; i < segments.size(); i++) 
        {
            if (segments.get(i)[0] == row && segments.get(i)[1] == col) 
            {
                hits[i] = true;
                return true;
            }
        }
        return false;
    }
    
    
    /** This determines if the ship is completely sunk. */
    public boolean isSunk() 
    {
        for (boolean hit : hits) 
        {
            if (!hit) return false;
        }
        return true;
    }
    
    public Type getType() 
    { 
        return type; 
    }
    public String getName() 
    { 
        return name; 
    }
}