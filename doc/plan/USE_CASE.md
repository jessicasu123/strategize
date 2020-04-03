Use Cases
====
* User chooses and clicks on game from list of games on GameCenter View

    - Controller is created and passed in as a paramter to GameView

    - Game Center text and icons parsed from GameCenterView JSON file

* User is presented with GameView starting configuration and button pannel

    - User accesses buttons implemented through GameView.createButtons() 

     - GameView text and icons parsed from GameView JSON file

     -  User calls controller (Filereader) for initial configuration of board 

    - User calls GameView.display() to show game configuration on board

* User customizes their preferences for the game’s view

    - User clicks on dropdown boxes from CutomizationView to select colors and images for certain game pieces

    -  User calls GameView.mapColor(color, state) and GameView.mapImage(image, state) to map Colors and images to a certain game piece which are displayed accordingly in the GameView

* User clicks the “make move” button after finalizing move

    -  User calls controller.playMove()
    -  Controller sends the move information stored by pieceSelected and squareSelected to the back-end to validate and act upon

* User clicks the main menu button

    -  User’s view is changed from GameView to StartView (where a new game is chosen)

* User clicks the save game button

    - User calls Popup.save() and prompts user to enter file details

* User enters save details and clicks enter button

    -  User calls FileGenerator.createFile() to create a new file based on the user’s inputted details. 
    
    -  JSON File with current configuration is created by the FileCreator 

* User clicks the settings button

    -  User calls Popup.settings() and provides user with option to change gamepiece color/image and gameboard background color.
    
    -  User clicks save button and calls GameView to update settings with GameView.update().  

*  User clicks the help button

    - User calls Popup.help() and provides user with how-to-play information.

    -  User parses help text from GameView JSON file through calling FileReader.read().

* User clicks the restart button

    -   User reloads current game’s model to starting configuration with GameView.restart()

*   User enters game filename and clicks submit on StartView

    -  User calls FileReader.read(file) and determines game type, rules, and starting configuration.

    -  Controller is created and passed in as a paramter to GameView

    -  Game text and icons parsed from StartView JSON file

*   User moves game piece to valid location and presses 

    -  User passes x and y coordinates of game piece location to controller

    - User calls board.makeMove to validate the move with board.getMovesOfPiece and the model is updated 

    - Controller passes new configuration to GameView which updates the view.

* User moves game piece to invalid location

    -    User passes x and y coordinates of game piece location to controller

    -    User calls Game.makeUserMove to validate the move with board.getMovesOfPiece (invalid in this case)

    -   Controller passes error to GameView which calls Popup.invalidMove() that alerts the user that their move is invalid.

* Agent player moves game piece

    -    User calls Game.assignPlayerNumber(), agent.calculateMove(), and board.makeMove in the model to determine and update the agent’s move

    -    Controller passes updated model configuration data to GameView which updates the view.  

*   User Loses the game and clicks “Yes” to play again option

    -   User reloads current game’s model to initial configuration with GameView.restart()

*   User Loses the game and clicks “No” to play again option

    -   User’s view is changed from GameView to StartView (Game Center)

*   User is granted a random player number (1 or 2)

    -    User calls Game.assignPlayerNumber() in the model to randomly assign user and agent with their player numbers.

    -  Player number is passed to Controller and GameView calls controller.getUserNumber()

*   User is informed that they won/lost the game

    -    User calls Game.getGameStatus and agent.findWinner() in the model to determine if the user or agent won the game.

    -  Player number of winner is passed to Controller and GameView calls controller.getUserNumber() and Popup.winner(player number) to display the winner.

*   User selects Connect4 game in the GameCenter

    -   Controller is created and passed in for Connect4 GameView

    -  User calls FileReader.read() to parse JSON file for default Connect4 game configuration.

    -   User calls GameView.update() to show game configuration on board

*   User loads a saved game file for Connect4
    -   Controller is created and passed in for Connect4 GameView

    - User calls FileReader.read() to parse JSON File for the saved game’s configuration.

    - User calls GameView.update() to display the saved game configurations on board

    
