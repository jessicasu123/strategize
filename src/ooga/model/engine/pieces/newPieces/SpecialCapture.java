package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.ConvertibleNeighborFinder;

import java.util.List;
/**
 * This class determines if the move is a special capture, and if so acts accordingly
 *
 * A special capture is when the last neighbor converted only contains the
 * object that you added when converting and the state of the last neighbor is your player id
 *
 * When a special capture's criteria are met, all of the objects of the last piece converted as
 * well as those who are in the same column but of the opponent players state are added to this players
 * special (non-id) state
 *
 * @author Holly Ansel
 */
public class SpecialCapture implements MoveType {
    public static final int TO_ADD = 1;
    private ConvertibleNeighborFinder myPiecesChanged;
    private List<Integer> myPlayerStates;

    /**
     * @param playerStates - all of the states of the player whose move type this is
     * @param finder - the convertible neighbor finder that was given to find the neighbors
     */
    public SpecialCapture(List<Integer> playerStates, ConvertibleNeighborFinder finder){
        myPiecesChanged = finder;
        myPlayerStates = playerStates;
    }

    /**
     * Performs a special capture if the last piece converted only has the object you placed in it and is of your
     * player state
     * A special capture involves adding all of the objects of the last piece converted and opponents pieces in
     * the same column to the special state
     * and then clearing the objects of the last piece converted and opponent piece
     * @param moving - the game piece that is being moved
     * @param endCoordinateInfo - the end coordinate of where that piece is being moved to
     * @param neighbors - the neighbors of the game piece being moved
     * @param playerState - the player who is moving the piece
     * @param direction - the direction this piece is being moved in
     */
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
