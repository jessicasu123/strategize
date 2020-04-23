package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.MoveChecks.MoveCheck;

import java.util.ArrayList;
import java.util.List;

public class GamePiece {
    private int myState;
    private Coordinate myPosition;
    private int myDirection;
    private int myObjects;
    private List<MoveCheck> myMoveChecks;
    private List<MoveType> myMoveTypes;
    private boolean turnChange;

    public GamePiece(int state, Coordinate position, int direction, int numObjects, List<MoveCheck> checks, List<MoveType> moveTypes){
        myState = state;
        myPosition = position;
        myDirection = direction;
        myMoveChecks = checks;
        myMoveTypes = moveTypes;
        myObjects = numObjects;
    }
    //TODO: fix later
    public List<Coordinate> calculateAllPossibleMoves(List<GamePiece> neighbors, int playerID){
        List<Coordinate> possibleMoves = new ArrayList<>();


        return possibleMoves;
    }

    public void makeMove(Coordinate endCoordinateInfo, List<GamePiece> neighbors, int newState){
        for(MoveType move: myMoveTypes){
            move.completeMoveType(this, endCoordinateInfo, neighbors, newState, myDirection);
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
