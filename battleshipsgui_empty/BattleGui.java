import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * This class is the graphical interface for Battleships game.
 * It handles user input and display.
 */
public class BattleGui implements ActionListener 
{
    private static final String DEFAULT_FILENAME = "battleship_save.dat";
    private static final int GRID_SIZE = Board.SIZE;
    
    private final JButton[] buttonArray = new JButton[GRID_SIZE * GRID_SIZE];
    private BattleshipsGame game;
    private final JFrame frame;
    
    /** This creates new GUI with game instance. */
    public BattleGui(BattleshipsGame game) 
    {
        this.game = game;
        this.frame = new JFrame("Battleships Game");
        initializeGUI();
    }
    
    /** This sets up main window components. */
    private void initializeGUI() 
    {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(createMenuBar());
        frame.setContentPane(createMainPanel());
        frame.pack();
        frame.setSize(600, 600);
        frame.setVisible(true);
    }
    
    /** This creates menu bar with game options. */
    private JMenuBar createMenuBar() 
    {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        
        String[] menuItems = 
        {
            "New Game", "Save Game", "Load Game", "View Fleet"
        };
        for (String item : menuItems) 
        {
            JMenuItem menuItem = new JMenuItem(item);
            menuItem.addActionListener(this);
            gameMenu.add(menuItem);
        }
        
        menuBar.add(gameMenu);
        return menuBar;
    }
    
    /** Creates the main game grid panel. */
    private JPanel createMainPanel() 
    {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel gridPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        
        // Initialize grid buttons
        for (int i = 0; i < buttonArray.length; i++) 
        {
            buttonArray[i] = new JButton();
            buttonArray[i].setActionCommand(String.valueOf(i));
            buttonArray[i].addActionListener(this);
            buttonArray[i].setPreferredSize(new Dimension(50, 50));
            gridPanel.add(buttonArray[i]);
        }
        
        panel.add(gridPanel, BorderLayout.CENTER);
        updateBoard();
        return panel;
    }
    
    /** This handles all button clicks.*/
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() instanceof JMenuItem) 
        {
            handleMenuAction(e.getActionCommand());
        } else if (e.getSource() instanceof JButton && game.isPlayerTurn()) {
            handleGridClick((JButton) e.getSource());
        }
    }
    
    /** This processes the menu selection. */
    private void handleMenuAction(String command) 
    {
        switch (command) 
        {
            case "New Game":
                game.startNewGame();
                break;
            case "Save Game":
                if (game.saveGame(DEFAULT_FILENAME)) 
                {
                    JOptionPane.showMessageDialog(frame, "Game saved!");
                }
                break;
            case "Load Game":
                BattleshipsGame loaded = BattleshipsGame.loadGame(DEFAULT_FILENAME);
                if (loaded != null) 
                {
                    game = loaded;
                }
                break;
            case "View Fleet":
                showFleetStatus();
                break;
        }
        updateBoard();
    }
    
    /** This processes grid clicks during the player's turn. */
    private void handleGridClick(JButton button) 
    {
        int index = Integer.parseInt(button.getActionCommand());
        int row = index / GRID_SIZE;
        int col = index % GRID_SIZE;
        
        if (game.processPlayerShot(row, col)) 
        {
            updateBoard();
            
            // If computer's turn, process after short delay
            if (!game.isPlayerTurn()) 
            {
                Timer timer = new Timer(1000, e -> 
                {
                    updateBoard();
                    ((Timer)e.getSource()).stop();
                });
                timer.setRepeats(false);
                timer.start();
            }
        }
    }
    
    /** Updates all grid buttons to reflect current game state. */
    private void updateBoard() 
    {
        for (int i = 0; i < buttonArray.length; i++) 
        {
            int row = i / GRID_SIZE;
            int col = i % GRID_SIZE;
            char state = game.getCellState(row, col, false);
            
            buttonArray[i].setText(String.valueOf(state));
            buttonArray[i].setBackground(getColorForState(state));
            buttonArray[i].setEnabled(game.isPlayerTurn() && !game.isGameOver());
        }
    }
    
    /** This maps cell states to colors. */
    private Color getColorForState(char state) 
    {
        return switch (state) 
        {
            case 'X' -> Color.RED;    // Hit
            case 'O' -> Color.WHITE;  // Miss
            case 'B' -> Color.YELLOW; // Battleship (view fleet)
            case 'C' -> Color.ORANGE; // Cruiser
            case 'D' -> Color.GRAY;   // Destroyer
            case 'S' -> Color.PINK;   // Submarine
            default  -> Color.BLUE;   // Water
        };
    }
    
    /** Shows player's ship positions in dialog. */
    private void showFleetStatus() 
    {
        StringBuilder sb = new StringBuilder("Your Fleet:\n");
        for (int i = 0; i < GRID_SIZE; i++) 
        {
            for (int j = 0; j < GRID_SIZE; j++) 
            {
                sb.append(game.getCellState(i, j, true)).append(" ");
            }
            sb.append("\n");
        }
        JOptionPane.showMessageDialog(frame, sb.toString());
    }
    
    /** Launches the game. */
    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> 
        {
            BattleshipsGame game = new BattleshipsGame();
            new BattleGui(game);
        });
    }
}