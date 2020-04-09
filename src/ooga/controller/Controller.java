package ooga.controller;

import ooga.model.data.FileHandler;
import ooga.model.data.JSONFileReader;
import ooga.model.engine.Game;
import ooga.model.engine.GameFramework;
import ooga.model.engine.InvalidMoveException;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class Controller implements ControllerFramework {
    private GameFramework myGame;
    private FileHandler myFileHandler;
    private Boolean isPieceSelected;
    private int pieceSelectedX;
    private int pieceSelectedY;
    private int squareSelectedX;
    private int squareSelectedY;
    private int myUserPlayerID;
    private int myAgentPlayerID;



    public Controller(String fileName, String userID, String opponent) throws IOException, ParseException {
        myFileHandler = new JSONFileReader(fileName);
        isPieceSelected = false;
        setPlayerID(userID);
        String gameType = "Tic-Tac-Toe"; //getStartingProperties().get("GameType");
        // TODO: change neighborhoods to be result of call to fileHandler. Neighborhood type should be specified in JSON file for each game.
        List<String> neighborhoods = new ArrayList<>(); //eventually change to call to fileHandler
        myGame = new Game(gameType, createTestConfig(), neighborhoods, myUserPlayerID, myAgentPlayerID);
    }

    //TODO: remove once JSON handling works
    public List<List<Integer>> createTestConfig() {
        Integer[][] startingConfig = {
                {0,0,0},
                {0,0,0},
                {0,0,0}
        };
        List<List<Integer>> config = new ArrayList<>();
        for (int i = 0; i < 3;i++) {
            config.add(Arrays.asList(startingConfig[i]));
        }
        return config;
    }

    private void setPlayerID(String userID) {
        if (userID.equals("Player1")) {
            myUserPlayerID = 1;
            myAgentPlayerID = 2;
        }
        else {
            myUserPlayerID = 2;
            myAgentPlayerID = 1;
        }
    }

    @Override
    public void saveANewFile(String fileName, Map<String, String> properties) {
        myFileHandler.saveToFile(fileName, properties, myGame.getVisualInfo());
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

    //TODO: from Jessica - I removed the playerID from Game.makeUserMove because the Game already has instance var for the userID
    //not sure if you want to also remove the userID parameter?
    @Override
    public void playMove(int userID) throws InvalidMoveException {
        if (!isPieceSelected) {
            pieceSelected(squareSelectedX, squareSelectedY);
        }
        myGame.makeUserMove(new ArrayList<>(List.of(pieceSelectedX, pieceSelectedY, squareSelectedX, squareSelectedY)));
        isPieceSelected = false;
    }


    //TODO: from Jessica - I removed the agentID from Game.makeAgentMove because the Game already has instance var for the agentID
    //not sure if you want to also remove the agentID parameter?
    @Override
    public void haveAgentMove(int agentID) throws InvalidMoveException {
        myGame.makeAgentMove();
    }

    @Override
    public List<List<Integer>> getGameVisualInfo() {
        return myGame.getVisualInfo();
    }


    @Override
    public int getUserNumber() {
        return myUserPlayerID;
    }

    @Override
    public boolean gameOver() {
        return myGame.getEndGameStatus() > 0;
    }

    @Override
    public int gameWinner() {
        return myGame.getEndGameStatus();
    }


}
