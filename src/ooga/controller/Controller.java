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
    private String myUserImage;
    private String myAgentImage;



    public Controller(String fileName, String userID, String opponent) throws IOException, ParseException {
        myFileHandler = new JSONFileReader(fileName);
        isPieceSelected = false;
        setPlayerID(userID);
        String gameType = getStartingProperties().get("Gametype");
        myGame = new Game(gameType, myFileHandler.loadFileConfiguration(), myFileHandler.getNeighborhood(), myUserPlayerID, myAgentPlayerID);
    }

    //TODO: fix
    private void setPlayerID(String userID) {
        int player1State = 0;
        int player2State = 0;
        String player1Image = "";
        String player2Image = "";
        try {
            player1State = Integer.parseInt(getStartingProperties().get("State"+Integer.toString(1)));
            player2State = Integer.parseInt(getStartingProperties().get("State"+Integer.toString(2)));
            player1Image = getStartingProperties().get("Image"+Integer.toString(1));
            player2Image = getStartingProperties().get("Image"+Integer.toString(2));
            Integer.parseInt(getStartingProperties().get("State"+Integer.toString(1)));
        } catch (IOException | ParseException e) {
            System.out.println(e.getMessage());
        }
        if (userID.equals("Player1")) {
            myUserPlayerID = player1State;
            myAgentPlayerID = player2State;
            myUserImage = player1Image;
            myAgentImage = player2Image;
        }
        else {
            myUserPlayerID = player2State;
            myAgentPlayerID = player1State;
            myUserImage = player2Image;
            myAgentImage = player1Image;
        }
    }

    public String getUserImage() {
        return myUserImage;
    }

    public String getAgentImage() {
        return myAgentImage;
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


}
