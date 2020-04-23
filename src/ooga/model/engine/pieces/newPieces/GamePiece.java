package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.MoveChecks.MoveCheck;
import ooga.model.engine.pieces.newPieces.MoveTypes.MoveType;

import java.util.ArrayList;
import java.util.List;

public class GamePiece {
    public static final int DIRECTION = 1;
    private int myState;
    private Coordinate myPosition;
    private int myDirection;
    private List<MoveCheck> myMoveChecks;
    private List<MoveType> myMoveTypes;
    private boolean turnChange;

    public GamePiece(int state, Coordinate position, boolean posDirection, List<MoveCheck> checks, List<MoveType> moveTypes){
        myState = state;
        myPosition = position;
        if(posDirection){
            myDirection = DIRECTION;
        }else{
            myDirection = -DIRECTION;
        }
        myMoveChecks = checks;
        myMoveTypes = moveTypes;
    }
    //TODO: fix later
    public List<Coordinate> calculateAllPossibleMoves(List<GamePiece> neighbors, int playerID){
        List<Coordinate> possibleMoves = new ArrayList<>();


        return possibleMoves;
    }

    public void makeMove(Coordinate endCoordinateInfo, List<GamePiece> neighbors, int newState){
        for(MoveType move: myMoveTypes){
            move.completeMoveType(this, endCoordinateInfo, neighbors, newState);
        }
    }

    public int getState(){
        return myState;
    }

    public boolean changeTurnAfterMove(){
        return turnChange;
    }

    public int getVisualRepresentation(){
        return myState;
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

    protected void move(Coordinate moveTo){
        myPosition = moveTo;
    }
}
