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

    //TODO: figure out parameter, based on FileHandler
    public MockController(FileHandler fileHandle){
        myFileHandler = fileHandle;
        myGame = new MockGame("", myFileHandler.loadFileConfiguration());
    }

    //TODO: would these by private helper methods called in the constructor?
    @Override
    public List<List<Integer>> getStartingStateConfiguaration() {
        return null;
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

    //TODO: figure out when this should be called
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
}
