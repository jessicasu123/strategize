package ooga.model.engine.player;

import ooga.model.engine.pieces.moveChecks.MoveCheck;
import ooga.model.engine.pieces.MoveType;

import java.util.Collections;
import java.util.List;

/**
 * This class acts to hold all of the information associated with a player
 * @author all
 */
public class PlayerInfoHolder {
    private List<Integer> myPlayerStates;
    private List<Integer> myDirections;
    private List<MoveCheck> myMoveChecks;
    private List<MoveCheck> myNeighborMoveChecks;
    private List<MoveType> myMoveTypes;
    private boolean isPlayer1;

    /**
     * @param states - the states of this player
     * @param directions - the directions that this player has
     * @param selfMoveChecks - the move checks that this player performs on their own pieces
     * @param neighborMoveChecks - the move checks that this player performs on their neighbors
     * @param moveTypes - the move types this players pieces have
     * @param isPlayer1 - if this player is player 1 or not
     */
    public PlayerInfoHolder(List<Integer> states, List<Integer> directions,
                            List<MoveCheck> selfMoveChecks, List<MoveCheck> neighborMoveChecks,
                            List<MoveType> moveTypes, boolean isPlayer1){
        myPlayerStates = states;
        myDirections = directions;
        myMoveChecks = selfMoveChecks;
        myNeighborMoveChecks = neighborMoveChecks;
        myMoveTypes = moveTypes;
        this.isPlayer1 = isPlayer1;
    }

    /**
     * @return an unmodifiable list of the player states
     */
    public List<Integer> getPlayerStates() {
        return myPlayerStates;
    }

    /**
     * @return a list of all of the directions
     * (these can change so it isn't unmodifiable)
     */
    public List<Integer> getDirections() {
        return myDirections;
    }

    /**
     * @return an unmodifiable list of the self move checks
     */
    public List<MoveCheck> getMoveChecks() {
        return myMoveChecks;
    }

    /**
     * @return an unmodifiable list of the neighbor move checks
     */
    public List<MoveCheck> getNeighborMoveChecks() {
        return myNeighborMoveChecks;
    }

    /**
     * @return an unmodifiable list of the move types
     */
    public List<MoveType> getMoveTypes() {
        return myMoveTypes;
    }

    /**
     * @return if this player is player1
     */
    public boolean isPlayer1(){
        return isPlayer1;
    }
}
