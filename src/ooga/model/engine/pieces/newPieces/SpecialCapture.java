package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.ConvertibleNeighborFinder;

import java.util.List;

public class SpecialCapture implements MoveType {
    public static final int TO_ADD = 1;
    private ConvertibleNeighborFinder myPiecesChanged;
    private List<Integer> myPlayerStates;

    public SpecialCapture(List<Integer> playerStates, ConvertibleNeighborFinder finder){
        myPiecesChanged = finder;
        myPlayerStates = playerStates;
    }

    @Override
    public void completeMoveType(GamePiece moving, Coordinate endCoordinateInfo, List<GamePiece> neighbors,
                                 int playerState, int direction) {
        List<GamePiece> piecesChanged = myPiecesChanged.findNeighborsToConvert(moving.getPosition(),endCoordinateInfo,
                moving.getNumObjects(),playerState,direction,neighbors);
        GamePiece lastPiece = piecesChanged.get(piecesChanged.size() - 1);
        if(lastPiece.getState() == playerState && lastPiece.getNumObjects() == 1){
            performSpecialCapture(neighbors,lastPiece.getPosition().getCol(), playerState);
            lastPiece.incrementNumObjects(-lastPiece.getNumObjects());
        }
    }

    private void performSpecialCapture(List<GamePiece> neighbors, int currYPos, int playerState) {
        for(GamePiece otherPiece: neighbors){
            if(otherPiece.getPosition().getCol() == currYPos && !myPlayerStates.contains(otherPiece.getState())){
                for(GamePiece myPiece: neighbors){
                    if(myPlayerStates.contains(myPiece.getState()) && myPiece.getState() != playerState){
                        myPiece.incrementNumObjects(TO_ADD + otherPiece.getNumObjects());
                        otherPiece.incrementNumObjects(-otherPiece.getNumObjects());
                        break;
                    }
                }
            }
        }
    }
}
