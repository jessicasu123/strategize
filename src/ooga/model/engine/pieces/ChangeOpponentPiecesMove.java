package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.convertibleNeighborFinder.ConvertibleNeighborFinder;

import java.util.Collection;
import java.util.List;

/**
 * CODE MASTERPIECE (PT 1):
 * My code masterpiece centers around the feature of creating a move where the opponent's
 * pieces are turned to a different state. This involves the interaction of multiple classes that are each pretty short,
 * so the total of all these 4 classes does not exceed 300 lines.
 * ChangeOpponentPiecesMove demonstrates the principles of communication, modularity, and polymorphism.
 * - COMMUNICATION
 *      - all names are meaningful and clearly demonstrate the class's purpose (changing opponent pieces)
 *      - methods are concise and streamlined
 *  - MODULARITY / SINGLE RESPONSIBILITY
 *      - all instance field variables are private
 *      - this class is solely responsible for CHANGING opponent pieces, and it delegates responsibilities on LINE 67
 *      to a different object (ConvertibleNeighborFinder) to FIND which neighbors actually need to be changed
 *          - the ConvertibleNeighborFinder interface and factory is included to demonstrate its design as well
 *  - POLYMORPHISM / FLEXIBILITY
 *      - this class does not need to know what KIND of ConvertibleNeighborFinder it is acting on; it can simply
 *      call the findNeighborsToConvert which all classes that implement ConvertibleNeighborFinder will have
 *      - therefore, this class is also flexible because it can support ANY kind of ConvertibleNeighborFinder and can
 *      change opponent pieces for a wide range of neighbors determined by various game rules
 *       - on a larger scale, this class itself demonstrates polymorphism because it implements the MoveType interface.
 *       Therefore, for any class that calls a MoveType (ex. GamePiece), the class does NOT need to know what
 *       specific MoveType it is acting on; a game JSON configuration file can simply specify one of the MoveTypes as
 *       "ChangeOpponentPiecesMove". The design, then, becomes very DATA-DRIVEN.
 */

/**
 * This class is responsible for changing the state of a series of
 * opponents/neighbors, whether it's to the empty state or to the current player state.
 * The neighbors are decided by a ConvertibleNeighborFinder object and passed
 * in as a parameter.
 *
 * @author Jessica Su
 */
public class ChangeOpponentPiecesMove implements MoveType {
    private ConvertibleNeighborFinder myConvertibleNeighborFinder;
    private int myEmptyState;
    private boolean convertToEmptyState;

    /**
     * @param convertibleNeighborFinder - finds all the neighbors that need to be converted
     * @param convertToEmptyState - boolean to decide whether to convert to the empty state or not
     * @param emptyState - the integer that specifies the empty state
     */
    public ChangeOpponentPiecesMove(ConvertibleNeighborFinder convertibleNeighborFinder, boolean convertToEmptyState, int emptyState) {
        myEmptyState = emptyState;
        this.convertToEmptyState = convertToEmptyState;
        myConvertibleNeighborFinder = convertibleNeighborFinder;
    }

    /**
     * Converts all the neighbors provided by neighborhood converter finder
     * (based on the neighborhood converter type) to a specified new state.
     *
     * @param moving - the current piece that is being considered
     * @param endCoordinateInfo - the end coordinate of where the piece is potentially moving to
     * @param neighbors - the list of neighbors
     * @param playerState - the current player ID
     * @param direction - the direction of the player
     */
    @Override
    public void completeMoveType(GamePiece moving, Coordinate endCoordinateInfo, List<GamePiece> neighbors, int playerState, int direction) {
        List<GamePiece> neighborsToConvert = myConvertibleNeighborFinder.findNeighborsToConvert(moving.getPosition(), endCoordinateInfo,
                moving.getNumObjects(), playerState,direction, neighbors);
        for (GamePiece neighbor: neighborsToConvert) {
            if(convertToEmptyState){
                neighbor.changeState(myEmptyState);
            }else{
                neighbor.changeState(playerState);
            }
        }
    }
}
