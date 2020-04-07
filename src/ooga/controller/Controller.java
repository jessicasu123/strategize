package ooga.controller;

import ooga.model.data.FileHandler;
import ooga.model.engine.Game;
import ooga.model.engine.GameFramework;
import ooga.model.engine.InvalidMoveException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Controller implements ControllerFramework {
    GameFramework myGame;
    FileHandler myFileHandler;
    int pieceSelectedX;
    int pieceSelectedY;
    int squareSelectedX;
    int squareSelectedY;


    public Controller(String gameType, FileHandler fileHandle) throws IOException {
        myFileHandler = fileHandle;
        myGame = new Game(gameType, myFileHandler.loadFileConfiguration());
    }


    @Override
    public void saveANewFile(String fileName, Map<String, String> properties) {
        myFileHandler.saveToFile(fileName, properties, myGame.getVisualInfo());
    }

    @Override
    public Map<String,String> getStartingProperties() {
        return myFileHandler.loadFileProperties();
    }

    @Override
    public void pieceSelected(int x, int y) {
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
        // TODO: may be useful to add a getter for myUserPlayerID and my AgentPlayerID in Game
        myGame.makeUserMove(userID, new ArrayList<>(List.of(pieceSelectedX, pieceSelectedY, squareSelectedX, squareSelectedY)));
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
        return 0; //TODO - should this be provided by game once its assigned?
    }

    @Override
    public boolean gameOver() {
        return myGame.getEndGameStatus() != 0;
    }

    @Override
    public int gameWinner() {
        return myGame.getEndGameStatus();
    }


}
