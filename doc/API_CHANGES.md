## Original APIs

* ControllerFrameWork
    * haveAgentMove -> method removed
        * This method is no longer in our program because we originally did not anticipate for having turn logic in our program. Initially our program just rotated between the method for user move and agent move and didn't account for things such as passing or having another turn. When implementing the turn logic it was necessary to just call one method in the controller and then have the Game class see which players turn it is.
    * getUserNumber -> method removed (but was replaced by a method with a similar purpose)
        * Initially we thought that players would be distinguished just by a single number. However as we started doing more complicated games such as checkers and mancala we saw some games had multiple states for each player. So only using one number to identify the state of a player would be inadequate.
  
* FileDataException -> renamed
   * This class was renamed to be InvalidDataException because it is more clear what is being checked for. This was simply a name change so it was not really a change to the API
    
* FileHandler
    * saveToFile -> change in parameters
        * Originally when saving a file we did not expect that in addition to state information we would also have to deal with object information. In learning that we needed to deal with object information we realized that we needed that as a parameter to save as well since for games such as mancala the number of objects changes.
        Additionally originally we were going to take in a map for the properties, however in implementing the actual program we realized that the properties would never change and visual properties (such as images) could easily be adjusted in the customization window upon starting again, so we thought it would be more efficient to not use this parameter.
    
* AgentPlayer
    * findGameWinner -> moved to a different class
        * Early in the program we realized that to allow flexibility in the future if we wanted to add the ability to play against a human the logic of the agent versus the agent player would need to be separated. The AgentPlayer would handle finding a move while the Agent would evaluate the game state and determine if the game has been won and if so by whom. If this method was in the AgentPlayer then an AgentPlayer would be needed even when 2 humans were playing against each other which would be unnecessary/complicated.
    
* BoardFramework
    * getAllLegalMoves -> parameter added
        * A parameter was added for a list of player states because we initially did not think through the fact that you would need to find the legal moves for a specific player who may have multiple states
    * makeMove -> parameter added
         * A parameter was added for the player because we initially did not think through the fact that you would need to check the player that you are making the move for
    * evaluateBoard -> removed
        * Initially we were unsure if the Agent player would be able to evaluate the board from its own class. This method was a precaution in case not possible but we realized early on that this was not needed nor was this possible in the board. 

* Coordinate 
    * getRow -> renamed for readability
        * This method was renamed for readability because the logic of the program corresponds to rows and columns, but before this method was calling for the x coordinate which was really the row and made the logic confusing. This was only refactored to change the name.
    * getCol -> renamed for readability
         * This method was renamed for readability because the logic of the program corresponds to rows and columns, but before this method was calling for the y coordinate which was really the column and made the logic confusing. This was only refactored to change the name.

* GameFramework
    * These methods were made private and a public method was used in its place which calls on these two methods. This is also because we didn't anticipate turn logic. In order for the game to control the turn logic when a move is being made we needed to check whose turn it was in order to tell which player to move. It should not be the case that any player could be called on at anytime, which is what would have been the case if these methods were public.
        * makeUserMove
        * makeAgentMove

* GamePiece
    * calculateAllPossibleMoves
        * A parameter was added for the player because we initially did not think through the fact that you would need to find the possible moves for a piece for a specific player. For example in checkers just because a piece has moves doesn't mean either player can move it.
    * makeMove
        * A parameter was added for the player because we initially did not think through the fact that you would need to make the move for a specific player. Making a move for a player changes the state of the piece. For example X player moving in tic-tac-toe versus O player moving results in 2 different states, so the player making the move needs to be known.
    * evaluateState
        * Initially we were unsure if the Agent player would be able to evaluate the game state from its own class. This method was a precaution in case not possible but we realized early on that this was not needed nor was this possible in the at the piece level. 

    
* GameViewFramework
    * update -> removed
        * Having a timeline for this game does not make sense because the changes happen based on the user rather than automatically. Therefore we decided to have updates to the state based on action events such as buttons pressed. Because of this there was no need for a public method, rather it just displayed itself. Having an update method would not have been functional.
    