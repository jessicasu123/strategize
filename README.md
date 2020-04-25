final
====

This project implements a player for multiple related games.

Names: Brian Li (bl195), Holly Ansel (haa20), Jessica Su (js803), Sanya Kochhar (sk489) 

### Timeline

Start Date: March 30, 2020

Finish Date: April 24, 2020

Hours Spent: 500+

### Primary Roles

* **Jessica** - developer 
    * *Back-end responsibilities*
        * Created the Game framework & class to manage game logic 
        * Created the Board framework & class 
        * Created the Neighborhood hierarchy to pass in information about neighbors from board to game piece
        * Implemented the evaluation functions & moves required to run an Othello game 
    * *Front-end responsibilities* 
        * Made the CustomizationView to customize player images, board colors, and switch to dark/light mode 
        * Allowed board cells to hold multiple piece images 
* **Holly** - developer
    * *Back-end responsibilities*
        * Created the Agent Player
        * Established the Agent Hierarchy
        * Implemented the evaluation functions/win types & moves required to run a Checkers Game
        * Implemented the evaluation functions/win type required to run for a Tic-Tac-Toe game
        * Implemented the moves required to run a Mancala game
    * *Front-end responsibilities*
        * Created the Start View
* **Brian** - developer 
    * *Back-end responsibilities*
        * Created and implemented JSONFileReader
        * Implemented the evaluation functions & moves required for TicTacToe and Connect4
    * *Front-end responsibilities*
        * Implemented GameView (Button functionality, board, and save file)
        * Implemented PopUps class for button functionality
* **Sanya** - developer
    * *Back-end responsibilities*
        * Created the basis of the Controller and GamePiece frameworks
        * Implemented the agent evaluations for Mancala, Chopsticks and Othello
        * Implemented the moves required for Mancala and Chopsticks game pieces
        * Implemented game restart functionality
    * *Front-end responsibilities*
        * Implemented GameSetupOptions for changing board dimensions and playing as player 1 or player 2 after selecting a game
        * Implemented EndViewPopUp (display win status, play again, return to setup and return to menu when the game ends)


### Resources Used

The following are resources we consulted to create evaluation functions for the AI minimax function for some of our games: 
   * Inspiration for **Othello’s** square weights was taken from [here](http://mnemstudio.org/game-reversi-example-2.htm). 
   * Inspiration for **Mancala’s** evaluation function was taken from [here](http://www-scf.usc.edu/~shin630/Youngmin/files/AI_HW2_Description.pdf). 
   * Inspiration for **Checkers’** sum of distances was taken from [here](https://cs.huji.ac.il/~ai/projects/old/English-Draughts.pdf).


### Running the Program

**Main class**: 

The game starts when you run the **Main** class. A screen with all the game options will appear, where you can use the navigation buttons to select a game, load a saved game, or load a known configuration. From there, you can play the game and customize it how you want! 

**Data files needed**: 

* *Game information*
    * All the information specific to a game is present in the JSON files in our src/resources/gameFiles folder. 
    * Currently, our supported default game files are checkers.json, chopsticks.json, connect4.json, mancala.json, othello.json, and tic-tac-toe.json. 
    * When you save a current configuration while playing the game, a game file with the name you have chosen to save it with will appear in the same folder. 
* *View information*
    * All the information (ex. Button actions, label texts, etc.) related to populating a view are in the src/resources folder.
    * The current files corresponding to different views in the game are: CustomizationView.json, 
        EndView.json, GameCenterView.json, GameSetUpOptions.json, and GameView.json. 
    * We also have a style.css file in the src/resources file for specific styling options. 
* *Maven libraries*
    * For JSON file parsing: org.json 
        * org.json:json:20190722
            * May need to remove the junit component of this because it is an old version
    * For JavaFX components/testing
        * org.testfx:testfx-junit5:4.0.16-alpha


**Features implemented**:

* Example games: 
    * Tic Tac Toe 
    * Connect4 
    * Othello
    * Checkers 
    * Mancala 
    * Chopsticks
* Option to restart game or return to setup
* Option to keep playing game after win/loss and winner scores
* Customization of board dimensions
* In-game customization of piece images, board colors
* Option to play game in dark mode or light mode 
* Save a current game configuration to a data file
* Playing a saved game
* Artificial player

### Notes/Assumptions

**Assumptions or Simplifications**:

* Will always be 2 player games 
* All pieces in a particular game share basic functionality (although there can be some sort of "special state" like a king state in Checkers)
    * To clarify: our program does not currently support games like Chess, where there are 16 types of pieces with varying functionality 
* Can only play against a computer agent, not against another human player

**Interesting data files**:
* Tic-tac-toe.json 
    * Contains 3 different dimensions to choose from: 3x3, 4x4, 5x5 
    * For each dimension, the number to get in a row increases (3x3 requires 3 in a row to win, 4x4 requires 4 in row, 5x5 is 5 in a row) 
* Mancala.json 
    * Besides the initial configuration of player states (ex. 0 for empty, 1 for player 1, 2 for player), the object configuration is especially relevant because each Mancala cell holds more than one object 
    * Because it requires multi-piece cells in the view, the config file indicates how many maximum pieces can be displayed in a cell 
    * The special states in Mancala also represent the player banks, which are different colors for each player but also collect pieces 

**Known Bugs**: N/A

**Extra credit**:
* Ability to play Connect5
* Choice of playing as player 1 or player 2
* How To Play pop up


### Impressions

We thought this project was a good way to learn how to effectively use data-driven design to easily run a variety of strategy games. For all the games, we found commonalities such that we could implement general evaluation function types, win types, move checks, and move types. Each game specifically leverages a combination of these types, as well as specific configuration or visual information, in order to allow players to make moves and determine win statuses. With this data-driven framework, we were able to specify everything required to run the front-end and back-end portions of the game in a JSON configuration file, while also providing the flexibility for a user to potentially create his or her own game by using a combination of our supported evaluation functions, win types, move types, move checks, configurations, and visual information. Currently, we can support Tic-Tac-Toe, Connect4, Othello, Checkers, Mancala, and Chopsticks, and users can customize the pieces, board, and background to look the way they want!
