2654743
Chigozie Joel Uzonwanne
Project Report: Battleships Game
________________________________________
Executive Summary
This project aimed to implement a Battleships game in Java, allowing a human player to compete against a computer opponent. However, despite extensive effort, the game does not fully work as intended. Many challenges were encountered, including issues with compiling the provided BattleGui class in BlueJ, difficulties in debugging interactions between classes, and struggles with implementing functional game logic. Although key object-oriented principles such as high cohesion and low coupling were followed, the complexity of managing state and interactions led to significant setbacks.
The main menu and game UI load correctly, but the computer player does not interact as expected during gameplay. Initially, the game displayed a victory message when the player continued clicking the grid without waiting for the computer's turn. However, after troubleshooting, I unintentionally removed the victory message altogether, further breaking the game. This report provides an honest reflection of the experience, the obstacles faced, and potential improvements.
________________________________________
Introduction
This report describes the attempted implementation of the Battleships game, highlighting the challenges and limitations encountered during development. The project involved designing multiple interacting classes, implementing game logic, and integrating a graphical user interface (GUI). Despite following structured development practices, the final product does not function as expected, leading to frustration and exhaustion.
________________________________________
Requirements
The project aimed to meet the following requirements:
•	A GUI for game interaction.
•	Random ship placement.
•	Turn-based gameplay between a human player and a computer opponent.
•	A menu system for game control (new game, save, load, view fleet).
•	Tracking of hits, misses, and ship status.
•	A basic AI opponent with targeting logic.
However, due to difficulties in implementation, some of these features remain incomplete or malfunctioning. The graphical interface faced issues with event handling, the AI logic did not perform as expected, and debugging the interactions between game components proved time-consuming and frustrating.
________________________________________
Design
The game followed an object-oriented approach, using the following key classes:
•	BattleGui: Handles user interaction and GUI updates.
•	BattleshipsGame: Manages game logic, including turns, win conditions, and game state.
•	Board: Represents the game grid, handling ship placement and shot recording.
•	Player: Serves as a base class for human players.
•	ComputerPlayer: Implements basic AI behavior for the opponent.
•	Ship: Represents individual ships, tracking their position and status.
Despite designing a structured class hierarchy, integrating them effectively proved challenging. Ensuring smooth turn-based mechanics and proper synchronization between game state and GUI was a major difficulty.
Game Flow Issues
1.	The game initializes, but GUI updates do not always reflect the correct state.
2.	Shot processing has inconsistencies, leading to unexpected behaviour.
3.	AI decision-making does not always execute correctly, leading to the computer failing to take its turn.
4.	Saving and loading game states sometimes result in corrupted data.
5.	The victory message no longer displays after troubleshooting, making it unclear when the game ends.
Pseudocode for intended shot handling (but not fully working in practice):
if (valid shot):
    update board;
    if (hit):
        check if ship is sunk;
        if (all ships sunk):
            end game;
    else:
        switch turn;
________________________________________
Source Code Challenges
•	BattleGui: Difficulty in getting event handling to function properly.
•	BattleshipsGame: Complexity in managing game flow led to errors.
•	Board: Issues in ship placement and hit detection logic.
•	ComputerPlayer: AI strategy did not work as expected, often failing to take its turn.
•	Ship: Mostly functional but required better integration with Board.
While the code was written with best practices in mind, debugging was difficult due to interdependencies between classes.
________________________________________
Instructions
1.	Run BattleGui.java.
2.	Click grid cells to fire shots.
3.	Click the menu to start a new game, save, or load.
4.	The computer does not always take its turn, causing gameplay to freeze.
5.	The victory message does not display, even when all enemy ships are sunk.
________________________________________
Evaluation
Challenges Faced
•	The provided BattleGui class had compatibility issues in IntelliJ.
•	Debugging object interactions and event-driven logic was overwhelming.
•	The AI malfunctioned, failing to take turns.
•	The game initially displayed a victory message when clicking after winning, but troubleshooting removed the message entirely.
•	Graphical elements did not always update as expected.
Bugs and Limitations
•	The game does not fully function as expected.
•	The computer player does not take its turn, breaking gameplay.
•	GUI updates do not always sync with the actual game state.
•	The victory message no longer displays, making it unclear when the game ends.
•	Saving/loading introduces inconsistencies.
________________________________________
Summary and Conclusion
This project was an immense challenge, and despite significant effort, the final product does not work as intended. While object-oriented principles were applied, practical implementation proved difficult. If given more time, a simpler approach with better debugging strategies would be beneficial. The experience, while frustrating, reinforced the importance of incremental testing and clearer debugging strategies.
________________________________________
Critical Self-Evaluation
•	What I Learned: Software development is challenging, and debugging complex interactions is time intensive.
•	Difficulties: Understanding and managing GUI interactions, AI logic, and game flow.
•	What I Would Do Differently:
o	Start with a simpler text-based version before adding a GUI.
o	Use a different development environment, like IntelliJ, because BlueJ lags and freezes too often on my laptop.
o	Focus more on debugging before expanding features.
o	Test small features before integrating them into the larger system to avoid breaking functionality.
•	Self-Assessment: I would rate my project 50/100 due to incomplete functionality. However, I gained valuable experience in problem-solving and software design.

