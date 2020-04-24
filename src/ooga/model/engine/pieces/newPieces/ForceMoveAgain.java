package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.ConvertibleNeighborFinder;

import java.util.List;

public class ForceMoveAgain implements MoveType{
    private ConvertibleNeighborFinder myPiecesChangedFinder;
    private List<Integer> myPlayerStates;

    public ForceMoveAgain(List<Integer> playerStates, ConvertibleNeighborFinder finder){
        myPiecesChangedFinder = finder;
        myPlayerStates = playerStates;
    }

    @Override
    public void completeMoveType(GamePiece moving, Coordinate endCoordinateInfo, List<GamePiece> neighbors,
                                 int playerState, int direction) {
        List<GamePiece> piecesChanged = myPiecesChangedFinder.findNeighborsToConvert(moving.getPosition(),endCoordinateInfo,
                moving.getNumObjects(),playerState,direction,neighbors);
        if(myPlayerStates.contains(piecesChanged.get(piecesChanged.size() - 1).getState())){
            moving.changeTurn(false);
        }else{
            moving.changeTurn(true);
        }
    }




}
