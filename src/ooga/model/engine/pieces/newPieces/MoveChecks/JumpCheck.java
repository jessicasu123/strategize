package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.NeighborsBetweenCoordinatesFinder;

import java.util.List;
/**
 * This class is responsible for checking whether from the start coordinate a piece can jump to the location
 * of the piece checking.
 * A jump needs to follow the pattern of jumping over a piece of the opponent state, followed by an empty square
 * Jumps can only land on empty squares, therefore the number of pieces being jumped over has to be even
 *
 * @author Holly Ansel
 */
public class JumpCheck implements MoveCheck {
    public static final int SQUARE = 2;
    private NeighborsBetweenCoordinatesFinder myPiecesInBetween;
    private int myEmptyState;
    private List<Integer> myPlayerStates;

    /**
     * @param emptyState - the empty state, where jumps can land and is used to confirm the pattern
     * @param playerStates - the states of player who is jumping which is used to identify opponent pieces ensuring
     *                     that you only jump over opponents
     */
    public JumpCheck(int emptyState, List<Integer> playerStates){
        myPiecesInBetween = new NeighborsBetweenCoordinatesFinder();
        myEmptyState = emptyState;
        myPlayerStates = playerStates;
    }

    /**
     * Checks the pattern of pieces in between the start and end coordinate to see if they alternate opponent state,
     * empty state and the end coordinate is an empty state
     * @param startingLocation - the starting location of piece that is calculating its possible moves
     * @param checking - the game piece that is being checked
     * @param neighbors - the neighbors of the piece that is calculating its possible moves
     * @param player - the player which the move is being checked for
     * @param directions - all the legal directions this piece can move in
     * @return whether going from the start coordinate to the end coordinate meets the conditions for a legal jump
     */
    @Override
    public boolean isConditionMet(Coordinate startingLocation, GamePiece checking, List<GamePiece> neighbors,
                                  int player, List<Integer> directions) {
        boolean patternMet = true;
        List<GamePiece> piecesBetween = myPiecesInBetween.findNeighborsToConvert(startingLocation,
                checking.getPosition(),checking.getNumObjects(), player,directions.get(0),neighbors);
        Coordinate startCheckLocation = findStartingLocation(piecesBetween,startingLocation,checking);
        int numPiecesToCheck = piecesBetween.size();
        if(checkEvenNumberOfPieces(numPiecesToCheck)){
            return false;
        }
        for(int i = 0; i < numPiecesToCheck;i++){
            boolean patternMetSoFar = pathMeetsPattern(piecesBetween,startCheckLocation,checking,i);
            patternMet = patternMet && patternMetSoFar;
            piecesBetween = myPiecesInBetween.findNeighborsToConvert(startCheckLocation,checking.getPosition(),
                    checking.getNumObjects(), player,directions.get(0),piecesBetween);
            startCheckLocation = findStartingLocation(piecesBetween,startCheckLocation,checking);
        }

        return patternMet && checkDirection(directions,startingLocation,checking.getPosition(), numPiecesToCheck)
                && checking.getState() == myEmptyState;
    }

    private boolean checkEvenNumberOfPieces(int numPiecesToCheck) {
        return numPiecesToCheck % 2 == 0;
    }

    private boolean checkDirection(List<Integer> directions, Coordinate startingLocation, Coordinate endLocation,
                                    int numPiecesChecked){
        for(int direction : directions){
            //includes counting the starting location
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
        if(checkEvenNumberOfPieces(numPiecesChecked)){
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
