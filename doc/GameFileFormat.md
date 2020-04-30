## Purpose
- This file indicates the format that must be followed to create a new game file. 
- Comments explaining key values are included as:
    > blockquotes
- Permissible values for certain keys are included in the appendix below
- For keys that are not used by all games, default values are included

## Template
{

"Keys": ["Gametype", "Neighborhood", "WinType", "EvaluationFunctions", "SelfMoveChecks", "SelfNumObjectsToCompare",
    "NeighborMoveChecks", "NeighborNumObjectsToCompare", "MoveTypes", "NeighborConverterType","SpecialPieceIndex",
    "Player1PromotionIsLastRow", "OnlyChangeOpponent", "EmptyState",
    "PiecesMove", "Player1PosDirection", "CheckCurrConfig", "MultiplePiecesPerSquare", "ConvertToEmptyState",
    "NumVisualRowsPerSquare","MaxNumObjectsPerSquare","SquareClickOptions","SquareClickType",
    "DimensionOptions", "BoardKeys", "Default","3 x 3", "4 x 4", "5 x 5", "PlayerKeys","Player1", "Player2", "possibleMove", "Rules"],
  
   > these keys must be included for the file to be read in with no errors. If any values are added, these keys must be updated 

  "Gametype": "",
   
   > * Specify name of the game
   > * String
  
  "Neighborhood": "",
   
   > * Specify neighborhood type from appendix
   > * String
  
  "WinType": "", 
   > * Specify WinType to be used by the agent from appendix
   > * String

  "EvaluationFunctions": [], 
  > * Specify evaluation functions to be used by the agent from appendix, separated by commas
  > * String Array

  "SelfMoveChecks": [],
  > * Specify MoveChecks to be carried out on a piece itself from appendix, separated by commas
  > * String Array

  "SelfNumObjectsToCompare": 0,
  > Default 0; relevant for games whose pieces have objects and use NumObjectsCheck; specifies the min bound for number of objects of that piece to be compared; int

  "NeighborMoveChecks": [],
  > Specify MoveChecks to be carried out on a piece's neighbor from appendix, separated by commas; String Array

  "NeighborNumObjectsToCompare": 0,
  > Default 0; relevant for games whose pieces have objects and use NumObjectsCheck; specifies the min bound for number of objects of a neighbor's piece to be compared; int

  "ConvertToEmptyState": true,
  > Default false; if a piece can be converted to an empty state in games such as Checkers; boolean

  "Player1PromotionIsLastRow": false,
  > Default false; relevant for games the use PromotionMove to indicate if the last row is a promotion row in games such as Checkers; boolean

  "OnlyChangeOpponent": false,
  > Default false; relevant for games that use ChangeNeighborObjectsMove, eg: Chopsticks, Mancala to determine a piece should only 
>be impacted if it is an opponent piece; boolean

  "MoveTypes": [],
  > Specify MoveTypes the game's pieces must carry out from the appendix below; String array

  "NeighborConverterType": "",
  > Specify ConvertibleNeighborFinder from the appendix below based on neighbors the game's piece can impact; String

  "SpecialPieceIndex": 0,
  > * Default 0
  > * Relevant for games that have special pieces (Eg: Checkers has kings at index 1 of their PlayerStates ArrayList); int

  "EmptyState": 0,
  > Empty state for that game's pieces; int

  "PiecesMove": false,
  > Indicates whether game pieces move or are stationary; boolean

  "CheckCurrConfig": true,
  > * Default: true
  > * Relevant for games that use MorePieces evaluation function or NoMovesMorePieces WinType to indicate
  > whether the current piece configuration is to be checked (true) or the object configuration (false); boolean

  "MultiplePiecesPerSquare": false,
  > * Default: false
  > * Relevant for games whose pieces have objects; boolean

  "NumVisualRowsPerSquare": 1,
  > Default: 1; relevant for games like Mancala where multiple rows may be represented on one board coordinate; int

  "MaxNumObjectsPerSquare": 1,
  > Default: 1; relevant for games like Mancala and Chopsticks where a piece may require visually showing multiple objects; int

  "SquareClickOptions": ["empty", "player", "agent", "own player"],
  > Indicates states a square can take; above options shown as an example; String array

  "SquareClickType": ["empty"],
  > Indicates states of squares that the user is able to click on for a valid move; above is example; String array

  "DimensionOptions": [ "3 x 3", "4 x 4", "5 x 5" ],
  > Indicates the board dimension options available for this game; above is format example, must match options in JSON object
>below; String array

  "BoardKeys": ["Height", "Width", "InitialConfig", "ObjectConfig", "WinValue", "BoardWeights"],
  > Keys to be read in for the board configuration. For internal use by FileHandler

  "Default": "3 x 3",
  > Default dimensions of the board; above is example; String array

  "3 x 3": {
    "Height": "3",
    "Width": "3",
    "InitialConfig": "0,0,0;0,0,0;0,0,0",
    "ObjectConfig": "1,1,1;1,1,1;1,1,1",
    "WinValue": 3,
    "BoardWeights": "0,0,0;0,0,0;0,0,0"
  },

  "4 x 4": {

    "Height": "4",

    "Width": "4",

    "InitialConfig": "0,0,0,0;0,0,0,0;0,0,0,0;0,0,0,0",

    "ObjectConfig": "1,1,1,1;1,1,1,1;1,1,1,1;1,1,1,1",

    "WinValue": 4,

    "BoardWeights": "0,0,0,0;0,0,0,0;0,0,0,0;0,0,0,0"
  },

  "5 x 5": {

    "Height": "5",

    "Width": "5",

    "InitialConfig": "0,0,0,0,0;0,0,0,0,0;0,0,0,0,0;0,0,0,0,0;0,0,0,0,0",

    "ObjectConfig": "1,1,1,1,1;1,1,1,1,1;1,1,1,1,1;1,1,1,1,1;1,1,1,1,1",

    "WinValue": 5,

    "BoardWeights": "0,0,0,0,0;0,0,0,0,0;0,0,0,0,0;0,0,0,0,0;0,0,0,0,0"
  },
  > JSON objects indicating values associated with the board configuration. Must match board dimensions indicated in array above.

>> InitialConfig: player states on the board, cols separated by commas, rows separated by semi-colons
>
>> ObjectConfig: any objects present on the board; defaults to all 1s if the game does not have objects
>
>> WinValue: default 0; relevant for games like TicTacToe and Connect4 that must reach a number of pieces in a row to win 

  "PlayerKeys": ["States", "Images", "Colors", "StatesToIgnore","ImmovableState", "Direction"],
  > Keys for player objects to be read in next; used internally by the file handler

  "Player1": {
  
    "States": [1],
  
    "StatesToIgnore": [],
  
    "ImmovableState": -1,
  
    "Direction": [0],
  
    "Images": ["X.png"],
  
    "Colors": ["white"]
  },

  "Player2": {

    "States": [2],

    "StatesToIgnore": [],

    "ImmovableState": -1,

    "Direction": [0],

    "Images": ["O.png"],

    "Colors": ["white"]
  },
  > JSON objects to indicate important player values; format as above
  >
  >> States: possible states each player's pieces may have
  >
  >> StatesToIgnore: relevant for games like Mancala which may have goal states to be ignored
  >
  >> ImmovableState: relevant for games where certain states may not be able to move
  >
  >> Direction: relevant for games where pieces are able to move in a certain direction on a board
  >
  >> Images: indicates the default piece image for that player's piece. File must be added to the /resources/images/pieces folder
  > 
  >> Colors: colors that the pieces use on the front-end

  "possibleMove": "",

  "Rules": ["Place piece in any open spot on the grid. Alternate turns with opponent.", "Prevent opponent from getting 3 in a row (horizontal, vertical, diagonal).", "First one to get 3 in a row wins!", "If nobody gets 3 in a row, a tie is called."]
  > Rules for the game to be displayed in the How To Play popup


}

## Appendix
- Existing permissible values for evaluations are included below. JavaDoc comments in each class explain their functionality
    
    - EvaluationFunctions:
        - MorePieces
        - NumOpenLines
        - PositionWeights
        - SumOfDistances
    
    - WinTypes:
        - ConsecutivePieces
        - NoMovesMorePieces
        - NoPiecesForOpponent
    
    - Neighborhoods:
        - CompleteNeighborhood
        - DiagonalNeighborhood
        - HorizontalNeighborhood
        - VerticalNeighborhood
    
    - ConvertibleNeighborFinders:
        - FlippableNeighborFinder
        - NeighborAtEndCoordinateFinder
        - NeighborsBetweenCoordinatesFinder
        - NeighborsUntilNoObjectsFinder
    
    - MoveChecks:
        - AllFlippableDirectionsCheck
        - BelowCheck
        - EmptyStateCheck
        - JumpCheck
        - NotEmptyStateCheck
        - NotImmovableCheck
        - NumObjectsCheck
        - OpponentPieceCheck
        - OwnPieceCheck
        - StepCheck    
        
    - MoveTypes
        - ChangeNeighborObjectsMove
        - ChangeOpponentPiecesMove
        - ChangeToNewStateMove
        - ClearObjectsMove
        - ForceMoveAgainMove
        - PieceAtMaxObjectsMove
        - PositionMove
        - PromotionMove
        - SpecialCaptureMove
        - SplitObjectsMove