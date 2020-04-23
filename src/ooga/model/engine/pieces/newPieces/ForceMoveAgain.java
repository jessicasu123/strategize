package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.NeighborhoodConverters.FindNeighborsUntilNoObjects;

import java.util.List;

public class ForceMoveAgain implements MoveType{
    private FindNeighborsUntilNoObjects myPiecesChanged;
    private boolean turnChange;
    private List<Integer> myPlayerStates;
    public ForceMoveAgain(List<Integer> statesToIgnore, List<Integer> playerStates){
        myPiecesChanged = new FindNeighborsUntilNoObjects(statesToIgnore);
        turnChange = true;
        myPlayerStates = playerStates;
    }
    @Override
    public void completeMoveType(GamePiece moving, Coordinate endCoordinateInfo, List<GamePiece> neighbors,
                                 int playerState, int direction) {
        turnChange = true;
        List<GamePiece> piecesChanged = myPiecesChanged.findNeighborsToConvert(moving.getPosition(),endCoordinateInfo,
                moving.getNumObjects(),playerState,direction,neighbors);
        if(myPlayerStates.contains(piecesChanged.get(piecesChanged.size() - 1).getState())){
            moving.changeTurn(false);
        }else{
            moving.changeTurn(true);
        }

    }

    @Override
    public boolean addOppositeDirection() {
        return false;
    }


}
