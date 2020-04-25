package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.convertibleNeighborFinder.ConvertibleNeighborFinder;

import java.util.List;

/**
 * This class determines if based on the move the turn should or shouldn't change
 * The turn should not change when the last neighbor to be converted is a
 * special state (not the id state) for this player
 * @author Holly Ansel
 */
public class ForceMoveAgainMove implements MoveType{
    private ConvertibleNeighborFinder myPiecesChangedFinder;
    private List<Integer> myPlayerStates;

    /**
     * @param playerStates - all the states of the player whose move type this is
     * @param finder - the convertible neighbor finder that was given to find the neighbors
     */
    public ForceMoveAgainMove(List<Integer> playerStates, ConvertibleNeighborFinder finder){
        myPiecesChangedFinder = finder;
        myPlayerStates = playerStates;
    }

    /**
     * a move again (change turns being false) occurs when the last of the convertible neighbors belongs
     * to your special state
     * @param moving - the game piece that is being moved
     * @param endCoordinateInfo - the end coordinate of where that piece is being moved to
     * @param neighbors - the neighbors of the game piece being moved
     * @param playerState - the player who is moving the piece
     * @param direction - the direction this piece is being moved in
     */
    @Override
    public void completeMoveType(GamePiece moving, Coordinate endCoordinateInfo, List<GamePiece> neighbors,
                                 int playerState, int direction) {
        List<GamePiece> piecesChanged = myPiecesChangedFinder.findNeighborsToConvert(moving.getPosition(),endCoordinateInfo,
                moving.getNumObjects(),playerState,direction,neighbors);
        int stateLandedIn = piecesChanged.get(piecesChanged.size() - 1).getState();
        if(myPlayerStates.contains(stateLandedIn) && stateLandedIn != playerState){
            moving.changeTurn(false);
        }else{
            moving.changeTurn(true);
        }
    }




}
