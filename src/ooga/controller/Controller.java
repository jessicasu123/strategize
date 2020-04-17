package ooga.controller;

import ooga.model.data.FileHandler;
import ooga.model.data.JSONFileReader;
import ooga.model.engine.*;
import ooga.model.engine.GameTypeFactory.GameFactory;
import ooga.model.engine.GameTypeFactory.GameTypeFactory;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    private int myUserPlayerID;
    private int myAgentPlayerID;
    private String myUserImage;
    private String myAgentImage;
    private String gameFileName;
    private boolean userIsPlayer1;
    private boolean userTurn;


    public Controller(String fileName, String userID, String opponent) throws IOException, ParseException, InvalidGameTypeException {
        gameFileName = fileName;
        myFileHandler = new JSONFileReader(gameFileName);
        userIsPlayer1 = userID.equals("Player1");
        userTurn = userIsPlayer1;
        isPieceSelected = false;
        setPlayerID();
        gameType = createGameTypeFactory();
        myGame = new Game(gameType, myFileHandler.loadFileConfiguration(), myFileHandler.getNeighborhood(),
                myUserPlayerID, myAgentPlayerID, userIsPlayer1);

    }

    private GameTypeFactory createGameTypeFactory() throws IOException, ParseException, InvalidGameTypeException {
        String gameType = getStartingProperties().get("Gametype");
        int specialPlayer1ID = Integer.parseInt(getStartingProperties().get("SpecialState" + 1));
        int specialPlayer2ID = Integer.parseInt(getStartingProperties().get("SpecialState" + 2));
        boolean player1PosDirection = Boolean.parseBoolean(getStartingProperties().get("Player1Direction"));
        int emptyState = Integer.parseInt(getStartingProperties().get("EmptyState"));
        int specialUserID;
        int specialAgentID;
        boolean userPosDirection;
        if(userIsPlayer1){
            specialUserID = specialPlayer1ID;
            specialAgentID = specialPlayer2ID;
            userPosDirection = player1PosDirection;
        }else{
            specialUserID = specialPlayer2ID;
            specialAgentID = specialPlayer1ID;
            userPosDirection = !player1PosDirection;
        }
        return new GameFactory().createGameType(gameType,myUserPlayerID, myAgentPlayerID, specialUserID,
                specialAgentID, userPosDirection, emptyState);

    }

    //TODO: fix
    private void setPlayerID() throws IOException, ParseException {
        int player1State = Integer.parseInt(getStartingProperties().get("State"+Integer.toString(1)));
        int player2State = Integer.parseInt(getStartingProperties().get("State"+Integer.toString(2)));
        String player1Image = getStartingProperties().get("Image"+Integer.toString(1));
        String player2Image = getStartingProperties().get("Image"+Integer.toString(2));

        if (userIsPlayer1) {
            assignPlayerIDAndImages(player1State, player2State, player1Image, player2Image);
        }
        else {
            assignPlayerIDAndImages(player2State, player1State, player2Image, player1Image);
        }
    }

    private void assignPlayerIDAndImages(int userState, int agentState, String userImage, String agentImage) {
        myUserPlayerID = userState;
        myAgentPlayerID = agentState;
        myUserImage = userImage;
        myAgentImage = agentImage;
    }

    public void restartGame() throws IOException, ParseException {
        myGame = new Game(gameType, myFileHandler.loadFileConfiguration(), myFileHandler.getNeighborhood(), myUserPlayerID, myAgentPlayerID, userIsPlayer1);
    }

    public boolean playerPass() {return myGame.didPlayerPass();}
    public boolean userTurn(){
        return userTurn;
    }
    //TODO: either keep this here or have JSON file reader attach each attribute (ex. image, color, etc.) to the STATE
    public String getUserImage() {
        return myUserImage;
    }

    //TODO: either keep this here or have JSON file reader attach each attribute (ex. image, color, etc.) to the STATE
    public String getAgentImage() {
        return myAgentImage;
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
    public int getUserNumber() {
        return myUserPlayerID;
    }

    @Override
    public int getAgentNumber() {return myAgentPlayerID;}

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
