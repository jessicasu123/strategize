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


    public Controller(String fileName, int userID, int agentID) throws IOException, ParseException {
        myFileHandler = new JSONFileReader(fileName);
//        String gameType = getStartingProperties.get("gameType");
        String gameType = "";
        // TODO: add in logic to set player id
        myUserPlayerID = userID;
        myAgentPlayerID = agentID;
        // TODO: change neighborhoods to be result of call to fileHandler. Neighborhood type should be specified in JSON file for each game.
        List<String> neighborhoods = new ArrayList<>(); //eventually change to call to fileHandler
        myGame = new Game(gameType, myFileHandler.loadFileConfiguration(), neighborhoods, myUserPlayerID, myAgentPlayerID);
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

    @Override
    public void playMove(int userID) throws InvalidMoveException {
        if (!isPieceSelected) {
            pieceSelected(squareSelectedX, squareSelectedY);
        }
        myGame.makeUserMove(userID, new ArrayList<>(List.of(pieceSelectedX, pieceSelectedY, squareSelectedX, squareSelectedY)));
        isPieceSelected = false;
    }


    @Override
    public void haveAgentMove(int agentID) throws InvalidMoveException {
        myGame.makeAgentMove(agentID);
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
