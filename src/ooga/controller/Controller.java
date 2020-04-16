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


    public Controller(String fileName, String userID, String opponent) throws IOException, ParseException, InvalidGameTypeException {
        gameFileName = fileName;
        myFileHandler = new JSONFileReader(gameFileName);
        isPieceSelected = false;
        setPlayerID(userID);
        gameType = createGameTypeFactory();
        myGame = new Game(gameType, myFileHandler.loadFileConfiguration(), myFileHandler.getNeighborhood(), myUserPlayerID, myAgentPlayerID);

    }

    private GameTypeFactory createGameTypeFactory() throws IOException, ParseException, InvalidGameTypeException {
        String gameType = getStartingProperties().get("Gametype");
        int specialPlayer1ID = Integer.parseInt(getStartingProperties().get("SpecialState"+1));
        int specialPlayer2ID = Integer.parseInt(getStartingProperties().get("SpecialState"+2));
        boolean player1PosDirection = Boolean.parseBoolean(getStartingProperties().get("Player1Direction"));
        int emptyState = Integer.parseInt(getStartingProperties().get("EmptyState"));
        return new GameFactory().createGameType(gameType,myUserPlayerID, myAgentPlayerID, specialPlayer1ID,
                specialPlayer2ID, player1PosDirection, emptyState);

    }

    //TODO: fix
    private void setPlayerID(String userID) throws IOException, ParseException {
        int player1State = Integer.parseInt(getStartingProperties().get("State"+Integer.toString(1)));
        int player2State = Integer.parseInt(getStartingProperties().get("State"+Integer.toString(2)));
        String player1Image = getStartingProperties().get("Image"+Integer.toString(1));
        String player2Image = getStartingProperties().get("Image"+Integer.toString(2));

        if (userID.equals("Player1")) {
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
        myGame = new Game(gameType, myFileHandler.loadFileConfiguration(), myFileHandler.getNeighborhood(), myUserPlayerID, myAgentPlayerID);
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
        myGame.makeUserMove(new ArrayList<>(List.of(pieceSelectedX, pieceSelectedY, squareSelectedX, squareSelectedY)));
        isPieceSelected = false;
    }


    @Override
    public void haveAgentMove() throws InvalidMoveException {
        myGame.makeAgentMove();
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
