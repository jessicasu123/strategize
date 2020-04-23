package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.MoveChecks.MoveCheck;

import java.util.ArrayList;
import java.util.List;

public class GamePiece {
    public static final int MAIN_DIRECTION_INDEX = 0;
    private int myState;
    private Coordinate myPosition;
    private List<Integer> myDirections;
    private int myMainDirection;
    private int myObjects;
    private List<MoveCheck> myMoveChecks;
    private List<MoveCheck> myNeighborMoveChecks;
    private List<MoveType> myMoveTypes;
    private boolean turnChange;

    public GamePiece(int state, Coordinate position, List<Integer> directions, int numObjects, List<MoveCheck> selfMoveChecks,
                     List<MoveCheck> neighborMoveChecks, List<MoveType> moveTypes){
        myState = state;
        myPosition = position;
        myDirections = directions;
        myMainDirection = myDirections.get(MAIN_DIRECTION_INDEX);
        myMoveChecks = selfMoveChecks;
        myNeighborMoveChecks = neighborMoveChecks;
        myMoveTypes = moveTypes;
        myObjects = numObjects;
    }

    public List<Coordinate> calculateAllPossibleMoves(List<GamePiece> neighbors, int playerID){
        List<Coordinate> possibleMoves = new ArrayList<>();
        boolean selfConditionsMet = checkSelfConditions(neighbors, playerID);
        if (selfConditionsMet) {
            if (myNeighborMoveChecks.size() > 0) {
                checkNeighborConditions(neighbors, possibleMoves, playerID);
            } else {
                possibleMoves.add(myPosition);
            }
        }
        return possibleMoves;
    }

    private boolean checkSelfConditions(List<GamePiece> neighbors, int playerID) {
        for (MoveCheck check : myMoveChecks) {
            if (!check.isConditionMet(myPosition, this, neighbors, playerID, myDirections)) {
                return false;
            }
        }
        return true;
    }

    private void checkNeighborConditions(List<GamePiece> neighbors, List<Coordinate> possibleMoves, int playerID) {
        for (GamePiece neighbor : neighbors) {
            boolean neighborConditionsMet = true;
            for (MoveCheck check : myNeighborMoveChecks) {
                // TODO: for state, should this be taking neighbor state or myState or playerID?
                if (!check.isConditionMet(myPosition, neighbor, neighbors, playerID, myDirections)) {
                    neighborConditionsMet = false;
                    break;
                }
            }
            if (neighborConditionsMet) {
                possibleMoves.add(neighbor.getPosition());
            }
        }
    }

    public void makeMove(Coordinate endCoordinateInfo, List<GamePiece> neighbors, int newState){
        for(MoveType move: myMoveTypes){
            move.completeMoveType(this, endCoordinateInfo, neighbors, newState, myMainDirection);
            if(move.addOppositeDirection() && !myDirections.contains(-myMainDirection)){
                myDirections.add(-myMainDirection);
            }
        }
    }

    public int getState(){
        return myState;
    }

    public boolean changeTurnAfterMove(){
        return turnChange;
    }

    public int getNumObjects(){
        return myObjects;
    }

    public Coordinate getPosition(){
        return myPosition;
    }

    protected void changeTurn(boolean changeTurns){
        turnChange = changeTurns;
    }
    
    protected void changeState(int newState){
        myState = newState;
    }

    protected void incrementNumObjects(int incrementBy){
        myObjects += incrementBy;
    }

    protected void move(Coordinate moveTo){
        myPosition = moveTo;
    }
}
