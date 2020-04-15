package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class ConnectFourGamePiece extends GamePiece {

    public ConnectFourGamePiece(int state, Coordinate position) {
        super(state, position);
    }

    @Override
    public List<Coordinate> calculateAllPossibleMoves(List<GamePiece> neighbors) {
        List<Coordinate> possibleMoves = new ArrayList<>();
        if (this.getState() == 0 && checkBelow(neighbors)) {
            possibleMoves.add(this.getPosition());
        }
        return possibleMoves;
    }

    @Override
    public void makeMove(Coordinate endCoordinateInfo, List<GamePiece> neighbors, int newState) {
        this.move(endCoordinateInfo);
        this.changeState(newState);
    }

    private boolean checkBelow(List<GamePiece> neighbors){
        for(GamePiece neighbor: neighbors){
            if(neighbor.getPosition().getYCoord() == this.getPosition().getYCoord()-1 && neighbor.getPosition().getXCoord() == this.getPosition().getXCoord()){
                return true;
            }
        }
        return (this.getPosition().getYCoord()==0);
    }
}
