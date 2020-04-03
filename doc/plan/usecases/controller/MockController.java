package ooga.controller;

import ooga.model.data.FileHandler;
import ooga.model.engine.GameFramework;
import ooga.model.engine.InvalidMoveException;
import ooga.model.engine.MockGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MockController implements ControllerFramework {
    GameFramework myGame;
    FileHandler myFileHandler;
    int pieceSelectedX;
    int pieceSelectedY;
    int squareSelectedX;
    int squareSelectedY;


    public MockController(FileHandler fileHandle){
        myFileHandler = fileHandle;
        myGame = new MockGame("", myFileHandler.loadFileConfiguration());
    }


    @Override
    public void saveANewFile(String fileName, Map<String, String> properties) {
        myFileHandler.saveToFile(fileName,properties,myGame.getVisualInfo());
    }

    @Override
    public Map<String,String> getStartingProperties() {
        return null;
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
    public void playMove() throws InvalidMoveException {
        myGame.makeUserMove(new ArrayList<>(List.of(pieceSelectedX, pieceSelectedY, squareSelectedX, squareSelectedY)));
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
    public int getUserNumber() {
        return 0;
    }

    @Override
    public boolean gameOver() {
        return false;
    }

    @Override
    public int gameWinner() {
        return 0;
    }


}
