## Demo Order:
* Connect 4 
    * Start screen (Holly)
        * New games can be added just by editing data file
    * Introduce start up option screen (Sanya)
    * Talk about general program structure (Holly)
        * MVC framework and flow of information
            * Once hit start button creates a controller which calls a file reader to determine what type for each class to create
                * Controller creates various components of the game engine, including various components such as the game and AI agent
    * User plays move (Jessica)
        * Talk about GUI/controller/model interaction
        * Board verifies move
        * Talk about move check and move type
    * Agent player plays connect 4 move (Holly and Brian)
        * Agent player (Holly)
        * Board gets all legal moves for agent player (Brian)
    * Talk about agent (Holly and Brian)
        * General agent structure (Holly)
        * Talk about evaluation function and win type (Brian)
    * Customize piece appearances & switching to dark mode (Jessica)
    * Restart (Sanya)
        * Creates a new game and resets any game instance-specific variables
        * Does not change any setup options that were fed into the controller (player1/2, dimensions)
* Tic-Tac-Toe
    * (Mention that this game is the mod)
    * Application opens to GameSetupOptions (Sanya)
        * Talk about how different Board dimensions are offered
        * Player 1 vs player 2 start 
            * Select to play as player2
        * Select 3x3 and start the game 
    * Talk about agent move (Brian)
        * Evaluation function (gets all moves from game piece)
    * User plays a move (Brian)
        * Talk about move checks (EmptyStateCheck) and move types (ChangeToNewStateMove) 
    * Play until tie (Jessica)
        * How board does ties
    * End pop up (Sanya)
        * Click back to set up
        * Select to play on 4x4
    * Play until loss (Holly)
        * Talk about win type
        * Click play again
    * Talk about score (Sanya)
* Mancala
    * (application opens to board, mention that this is the significantly different game)
    * Board (Jessica)   
        * State config 
        * Object config 
    * GamePieces (Jessica)
        * All have state 
        * Mancala also changes its number of objects 
    * Click How to play (Brian)
    * Make user move that has user play another turn (Holly)
        * Talk about turn logic for game class
        * Talk about move checks and move types
    * Agent move (Sanya)
        * Talk about evaluation function (MorePieces) and win type (NoMovesMorePieces)
    * Save dialog from within the game (Brian)
        * Saving file & loading it 
            * Save general properties (same as starting) 
            * Also saves the current (not starting) object & state configuration 
        * Back to menu button then load saved game
         
## Project Discussion
* Transition from demo to functionality (Sanya)
#### Functionality. Show off the features of your running program:
* run the program from the master branch through a planned series of steps/interactions (all)
* show an example of all the kinds of data files used by the program and describe which are essential (e.g., internal resources) and which can be user created (e.g., external or example data)
    * Game data file (make sure to know all the specific vars/booleans even if you don’t have to mention each) (Sanya)
        * Some important features that change the way the game runs:
            * the evaluation function and win type to be used by the agent to determine the outcome of a move
            * Themovechecks and movetypes used by the piece to calculate the possible moves and carry out the moves for a specific piece.
            * These are interchangeable so the functionality of the game is very flexible to use different winning conditions or change the behavior of pieces
        * There are also a number of booleans to indicate game-specific differences in certain classes, for example TTT and connect 4 use the same evaluations but the CheckBelow boolean indicates that in connect4, an additional check needs to be carried out to ensure that there is a piece beneath the current cell for a move to be valid
        * Default board config as well as a few alternate board configurations, especially since in the case of tic tac toe, the winvalue changes
        * Players - player 1 & 2, default images
        * Rules - for the how to play screen
    * View data file (Brian and Jessica)
        * Game center data file (Brian)
            * Contains data regarding the label, icon, description, and default file for each of the 6 strategy games as well as the text for loading a saved game.
        * Game view data file (Brian and Jessica)
            * Contains data regarding the icons and text for various GameView buttons and labels. This file also contains the text for alerts when an error is encountered by the user.  
            * Also demonstrates reflection - keys are button names and values are the methods they refer to (Jessica)
* show a variety of tests (including both happy and sad paths for backend classes, frontend classes, and integration tests) and verify they all pass (Holly)
    * Happy paths
    * Sad paths
    * Integration tests
* if asked, everyone should be able to describe how their code relates to specific demoed features as they are shown (all)
* Transition from functionality to design (Holly)
#### Design. Revisit the design from the original plan and compare it to your current version (as usual, focus on the behavior and communication between modules, not implementation details):
* describe two APIs in detail (one from the first presentation and a new one): (Holly)
1. API = Game Piece (one from first presentation)
    * open for extension to support easily adding new features
        * Can easily add different functionalities of game pieces by propagating the list of self move checks, neighbor move checks, and move types in different ways
             * If want to add functionality to game piece can create one of these small components 
    * support users (your team mates) to write readable, well designed code
        * Encapsulates differences between game piece types (move checks and move types) from the rest of the program
        * Flexible to account for complex game functionalities by combining as many move checks and move types as user desires
    * changed during the Sprints
        * This API changed during sprint 2 when we realized that for different games the pieces needed to be able to have different information such as multiple states, objects, directions, and having an effect on the turn
        * Changed during sprint 3 when made into concrete class and implemented move checks and move types, gives flexibility for different game piece functionalities without having game specific game pieces
2. API = Evaluation Function (new one)
    * open for extension to support easily adding new features
        * To add a new evaluation function just implement the interface, add to factory, and then include your evaluation function in a data file to see it implemented. 
            * Other than the factory no need to touch any other existing code
    * support users (your team mates) to write readable, well designed code
        * Ensures adheres to single responsibility principle since only has 1 public method called on by the program
            * Makes code readable because the classes are small and encapsulate possible complex logic or calculations from the rest of the program
        * Provides flexibility to add and combine multiple evaluation types without having them affect one another
    * changed during the Sprints
        * Was created in the final sprint. However the functionality was present in the previous sprints and was preserved just simply extracted into its own class and made more general
* show two Use Cases implemented in Java code in detail that show off how to use each of the APIs described above (Brian)
1. Game Piece
    * Use Case: User makes move in Connect4 and game piece is created
        * In the Controller, GamePieceCreator is created through the makeGamePieceCreator method
        * In the new GamePieceCreator, creategamepiece is called which creates a new gamepiece with the move types and move checks of connect4 as specified in the data file.
        * Specifcally, the movetype for connect4 is ChangeToNewState
        * And the Move Checks are BelowCheck, EmptyStateCheck 
2. Evaluation Function
    * Use Case: User takes first turn in Tic-Tac-Toe and it is up to agent to make move
        * Agent and evaluation functions specified in game file that compose agent are created in controller through the createAgent and createEvaluationfunction methods which take in the current game configuration as a parameter.
        * NumOpenLines evaluation function is used to determine the best possible move for the agent using the AI minimax algorithm.
        * Finally, in the Agent, evaluateCurrentGameState is called to return the evaluation of the current game state based on the NumOpenLines evaluation function
* revisit the design's goals: is it as flexible/open as you expected it to be and how have you closed the core parts of the code in a data driven way? (Sanya)
    * We were able to refactor our design to be more flexible than expected since we had time in sprint 3 to refactor out our inheritance hierarchies for the agent and the game pieces to use composition instead. 
        * This has made our program truly flexible in that you can create different rules for games and completely change the behavior of pieces without touching the code.
        * Since we have used composition, it’s easy to add any number of new games that use some of the common evaluations, for example you can create a crossover between Othello and Connect 4 by simply using your own choice of piece and agent composition classes
    * Closed: it is closed to modification in that adding a new game type does not allow or require you to touch the game pieces
        * MoveChecks and MoveTypes are open to adding new types, but the overall functionality of the concrete classes are closed in that they used those components in a predefined way (eg: first self then neighbor checks)
* describe one element of the design that has changed based on your deeper understanding of the project: how were those changes discussed and what trade-offs ultimately led to the changes (Jessica)
    * Initially, we started with having game-specific classes for both the agent and the game pieces 
    * Once we realized that we could greatly improve our code’s design by making it more data-driven, we made the big shift to entirely removing game-specific agent and game piece classes 
        * We’re going to talk about how we refactored the game pieces in particular, because this process was more difficult and requires more discussion of trade-offs 
            * PROS: user would have flexibility to select any combination of move checks and move types to create their own game (entirely data-driven) 
            * CONS: would reduce the efficiency of some parts of running the game (ex. By separating the move validation and making move components, some logic is repeated multiple times on each move) 
        * Ultimately, we knew trying to make our game pieces more data-driven was definitely the way to go 
            * We found commonalities between the way move checks were made and created a MoveCheck interface
                * For example: both connect 4 and tic tac toe have to check if the current state is empty, so we have an EmptyStateCheck 
            * We also found similarities in the execution of Moves and created a similar MoveTypes interface 
* Transition from functionality to team (Jessica)
#### Team. Present what your team and, you personally, learned from managing this project:
* contrast the completed project with where you planned it to be in your initial Wireframe and the initial planned priorities and Sprints with the reality of when things were implemented (Jessica) 
    * We were able to complete all the tasks we assigned ourselves during Sprint 1 and 2 
    * As for our initial wireframes, we made them under the impression that we would need to complete almost all of the additional features 
        * Therefore, we did not implement everything from our wireframes, but we still have 6 features from the additional features list 
    * For the final sprint, rather than building features such as a social center or a player profile, we used our time to refactor our design and make it more data-driven 
* Also, share one thing each person learned from using the Agile/Scrum process to manage the project. (all) 
    * Jessica - I learned that we could use Git to assign issues to people and use milestones to track our progress. 
    * Holly - I learned how beneficial it is to have small tasks to work on and still have a weekly goal of a functional project
    * Sanya - I learned that splitting work into smaller, more tangible goals made work more manageable as well as allowed us to prioritize features that were most important first
    * Brian - I learned that having sprints and milestones is extremely effective because it keeps us accountable for our individual responsibilities which greatly improved productivity.
* show a timeline of at least four significant events (not including the Sprint deadlines) and how communication was handled for each (i.e., how each person was involved or learned about it later) (Sanya)
    1. Integration for sprint 1 - After we finished with our plan, we separated off to write our own parts. Later in that week, we came back together to integrate and found that, because we had all based our code on the APIs and mock interfaces, our different parts interacted exactly how we expected them to. Holly had included extensive comments detailing each method, and we had made a google doc to document calls each method would make so we were able to interact with each other’s code as if it was already written so integration was seamless.
    2. Refactoring Agent - going into the final sprint, we met up to do a code review - wanted to focus more on design than features - decided to refactor Agent to use composition. We each took responsibility for the agents that we had originally written to be game specific and identified ones that could be shared amongst different games. We also wrote new tests to make sure these worked as expected.
    3. Refactoring GamePiece - after office hours with you, we discussed how to break apart and reassemble our GamePiece into MoveChecks and MoveTypes. We marked ones that were similar across our games, each picked a few and took a day to work separately. The next day, we met up and pair programmed integration by screen sharing and stepping through everywhere that we needed to make changes. Each person’s MoveChecks & MoveTypes required different data to be indicated in the data files, so we were able to think through how that data would be provided and flow through.
    4. Integration tests - after this large scale refactoring, before deleting our old agents and GamePieces, each of us wrote integration tests in addition to the tests for all the new components we had built. These were to make sure that each game’s own interaction worked with the new set of piece and agent logic from start to finish. We each took responsibility for the games that we had worked most on.
* Also, share one thing each person learned from trying to manage a large project yourselves. (all)
    * Jessica - Even though it might seem tedious, it’s really important to have a really solid plan in the beginning (ex. Outlining all the methods for APIs)  and delegate responsibilities clearly. 
    * Holly - In having such a large project I learned the importance of making sure to write good and well designed code the first time around rather than having the mindset of refactoring later
    * Sanya - Effective planning can make working separately much more productive. By talking through the method calls and extensively commenting the mock APIs, we were able to know exactly what others’ code would do before it was written. 
    * Brian - I learned the importance of making sure to be in constant communication with all team members to make sure everyone’s questions are quickly resolved and ideas are considered. 
* describe specific things the team actively worked to improve on during the project and one thing that could still be improved (Holly)
    * Worked to improve
        * Data driven design
            * Agent and game piece
        * Flexibility of the program (ties in with data driven design)
        * Overall good design for the flow of information
        * Readable and understandable code
    * Could still be improved
        * Single responsibility principle (i.e controller and json file handler)
* Also, share one thing each person learned about creating a positive team culture. (all)
    * Jessica - A positive team culture involves always being willing to help & being responsive in the group chat.  
    * Holly - Creating a positive team culture definitely involves everyone being equally committed to the project and working their hardest
    * Sanya - A positive team culture really involves communicating regularly, being responsive and each working at the same capacity
    * Brian- A positive team culture involves being motivated and excited as a team to carry out a common goal and being respective of everyone’s ideas
* revisit your Team Contract to assess what parts of the contract are still useful and what parts need to be updated (or if something new needs to be added) (Brian)
* Also, share one thing each person learned about how to communicate and solve problems collectively, especially ways to handle negative team situations. (all)
    * Jessica - I learned to listen to everyone’s viewpoints respectfully (even if you disagree) before coming to a decision as a group.  
    * Holly - To communicate and solve problems collectively I learned that it's important to share whatever ideas you have, although they might not be the best it may spark someone else’s thought process
    * Sanya - I learned that effectively solving problems together involves offering multiple solutions so the team can help evaluate their pros and cons, and making an effort to understand each other’s challenges
    * Brian - I learned that it may take multiple attempts and trials for a team to come up with an effective solution to an obstacle and to be patient and respectful when encountering issues
