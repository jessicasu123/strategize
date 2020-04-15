package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MancalaGamePieceTest {

    MancalaGamePiece goalPocket1 = new MancalaGamePiece(4,4,-1,0, 0, new Coordinate(0,0));
    MancalaGamePiece goalPocket2 = new MancalaGamePiece(2,2,1,0, 0, new Coordinate(1,7));

    MancalaGamePiece emptySquare1 = new MancalaGamePiece(0,0,0,0, 0, new Coordinate(0,7));
    MancalaGamePiece emptySquare2 = new MancalaGamePiece(0,0,0,0, 0, new Coordinate(1,0));

    MancalaGamePiece topSqaure1 = new MancalaGamePiece(3,4,-1,4, 0, new Coordinate(0,1));
    MancalaGamePiece topSqaure2 = new MancalaGamePiece(3,4,-1,4, 0, new Coordinate(0,2));
    MancalaGamePiece topSqaure3 = new MancalaGamePiece(3,4,-1,4, 0, new Coordinate(0,3));
    MancalaGamePiece topSqaure4 = new MancalaGamePiece(3,4,-1,4, 0, new Coordinate(0,4));
    MancalaGamePiece topSqaure5 = new MancalaGamePiece(3,4,-1,4, 0, new Coordinate(0,5));
    MancalaGamePiece topSqaure6 = new MancalaGamePiece(3,4,-1,4, 0, new Coordinate(0,6));

    MancalaGamePiece bottomSqaure1 = new MancalaGamePiece(1,2,1,4, 0, new Coordinate(1,1));
    MancalaGamePiece bottomSqaure2 = new MancalaGamePiece(1,2,1,4, 0, new Coordinate(1,2));
    MancalaGamePiece bottomSqaure3 = new MancalaGamePiece(1,2,1,4, 0, new Coordinate(1,3));
    MancalaGamePiece bottomSqaure4 = new MancalaGamePiece(1,2,1,4, 0, new Coordinate(1,4));
    MancalaGamePiece bottomSqaure5 = new MancalaGamePiece(1,2,1,4, 0, new Coordinate(1,5));
    MancalaGamePiece bottomSqaure6 = new MancalaGamePiece(1,2,1,4, 0, new Coordinate(1,6));

    List<GamePiece> allPieces = new ArrayList<>(List.of(goalPocket1,goalPocket2,emptySquare1,emptySquare2,topSqaure1,topSqaure2,topSqaure3,topSqaure4,
            topSqaure5,topSqaure6, bottomSqaure1, bottomSqaure2, bottomSqaure3,bottomSqaure4,bottomSqaure5,bottomSqaure6));

    @Test
    void calculateAllPossibleMovesTop() {
        //simple test
        List<Coordinate> moves = new ArrayList<>(List.of(new Coordinate(0,2)));
        allPieces.remove(topSqaure6);
        List<Coordinate> movesCalculated = topSqaure6.calculateAllPossibleMoves(allPieces,3);
        assertEquals(moves, movesCalculated);
        allPieces.add(topSqaure6);


        //test ends in goal
        moves = new ArrayList<>(List.of(new Coordinate(0,0)));
        allPieces.remove(topSqaure4);
        movesCalculated = topSqaure4.calculateAllPossibleMoves(allPieces,3);
        assertEquals(moves, movesCalculated);
        allPieces.add(topSqaure4);

        //test wrap around
        moves = new ArrayList<>(List.of(new Coordinate(1,3)));
        allPieces.remove(topSqaure1);
        movesCalculated = topSqaure1.calculateAllPossibleMoves(allPieces,3);
        assertEquals(moves, movesCalculated);
        allPieces.add(topSqaure1);

    }


    @Test
    void calculateAllPossibleMovesBottom() {
        //simple test
        List<Coordinate> moves = new ArrayList<>(List.of(new Coordinate(1,5)));
        allPieces.remove(bottomSqaure1);
        List<Coordinate> movesCalculated = bottomSqaure1.calculateAllPossibleMoves(allPieces,1);
        assertEquals(moves, movesCalculated);
        allPieces.add(bottomSqaure1);


        //test ends in goal
        moves = new ArrayList<>(List.of(new Coordinate(1,7)));
        allPieces.remove(bottomSqaure3);
        movesCalculated = bottomSqaure3.calculateAllPossibleMoves(allPieces,1);
        assertEquals(moves, movesCalculated);
        allPieces.add(bottomSqaure3);

        //test wrap around
        moves = new ArrayList<>(List.of(new Coordinate(0,4)));
        allPieces.remove(bottomSqaure6);
        movesCalculated = bottomSqaure6.calculateAllPossibleMoves(allPieces,1);
        assertEquals(moves, movesCalculated);
        allPieces.add(bottomSqaure6);

    }

    @Test
    void testCalculateAllPossibleWhenNoMoves() {
        List<Coordinate> moves = new ArrayList<>();
        //test empty id
        List<Coordinate> movesCalculated = bottomSqaure1.calculateAllPossibleMoves(allPieces,0);
        assertEquals(moves, movesCalculated);

        //test opponent id
        movesCalculated = bottomSqaure1.calculateAllPossibleMoves(allPieces,3);
        assertEquals(moves, movesCalculated);

        //test moving empty square
        movesCalculated = emptySquare1.calculateAllPossibleMoves(allPieces,0);
        assertEquals(moves, movesCalculated);
        movesCalculated = emptySquare1.calculateAllPossibleMoves(allPieces,1);
        assertEquals(moves, movesCalculated);

        //test moving goal square
        movesCalculated = goalPocket1.calculateAllPossibleMoves(allPieces,1);
        assertEquals(moves, movesCalculated);
        movesCalculated = goalPocket1.calculateAllPossibleMoves(allPieces,2);
        assertEquals(moves, movesCalculated);
    }

    @Test
    void makeMoveTop() {
        //test normal move
        allPieces.remove(topSqaure6);
        topSqaure6.makeMove(new Coordinate(0,2), allPieces,3);
        assertEquals(new Coordinate(0,6), topSqaure6.getPosition());
        assertEquals(0, topSqaure6.getVisualRepresentation());
        assertEquals(5, topSqaure5.getVisualRepresentation());
        assertEquals(5, topSqaure4.getVisualRepresentation());
        assertEquals(5, topSqaure3.getVisualRepresentation());
        assertEquals(5, topSqaure2.getVisualRepresentation());
        topSqaure2 = new MancalaGamePiece(3,4,-1,4, 0, new Coordinate(0,2));
        topSqaure3 = new MancalaGamePiece(3,4,-1,4, 0, new Coordinate(0,3));
        topSqaure4 = new MancalaGamePiece(3,4,-1,4, 0, new Coordinate(0,4));
        topSqaure5 = new MancalaGamePiece(3,4,-1,4, 0, new Coordinate(0,5));
        topSqaure6 = new MancalaGamePiece(3,4,-1,4, 0, new Coordinate(0,6));
        allPieces.add(topSqaure6);
        allPieces = new ArrayList<>(List.of(goalPocket1,goalPocket2,emptySquare1,emptySquare2,topSqaure1,topSqaure2,topSqaure3,topSqaure4,
                topSqaure5,topSqaure6, bottomSqaure1, bottomSqaure2, bottomSqaure3,bottomSqaure4,bottomSqaure5,bottomSqaure6));

        //test move that ends in goal state
        allPieces.remove(topSqaure4);
        topSqaure4.makeMove(new Coordinate(0,0), allPieces,3);
        assertEquals(new Coordinate(0,4), topSqaure4.getPosition());
        assertEquals(0, topSqaure4.getVisualRepresentation());
        assertEquals(5, topSqaure3.getVisualRepresentation());
        assertEquals(5, topSqaure2.getVisualRepresentation());
        assertEquals(5, topSqaure1.getVisualRepresentation());
        assertEquals(1, goalPocket1.getVisualRepresentation());
        topSqaure2 = new MancalaGamePiece(3,4,-1,4, 0, new Coordinate(0,2));
        topSqaure3 = new MancalaGamePiece(3,4,-1,4, 0, new Coordinate(0,3));
        topSqaure4 = new MancalaGamePiece(3,4,-1,4, 0, new Coordinate(0,4));
        topSqaure1 = new MancalaGamePiece(3,4,-1,4, 0, new Coordinate(0,1));
        goalPocket1 = new MancalaGamePiece(4,4,-1,0, 0, new Coordinate(0,0));
        allPieces.add(topSqaure4);
        allPieces = new ArrayList<>(List.of(goalPocket1,goalPocket2,emptySquare1,emptySquare2,topSqaure1,topSqaure2,topSqaure3,topSqaure4,
                topSqaure5,topSqaure6, bottomSqaure1, bottomSqaure2, bottomSqaure3,bottomSqaure4,bottomSqaure5,bottomSqaure6));

        //test wrap around
        allPieces.remove(topSqaure1);
        topSqaure1.makeMove(new Coordinate(1,3), allPieces,3);
        assertEquals(new Coordinate(0,1), topSqaure1.getPosition());
        assertEquals(0, topSqaure1.getVisualRepresentation());
        assertEquals(5, bottomSqaure1.getVisualRepresentation());
        assertEquals(5, bottomSqaure2.getVisualRepresentation());
        assertEquals(5, bottomSqaure3.getVisualRepresentation());
        assertEquals(1, goalPocket1.getVisualRepresentation());
        bottomSqaure1 = new MancalaGamePiece(1,2,1,4, 0, new Coordinate(1,1));
        bottomSqaure2 = new MancalaGamePiece(1,2,1,4, 0, new Coordinate(1,2));
        bottomSqaure3 = new MancalaGamePiece(1,2,1,4, 0, new Coordinate(1,3));
        topSqaure1 = new MancalaGamePiece(3,4,-1,4, 0, new Coordinate(0,1));
        goalPocket1 = new MancalaGamePiece(4,4,-1,0, 0, new Coordinate(0,0));
        allPieces.add(topSqaure1);
        allPieces = new ArrayList<>(List.of(goalPocket1,goalPocket2,emptySquare1,emptySquare2,topSqaure1,topSqaure2,topSqaure3,topSqaure4,
                topSqaure5,topSqaure6, bottomSqaure1, bottomSqaure2, bottomSqaure3,bottomSqaure4,bottomSqaure5,bottomSqaure6));

    }

    @Test
    void makeMoveBottom() {
        //test normal move
        allPieces.remove(bottomSqaure1);
        bottomSqaure1.makeMove(new Coordinate(1,1), allPieces,1);
        assertEquals(new Coordinate(1,1), bottomSqaure1.getPosition());
        assertEquals(0, bottomSqaure1.getVisualRepresentation());
        assertEquals(5, bottomSqaure2.getVisualRepresentation());
        assertEquals(5, bottomSqaure3.getVisualRepresentation());
        assertEquals(5, bottomSqaure4.getVisualRepresentation());
        assertEquals(5, bottomSqaure5.getVisualRepresentation());
        bottomSqaure1 = new MancalaGamePiece(1,2,1,4, 0, new Coordinate(1,1));
        bottomSqaure2 = new MancalaGamePiece(1,2,1,4, 0, new Coordinate(1,2));
        bottomSqaure3 = new MancalaGamePiece(1,2,1,4, 0, new Coordinate(1,3));
        bottomSqaure4 = new MancalaGamePiece(1,2,1,4, 0, new Coordinate(1,4));
        bottomSqaure5 = new MancalaGamePiece(1,2,1,4, 0, new Coordinate(1,5));
        allPieces.add(bottomSqaure1);
        allPieces = new ArrayList<>(List.of(goalPocket1,goalPocket2,emptySquare1,emptySquare2,topSqaure1,topSqaure2,topSqaure3,topSqaure4,
                topSqaure5,topSqaure6, bottomSqaure1, bottomSqaure2, bottomSqaure3,bottomSqaure4,bottomSqaure5,bottomSqaure6));

        //test move that ends in goal state
        allPieces.remove(bottomSqaure3);
        bottomSqaure3.makeMove(new Coordinate(1,7), allPieces,1);
        assertEquals(new Coordinate(1,3), bottomSqaure3.getPosition());
        assertEquals(0, bottomSqaure3.getVisualRepresentation());
        assertEquals(5, bottomSqaure4.getVisualRepresentation());
        assertEquals(5, bottomSqaure5.getVisualRepresentation());
        assertEquals(5, bottomSqaure6.getVisualRepresentation());
        assertEquals(1, goalPocket2.getVisualRepresentation());
        bottomSqaure3 = new MancalaGamePiece(1,2,1,4, 0, new Coordinate(1,3));
        bottomSqaure4 = new MancalaGamePiece(1,2,1,4, 0, new Coordinate(1,4));
        bottomSqaure5 = new MancalaGamePiece(1,2,1,4, 0, new Coordinate(1,5));
        bottomSqaure6 = new MancalaGamePiece(1,2,1,4, 0, new Coordinate(1,6));
        goalPocket2 = new MancalaGamePiece(2,2,1,0, 0, new Coordinate(1,7));
        allPieces.add(bottomSqaure3);
        allPieces = new ArrayList<>(List.of(goalPocket1,goalPocket2,emptySquare1,emptySquare2,topSqaure1,topSqaure2,topSqaure3,topSqaure4,
                topSqaure5,topSqaure6, bottomSqaure1, bottomSqaure2, bottomSqaure3,bottomSqaure4,bottomSqaure5,bottomSqaure6));

        //test wrap around
        allPieces.remove(bottomSqaure6);
        bottomSqaure6.makeMove(new Coordinate(0,4), allPieces,1);
        assertEquals(new Coordinate(1,6), bottomSqaure6.getPosition());
        assertEquals(0, bottomSqaure6.getVisualRepresentation());
        assertEquals(1, goalPocket2.getVisualRepresentation());
        assertEquals(5, topSqaure6.getVisualRepresentation());
        assertEquals(5, topSqaure5.getVisualRepresentation());
        assertEquals(5, topSqaure4.getVisualRepresentation());
        bottomSqaure6 = new MancalaGamePiece(1,2,1,4, 0, new Coordinate(1,6));
        goalPocket2 = new MancalaGamePiece(2,2,1,0, 0, new Coordinate(1,7));
        topSqaure4 = new MancalaGamePiece(3,4,-1,4, 0, new Coordinate(0,4));
        topSqaure5 = new MancalaGamePiece(3,4,-1,4, 0, new Coordinate(0,5));
        topSqaure6 = new MancalaGamePiece(3,4,-1,4, 0, new Coordinate(0,6));
        allPieces.add(bottomSqaure6);
        allPieces = new ArrayList<>(List.of(goalPocket1,goalPocket2,emptySquare1,emptySquare2,topSqaure1,topSqaure2,topSqaure3,topSqaure4,
                topSqaure5,topSqaure6, bottomSqaure1, bottomSqaure2, bottomSqaure3,bottomSqaure4,bottomSqaure5,bottomSqaure6));



    }
}