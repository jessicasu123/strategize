package ooga.model.engine.Player;

import ooga.model.engine.pieces.newPieces.MoveChecks.MoveCheck;
import ooga.model.engine.pieces.newPieces.MoveType;

import java.util.List;

public class Player {
    private List<Integer> myPlayerStates;
    private List<Integer> myDirections;
    private List<MoveCheck> myMoveChecks;
    private List<MoveCheck> myNeighborMoveChecks;
    private List<MoveType> myMoveTypes;
    private boolean isPlayer1;

    public Player(List<Integer> states, List<Integer> directions,
                  List<MoveCheck> selfMoveChecks, List<MoveCheck> neighborMoveChecks,
                  List<MoveType> moveTypes, boolean isPlayer1){
        myPlayerStates = states;
        myDirections = directions;
        myMoveChecks = selfMoveChecks;
        myNeighborMoveChecks = neighborMoveChecks;
        myMoveTypes = moveTypes;
        this.isPlayer1 = isPlayer1;
    }

    public List<Integer> getPlayerStates() {
        return myPlayerStates;
    }
    public List<Integer> getDirections() {
        return myDirections;
    }

    public List<MoveCheck> getMoveChecks() {
        return myMoveChecks;
    }

    public List<MoveCheck> getNeighborMoveChecks() {
        return myNeighborMoveChecks;
    }

    public List<MoveType> getMoveTypes() {
        return myMoveTypes;
    }

    public boolean isPlayer1(){
        return isPlayer1;
    }
}
