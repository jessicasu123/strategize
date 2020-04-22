package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckersGamePieceTest {
    //along diagonal
    Coordinate pos1 = new Coordinate(0,0);
    CheckersGamePiece checker1Red = new CheckersGamePiece(1,1,2,0,1,1, pos1);
    CheckersGamePiece checker1Black = new CheckersGamePiece(3,3,4,0, -1,1, pos1);
    CheckersGamePiece checker1Empty = new CheckersGamePiece(0,0,0,0,0,1, pos1);

    Coordinate pos2 = new Coordinate(1,1);
    CheckersGamePiece checker2Red = new CheckersGamePiece(1,1,2,0,1, 1,pos2);
    CheckersGamePiece checker2Black = new CheckersGamePiece(3,3,4,0,-1,1, pos2);
    CheckersGamePiece checker2Empty = new CheckersGamePiece(0,0,0,0,0,1, pos2);

    Coordinate pos3 = new Coordinate(2,2);
    CheckersGamePiece checker3Red = new CheckersGamePiece(1,1,2,0,1,1, pos3);
    CheckersGamePiece checker3Black = new CheckersGamePiece(3,3,4,0,-1,1, pos3);
    CheckersGamePiece checker3Empty = new CheckersGamePiece(0,0,0,0,0,1, pos3);

    Coordinate pos4 = new Coordinate(3,3);
    CheckersGamePiece checker4Red = new CheckersGamePiece(1,1,2,0,1,1, pos4);
    CheckersGamePiece checker4Black = new CheckersGamePiece(3,3,4,0,-1,1, pos4);
    CheckersGamePiece checker4Empty = new CheckersGamePiece(0,0,0,0,0,1, pos4);

    Coordinate pos5 = new Coordinate(4,4);
    CheckersGamePiece checker5Red = new CheckersGamePiece(1,1,2,0,1,1, pos5);
    CheckersGamePiece checker5Black = new CheckersGamePiece(3,3,4,0,-1,1, pos5);
    CheckersGamePiece checker5Empty = new CheckersGamePiece(0,0,0,0,0,1, pos5);

    //alternate diagonals
    Coordinate pos6 = new Coordinate(2,0);
    CheckersGamePiece checker6Red = new CheckersGamePiece(1,1,2,0,1,1, pos6);
    CheckersGamePiece checker6Black = new CheckersGamePiece(3,3,4,0,-1,1, pos6);
    CheckersGamePiece checker6Empty = new CheckersGamePiece(0,0,0,0,0,1, pos6);

    Coordinate pos7 = new Coordinate(3,1);
    CheckersGamePiece checker7Red = new CheckersGamePiece(1,1,2,0,1,1, pos7);
    CheckersGamePiece checker7Black = new CheckersGamePiece(3,3,4,0,-1,1, pos7);
    CheckersGamePiece checker7Empty = new CheckersGamePiece(0,0,0,0,0,1, pos7);

    Coordinate pos8 = new Coordinate(4,0);
    CheckersGamePiece checker8Red = new CheckersGamePiece(1,1,2,0,1, 1,pos8);
    CheckersGamePiece checker8Black = new CheckersGamePiece(3,3,4,0,-1,1, pos8);
    CheckersGamePiece checker8Empty = new CheckersGamePiece(0,0,0,0,0,1, pos8);

    Coordinate pos9 = new Coordinate(4,2);
    CheckersGamePiece checker9Red = new CheckersGamePiece(1,1,2,0,1,1, pos9);
    CheckersGamePiece checker9Black = new CheckersGamePiece(3,3,4,0,-1,1, pos9);
    CheckersGamePiece checker9Empty = new CheckersGamePiece(0,0,0,0,0,1, pos9);

    Coordinate pos10 = new Coordinate(3,1);
    CheckersGamePiece checker10Red = new CheckersGamePiece(1,1,2,0,1,1, pos10);
    CheckersGamePiece checker10Black = new CheckersGamePiece(3,3,4,0,-1,1, pos10);
    CheckersGamePiece checker10Empty = new CheckersGamePiece(0,0,0,0,0, 1,pos10);

    Coordinate pos11 = new Coordinate(2,4);
    CheckersGamePiece checker11Red = new CheckersGamePiece(1,1,2,0,1, 1,pos11);
    CheckersGamePiece checker11Black = new CheckersGamePiece(3,3,4,0,-1,1, pos11);
    CheckersGamePiece checker11Empty = new CheckersGamePiece(0,0,0,0,0,1, pos11);

    Coordinate pos12 = new Coordinate(1,3);
    CheckersGamePiece checker12Red = new CheckersGamePiece(1,1,2,0,1,1, pos12);
    CheckersGamePiece checker12Black = new CheckersGamePiece(3,3,4,0,-1,1, pos12);
    CheckersGamePiece checker12Empty = new CheckersGamePiece(0,0,0,0,0,1, pos12);

    Coordinate pos13 = new Coordinate(0,4);
    CheckersGamePiece checker13Red = new CheckersGamePiece(1,1,2,0,1,1, pos13);
    CheckersGamePiece checker13Black = new CheckersGamePiece(3,3,4,0,-1,1, pos13);
    CheckersGamePiece checker13Empty = new CheckersGamePiece(0,0,0,0,0,1, pos13);

    CheckersGamePiece king =  new CheckersGamePiece(2,1,2,0,1,1,new Coordinate(2,2));
    CheckersGamePiece king2 =  new CheckersGamePiece(2,1,2,0,1,1,new Coordinate(0,0));
    CheckersGamePiece king3 =  new CheckersGamePiece(4,3,4,0,-1,1,new Coordinate(2,2));
    CheckersGamePiece king4 =  new CheckersGamePiece(4,3,4,0,-1,1,new Coordinate(0,0));

    @Test
    void testCalculateAllPossibleWhenNoMoves() {
        List<Coordinate> moves = new ArrayList<>();
        List<GamePiece> neighbors = new ArrayList<>(List.of(checker2Empty, checker3Empty, checker6Red, checker4Empty, checker5Black));
        //test empty id
        List<Coordinate> movesCalculated = checker1Red.calculateAllPossibleMoves(neighbors, 0);
        assertEquals(moves, movesCalculated);

        //test opponent id
        movesCalculated = checker1Red.calculateAllPossibleMoves(neighbors, 2);
        assertEquals(moves, movesCalculated);
        
        //test moving empty piece
        movesCalculated = checker1Empty.calculateAllPossibleMoves(neighbors, 0);
        assertEquals(moves, movesCalculated);
    }

    @Test
    void testCalculateAllPossibleMovesRedPawn() {
        //test only one step move
        List<Coordinate> moves = new ArrayList<>(List.of(new Coordinate(1,1)));
        List<GamePiece> neighbors = new ArrayList<>(List.of(checker2Empty, checker3Empty, checker6Red, checker4Empty, checker5Black));
        List<Coordinate> movesCalculated = checker1Red.calculateAllPossibleMoves(neighbors, 1);
        assertEquals(moves, movesCalculated);

        //test 1 jump (one path)
        moves = new ArrayList<>(List.of(new Coordinate(2,2)));
        neighbors = new ArrayList<>(List.of(checker2Black, checker3Empty, checker7Empty, checker4Empty, checker5Black));
        movesCalculated = checker1Red.calculateAllPossibleMoves(neighbors, 1);
        assertEquals(moves, movesCalculated);

        //test double jump (one path)
        moves = new ArrayList<>(List.of(new Coordinate(2,2), new Coordinate(4,4)));
        neighbors = new ArrayList<>(List.of(checker2Black, checker3Empty, checker7Empty, checker4Black, checker5Empty));
        movesCalculated = checker1Red.calculateAllPossibleMoves(neighbors, 1);
        assertEquals(moves, movesCalculated);

        //test no moves
        moves = new ArrayList<>();
        neighbors = new ArrayList<>(List.of(checker2Red, checker3Empty, checker4Black, checker5Empty));
        movesCalculated = checker1Red.calculateAllPossibleMoves(neighbors, 1);
        assertEquals(moves, movesCalculated);

        //test 2 step moves
        moves = new ArrayList<>(List.of(new Coordinate(2,2), new Coordinate(2,0)));
        neighbors = new ArrayList<>(List.of(checker1Empty, checker3Empty, checker6Empty, checker4Empty, checker5Black));
        movesCalculated = checker2Red.calculateAllPossibleMoves(neighbors, 1);
        assertEquals(moves, movesCalculated);

        //test 1 jump 2 paths
        moves = new ArrayList<>(List.of(new Coordinate(4,0), new Coordinate(4,4)));
        neighbors = new ArrayList<>(List.of(checker2Black, checker7Black, checker8Empty, checker4Black, checker5Empty));
        movesCalculated = checker3Red.calculateAllPossibleMoves(neighbors, 1);
        assertEquals(moves, movesCalculated);


        //test double jump 2 paths
        moves = new ArrayList<>(List.of(new Coordinate(2,2), new Coordinate(4,0), new Coordinate(4,4)));
        neighbors = new ArrayList<>(List.of(checker2Black, checker3Empty, checker7Black, checker8Empty, checker4Black, checker5Empty));
        movesCalculated = checker1Red.calculateAllPossibleMoves(neighbors, 1);
        assertEquals(moves, movesCalculated);

    }
    @Test
    void testCalculateAllPossibleMovesBlackPawn() {
        //test only one step move
        List<Coordinate> moves = new ArrayList<>(List.of(new Coordinate(3,3)));
        List<GamePiece> neighbors = new ArrayList<>(List.of(checker2Empty, checker3Empty, checker4Empty, checker1Black));
        List<Coordinate> movesCalculated = checker5Black.calculateAllPossibleMoves(neighbors, 3);
        assertEquals(moves, movesCalculated);

        //test 1 jump (one path)
        moves = new ArrayList<>(List.of(new Coordinate(2,2)));
        neighbors = new ArrayList<>(List.of(checker2Empty, checker3Empty, checker4Red, checker1Black, checker12Empty));
        movesCalculated = checker5Black.calculateAllPossibleMoves(neighbors, 3);
        assertEquals(moves, movesCalculated);

        //test double jump (one path)
        moves = new ArrayList<>(List.of(new Coordinate(2,2), new Coordinate(0,0)));
        neighbors = new ArrayList<>(List.of(checker1Empty, checker2Red, checker3Empty, checker4Red, checker11Empty));
        movesCalculated = checker5Black.calculateAllPossibleMoves(neighbors, 3);
        assertEquals(moves, movesCalculated);

        //test no moves
        moves = new ArrayList<>();
        neighbors = new ArrayList<>(List.of(checker1Empty, checker2Red, checker3Empty, checker4Black));
        movesCalculated = checker5Black.calculateAllPossibleMoves(neighbors, 3);
        assertEquals(moves, movesCalculated);

        //test 2 step moves
        moves = new ArrayList<>(List.of(new Coordinate(2,2), new Coordinate(2,4)));
        neighbors = new ArrayList<>(List.of(checker1Empty, checker2Red, checker3Empty, checker5Empty, checker11Empty));
        movesCalculated = checker4Black.calculateAllPossibleMoves(neighbors, 3);
        assertEquals(moves, movesCalculated);

        //test 1 jump 2 paths
        moves = new ArrayList<>(List.of(new Coordinate(0,0), new Coordinate(0,4)));
        neighbors = new ArrayList<>(List.of(checker1Empty, checker2Red, checker13Empty, checker12Red, checker4Red, checker11Empty));
        movesCalculated = checker3Black.calculateAllPossibleMoves(neighbors, 3);
        assertEquals(moves, movesCalculated);

        //test double jump 2 paths
        moves = new ArrayList<>(List.of(new Coordinate(2,2), new Coordinate(0,0), new Coordinate(0,4)));
        neighbors = new ArrayList<>(List.of(checker1Empty, checker2Red, checker13Empty, checker12Red, checker3Empty, checker4Red, checker11Empty));
        movesCalculated = checker5Black.calculateAllPossibleMoves(neighbors, 3);
        assertEquals(moves, movesCalculated);
    }
    @Test
    void testCalculateAllPossibleMovesKingRed() {
        //test step move
        List<Coordinate> moves = new ArrayList<>(List.of(new Coordinate(1, 1), new Coordinate(3, 3), new Coordinate(3, 1), new Coordinate(1, 3)));
        List<GamePiece> neighbors = new ArrayList<>(List.of(checker2Empty, checker4Empty, checker1Black, checker10Empty, checker12Empty));
        List<Coordinate> movesCalculated = king.calculateAllPossibleMoves(neighbors, 1);
        assertEquals(moves, movesCalculated);

        //test 1 jump
        moves = new ArrayList<>(List.of(new Coordinate(0,0), new Coordinate(4,4), new Coordinate(4,0), new Coordinate(0,4)));
        neighbors = new ArrayList<>(List.of(checker1Empty, checker2Black,  checker4Black, checker5Empty, checker10Black, checker12Black, checker13Empty, checker8Empty));
        movesCalculated = king.calculateAllPossibleMoves(neighbors, 1);
        assertEquals(moves, movesCalculated);

        //test double jump
        moves = new ArrayList<>(List.of(new Coordinate(2,2), new Coordinate(4,4), new Coordinate(4,0), new Coordinate(0,4)));
        neighbors = new ArrayList<>(List.of(checker2Black, checker3Empty, checker4Black, checker5Empty, checker10Black, checker12Black, checker13Empty, checker8Empty));
        movesCalculated = king2.calculateAllPossibleMoves(neighbors, 1);
        assertEquals(moves, movesCalculated);

    }

    @Test
    void testCalculateAllPossibleMovesKingBlack() {
        //test step move
        List<Coordinate> moves = new ArrayList<>(List.of(new Coordinate(1, 1), new Coordinate(3, 3), new Coordinate(3, 1), new Coordinate(1, 3)));
        List<GamePiece> neighbors = new ArrayList<>(List.of(checker2Empty, checker4Empty, checker1Black, checker10Empty, checker12Empty));
        List<Coordinate> movesCalculated = king3.calculateAllPossibleMoves(neighbors, 3);
        assertEquals(moves, movesCalculated);

        //test 1 jump
        moves = new ArrayList<>(List.of(new Coordinate(0,0), new Coordinate(4,4), new Coordinate(4,0), new Coordinate(0,4)));
        neighbors = new ArrayList<>(List.of(checker1Empty, checker2Red,  checker4Red, checker5Empty, checker10Red, checker12Red, checker13Empty, checker8Empty));
        movesCalculated = king3.calculateAllPossibleMoves(neighbors, 3);
        assertEquals(moves, movesCalculated);

        //test double jump
        moves = new ArrayList<>(List.of(new Coordinate(2,2), new Coordinate(4,4), new Coordinate(4,0), new Coordinate(0,4)));
        neighbors = new ArrayList<>(List.of(checker2Red, checker3Empty, checker4Red, checker5Empty, checker10Red, checker12Red, checker13Empty, checker8Empty));
        movesCalculated = king4.calculateAllPossibleMoves(neighbors, 3);
        assertEquals(moves, movesCalculated);

    }

    @Test
    void testMakeMoveRed() {
        //test normal move
        List<GamePiece> neighbors = new ArrayList<>(List.of(checker2Empty, checker3Empty, checker6Red, checker4Empty, checker5Black));
        checker1Red.makeMove(new Coordinate(1,1), neighbors, 1);
        assertEquals(new Coordinate(1,1), checker1Red.getPosition());
        assertEquals(new Coordinate(0,0), checker2Empty.getPosition());
        checker1Red = new CheckersGamePiece(1,1,2,0,1,1, pos1);
        checker2Empty = new CheckersGamePiece(0,0,0,0,0,1, pos2);

        //test 1 jump
        neighbors = new ArrayList<>(List.of(checker2Black, checker3Empty, checker4Red, checker12Empty));
        checker1Red.makeMove(new Coordinate(2,2), neighbors, 1);
        assertEquals(new Coordinate(2,2), checker1Red.getPosition());
        assertEquals(new Coordinate(0,0), checker3Empty.getPosition());
        assertEquals(0, checker2Black.getState());
        checker1Red = new CheckersGamePiece(1,1,2,0,1,1, pos1);
        checker2Black = new CheckersGamePiece(3,3,4,0,-1,1, pos2);
        checker3Empty = new CheckersGamePiece(0,0,0,0,0,1, pos3);

        //test double jump
        neighbors = new ArrayList<>(List.of(checker2Black, checker3Empty, checker4Black, checker5Empty));
        checker1Red.makeMove(new Coordinate(4,4), neighbors, 1);
        assertEquals(new Coordinate(4,4), checker1Red.getPosition());
        assertEquals(new Coordinate(0,0), checker5Empty.getPosition());
        assertEquals(0, checker2Black.getState());
        assertEquals(0, checker4Black.getState());

        //test king promotion
        assertEquals(2, checker1Red.getState());
    }

    @Test
    void testMakeMoveBlack() {
        //test normal move
        List<GamePiece> neighbors = new ArrayList<>(List.of(checker2Empty, checker3Empty, checker4Empty, checker1Black));
        checker5Black.makeMove(new Coordinate(3,3), neighbors, 3);
        assertEquals(new Coordinate(3,3), checker5Black.getPosition());
        assertEquals(new Coordinate(4,4), checker4Empty.getPosition());
        checker5Black = new CheckersGamePiece(3,3,4,0,-1,1, pos5);
        checker4Empty = new CheckersGamePiece(0,0,0,0,0,1, pos4);

        //test 1 jump
        neighbors = new ArrayList<>(List.of(checker2Empty, checker3Empty, checker4Red, checker1Black));
        checker5Black.makeMove(new Coordinate(2,2), neighbors, 3);
        assertEquals(new Coordinate(2,2), checker5Black.getPosition());
        assertEquals(new Coordinate(4,4), checker3Empty.getPosition());
        assertEquals(0, checker4Red.getState());
        checker5Black = new CheckersGamePiece(3,3,4,0,-1,1, pos5);
        checker4Red = new CheckersGamePiece(1,1,2,0,1,1, pos4);
        checker3Empty = new CheckersGamePiece(0,0,0,0,0,1, pos3);


        //test double jump
        neighbors = new ArrayList<>(List.of(checker1Empty, checker2Red, checker3Empty, checker4Red, checker1Black));
        checker5Black.makeMove(new Coordinate(0,0), neighbors, 3);
        assertEquals(new Coordinate(0,0), checker5Black.getPosition());
        assertEquals(new Coordinate(4,4), checker1Empty.getPosition());
        assertEquals(0, checker4Red.getState());
        assertEquals(0, checker2Red.getState());

        //test king promotion
        assertEquals(4, checker5Black.getState());
    }

    //trivial, this is tested indirectly in other tests
    @Test
    void testChangeState() {
        assertEquals(1, checker1Red.getState());
        checker1Red.changeState(2);
        assertEquals(2, checker1Red.getState());
    }

    //trivial, this is tested indirectly in other tests
    @Test
    void testGetState() {
        //normal
        assertEquals(1, checker1Red.getState());

    }

    //trivial, this is tested indirectly in other tests
    @Test
    void testGetPosition() {
        assertEquals(new Coordinate(0,0), checker1Red.getPosition());
    }
}