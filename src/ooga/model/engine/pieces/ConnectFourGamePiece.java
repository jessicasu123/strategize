package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class ConnectFourGamePiece extends GamePiece {

    private int ROWS = 5;
    private int myPlayerID;

    public ConnectFourGamePiece(int state, Coordinate position) {
        super(state, position);
    }


    @Override
    public List<Coordinate> calculateAllPossibleMoves(List<GamePiece> neighbors, int playerID) {
        myPlayerID = playerID;
        List<Coordinate> possibleMoves = new ArrayList<>();
        if (this.getState() == 0 && checkBelow(neighbors)) {
            System.out.println(this.getPosition());
            possibleMoves.add(this.getPosition());
        }
        return possibleMoves;
    }

    //TODO: replace number with DIM-1
    private boolean checkBelow(List<GamePiece> neighbors){
        if(!(this.getPosition().getXCoord()==ROWS)) {
            for (GamePiece neighbor : neighbors) {
                if (neighbor.getPosition().getXCoord()-1 == this.getPosition().getXCoord()  && neighbor.getPosition().getYCoord() == this.getPosition().getYCoord()) {
                    if (neighbor.getState()!=0) return true;
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public void makeMove(Coordinate endCoordinateInfo, List<GamePiece> neighbors, int newState) {
        this.move(endCoordinateInfo);
        this.changeState(newState);
    }

}
