package ooga.controller;

import ooga.model.data.FileHandler;
import ooga.model.data.JSONFileReader;
import ooga.model.engine.*;
import ooga.model.engine.GameTypeFactory.GameFactory;
import ooga.model.engine.GameTypeFactory.GameTypeFactory;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Controller implements ControllerFramework {
    private GameFramework myGame;
    private GameTypeFactory gameType;
    private FileHandler myFileHandler;
    private Boolean isPieceSelected;
    private int pieceSelectedX;
    private int pieceSelectedY;
    private int squareSelectedX;
    private int squareSelectedY;
    private String gameFileName;
    private boolean userIsPlayer1;
    private boolean userTurn;
    private PlayerInformationHolder myUserPlayerInformation;
    private PlayerInformationHolder myAgentPlayerInformation;



    public Controller(String fileName, String userID, String opponent) throws IOException, ParseException, InvalidGameTypeException {
        gameFileName = fileName;
        myFileHandler = new JSONFileReader(gameFileName);
        userIsPlayer1 = userID.equals("Player1");
        userTurn = userIsPlayer1;
        isPieceSelected = false;
        setPlayerInformation();
        gameType = createGameTypeFactory();
        myGame = new Game(gameType, myFileHandler.loadFileConfiguration(), myFileHandler.getNeighborhood(),
                myUserPlayerInformation, myAgentPlayerInformation, userIsPlayer1);

    }

    private GameTypeFactory createGameTypeFactory() throws IOException, ParseException, InvalidGameTypeException {
        String gameType = getStartingProperties().get("Gametype");
        int emptyState = Integer.parseInt(getStartingProperties().get("EmptyState"));

        return new GameFactory().createGameType(gameType,myUserPlayerInformation, myAgentPlayerInformation, emptyState);
    }

    private void setPlayerInformation() throws IOException, ParseException {
        if (userIsPlayer1) {
            myUserPlayerInformation = createPlayerInformationHolder(1);
            myAgentPlayerInformation = createPlayerInformationHolder(2);
        }
        else {
            myUserPlayerInformation = createPlayerInformationHolder(2);
            myAgentPlayerInformation = createPlayerInformationHolder(1);
        }
    }

    private PlayerInformationHolder createPlayerInformationHolder(int playerNum) throws IOException, ParseException {
        int playerState = Integer.parseInt(getStartingProperties().get("State"+playerNum));
        String playerImage = getStartingProperties().get("Image"+playerNum);
        int specialPlayerID = Integer.parseInt(getStartingProperties().get("SpecialState" + playerNum));
        String specialPlayerImage = getStartingProperties().get("SpecialImage"+playerNum);
        boolean playerPosDirection = Boolean.parseBoolean(getStartingProperties().get("Player1Direction"));
        if(playerNum != 1){
            playerPosDirection = !playerPosDirection;
        }
        return new PlayerInformationHolder(playerState,specialPlayerID,playerImage,specialPlayerImage,playerPosDirection);

    }

    public PlayerInformationHolder getUserInformation(){
        return myUserPlayerInformation;
    }

    public PlayerInformationHolder getAgentInformation(){
        return myAgentPlayerInformation;
    }

    public void restartGame() throws IOException, ParseException {
        myGame = new Game(gameType, myFileHandler.loadFileConfiguration(), myFileHandler.getNeighborhood(), myUserPlayerInformation,myAgentPlayerInformation, userIsPlayer1);
    }

    public boolean playerPass() {return myGame.didPlayerPass();}
    public boolean userTurn(){
        return userTurn;
    }

    public boolean doPiecesMove() throws IOException, ParseException {
        return  Boolean.parseBoolean(getStartingProperties().get("PiecesMove"));
    }

    @Override
    public void saveANewFile(String fileName, Map<String, String> startingProperties) throws IOException, ParseException {
        myFileHandler.saveToFile(fileName, startingProperties, myGame.getVisualInfo());
    }

    @Override
    public Map<String,String> getStartingProperties() throws IOException, ParseException {
        return myFileHandler.loadFileProperties();
    }

    @Override
    public void pieceSelected(int x, int y) {
        isPieceSelected = true;
        pieceSelectedX = x;
        pieceSelectedY = y;
    }

    @Override
    public void squareSelected(int x, int y) {
        squareSelectedX = x;
        squareSelectedY = y;
    }

    @Override
    public void playMove() throws InvalidMoveException {
        if (!isPieceSelected) {
            pieceSelected(squareSelectedX, squareSelectedY);
        }
        myGame.makeGameMove(new ArrayList<>(List.of(pieceSelectedX, pieceSelectedY, squareSelectedX, squareSelectedY)));
        isPieceSelected = false;
        userTurn = myGame.isUserTurn();
    }

    @Override
    public List<List<Integer>> getGameVisualInfo() {
        return myGame.getVisualInfo();
    }

    @Override
    public String getGameFileName() {
        return gameFileName;
    }


    @Override
    public boolean isGameOver() {
        return myGame.getEndGameStatus() > 0;
    }

    @Override
    public int gameWinner() {
        return myGame.getEndGameStatus();
    }

    public List<List<Integer>> getPossibleMovesForView() { return myGame.possibleMovesForView(); }


}
