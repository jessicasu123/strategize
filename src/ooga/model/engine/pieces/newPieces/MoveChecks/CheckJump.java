package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.FindNeighborsBetweenCoordinates;

import java.util.List;

public class CheckJump implements MoveCheck {
    public static final int SQUARE = 2;
    private FindNeighborsBetweenCoordinates myPiecesInBetween;
    private int myEmptyState;
    private List<Integer> myPlayerStates;

    public CheckJump(int emptyState, List<Integer> playerStates){
        myPiecesInBetween = new FindNeighborsBetweenCoordinates();
        myEmptyState = emptyState;
        myPlayerStates = playerStates;
    }


    @Override
    public boolean isConditionMet(Coordinate startingLocation, GamePiece checking, List<GamePiece> neighbors,
                                  int state, List<Integer> directions) {
        boolean patternMet = true;
        List<GamePiece> piecesBetween = myPiecesInBetween.findNeighborsToConvert(startingLocation,
                checking.getPosition(),checking.getNumObjects(),state,directions.get(0),neighbors);
        Coordinate startCheckLocation = findStartingLocation(piecesBetween,startingLocation,checking);
        int numPiecesToCheck = piecesBetween.size();
        if(numPiecesToCheck % 2 == 0){
            return false;
        }
        for(int i = 0; i < numPiecesToCheck;i++){
            boolean patternMetSoFar = pathMeetsPattern(piecesBetween,startCheckLocation,checking,i);
            patternMet = patternMet && patternMetSoFar;
            piecesBetween = myPiecesInBetween.findNeighborsToConvert(startCheckLocation,checking.getPosition(),
                    checking.getNumObjects(), state,directions.get(0),piecesBetween);
            startCheckLocation = findStartingLocation(piecesBetween,startCheckLocation,checking);
        }

        return patternMet && checkDirection(directions,startingLocation,checking.getPosition(), numPiecesToCheck)
                && checking.getState() == myEmptyState;
    }
    private boolean checkDirection(List<Integer> directions, Coordinate startingLocation, Coordinate endLocation,
                                    int numPiecesChecked){
        for(int direction : directions){
            if(startingLocation.getRow() + (direction * (numPiecesChecked + 1)) == endLocation.getRow()){
                return true;
            }
        }
        return false;
    }
    private boolean pathMeetsPattern(List<GamePiece> piecesBetween,Coordinate startingLocation, GamePiece checking,
                                     int numPiecesChecked){
        GamePiece closestToStart = checking;
        for(GamePiece between : piecesBetween){
            if(between.getPosition().equals(startingLocation)){
                closestToStart = between;
            }
        }
        boolean pieceTypeCheck;
        if(numPiecesChecked % 2 == 0){
            pieceTypeCheck = opponentPiece(closestToStart);
        }else{
            pieceTypeCheck = checkLandingSpotEmpty(closestToStart);
        }
        return pieceTypeCheck;

    }
    private Coordinate findStartingLocation(List<GamePiece> piecesBetween,Coordinate startingLocation, GamePiece checking){
        Coordinate closestToStart = checking.getPosition();
        for(GamePiece between : piecesBetween){
            if(euclideanDistance(startingLocation,between.getPosition()) <
                    euclideanDistance(startingLocation,closestToStart) ){
                closestToStart = between.getPosition();
            }
        }
        return closestToStart;
    }

    private int euclideanDistance(Coordinate start, Coordinate end){
        return (int) Math.sqrt(Math.pow(start.getRow() - end.getRow(), SQUARE) + Math.pow(start.getCol() - end.getCol(), SQUARE));
    }

    private boolean opponentPiece(GamePiece checking){
        return checking.getState() != myEmptyState && !myPlayerStates.contains(checking.getState());
    }

    private boolean checkLandingSpotEmpty(GamePiece checking){
        return checking.getState() == myEmptyState;
    }
}
