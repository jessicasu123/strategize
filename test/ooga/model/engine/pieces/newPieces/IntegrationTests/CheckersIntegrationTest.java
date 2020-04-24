package ooga.model.engine.pieces.newPieces.IntegrationTests;
import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.*;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.ConvertableNeighborFinder;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.FindNeighborsBetweenCoordinates;
import ooga.model.engine.pieces.newPieces.MoveChecks.CheckJump;
import ooga.model.engine.pieces.newPieces.MoveChecks.CheckOwnPiece;
import ooga.model.engine.pieces.newPieces.MoveChecks.CheckStep;
import ooga.model.engine.pieces.newPieces.MoveChecks.MoveCheck;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CheckersIntegrationTest {
    List<Integer> player1 = new ArrayList<>(List.of(1,2));
    List<Integer> player1Direction = new ArrayList<>(List.of(1));
    List<Integer> player2 = new ArrayList<>(List.of(3,4));
    List<Integer> player2Direction = new ArrayList<>(List.of(-1));
    List<Integer> bothDirections = new ArrayList<>(List.of(-1,1));
    int emptyState = 0;
    int numObjects = 1;
    ConvertableNeighborFinder myFinder = new FindNeighborsBetweenCoordinates();
    MoveCheck ownPiecePlayer1 = new CheckOwnPiece(player1);
    MoveCheck ownPiecePlayer2 = new CheckOwnPiece(player2);
    MoveCheck step = new CheckStep(emptyState);
    MoveCheck jumpPlayer1 = new CheckJump(emptyState, player1);
    MoveCheck jumpPlayer2 = new CheckJump(emptyState, player2);
    MoveType changeOpponent = new ChangeOpponentPieces(myFinder,true,emptyState);
    MoveType positon = new PositionMove();
    MoveType promotionPlayer1 = new Promotion(4,2);
    MoveType promotionPlayer2 = new Promotion(0,4);

    List<MoveCheck> selfMoveCheckPlayer1 = new ArrayList<>(List.of(ownPiecePlayer1));
    List<MoveCheck> selfMoveCheckPlayer2 = new ArrayList<>(List.of(ownPiecePlayer2));
    List<MoveCheck> neighborMoveCheckPlayer1 = new ArrayList<>(List.of(step, jumpPlayer1));
    List<MoveCheck> neighborMoveCheckPlayer2 = new ArrayList<>(List.of(step, jumpPlayer2));
    List<MoveType> moveTypesPlayer1 = new ArrayList<>(List.of(changeOpponent,positon,promotionPlayer1));
    List<MoveType> moveTypesPlayer2 = new ArrayList<>(List.of(changeOpponent,positon,promotionPlayer2));

    //along diagonal
    Coordinate pos1 = new Coordinate(0,0);
    GamePiece checker1Red = new GamePiece(1,pos1,numObjects,player1Direction,selfMoveCheckPlayer1,neighborMoveCheckPlayer1, moveTypesPlayer1);
    GamePiece checker1Black = new GamePiece(3,pos1,numObjects,player2Direction, selfMoveCheckPlayer2,neighborMoveCheckPlayer2, moveTypesPlayer2);
    GamePiece checker1Empty = new GamePiece(0,pos1,0,player2Direction,selfMoveCheckPlayer2,new ArrayList<>(), moveTypesPlayer2);

    Coordinate pos2 = new Coordinate(1,1);
    GamePiece checker2Red = new GamePiece(1,pos2,numObjects,player1Direction,selfMoveCheckPlayer1,neighborMoveCheckPlayer1, moveTypesPlayer1);
    GamePiece checker2Black = new GamePiece(3,pos2,numObjects,player2Direction, selfMoveCheckPlayer2,neighborMoveCheckPlayer2, moveTypesPlayer2);
    GamePiece checker2Empty = new GamePiece(0,pos2,0,player2Direction,selfMoveCheckPlayer2,new ArrayList<>(), moveTypesPlayer2);

    Coordinate pos3 = new Coordinate(2,2);
    GamePiece checker3Red = new GamePiece(1,pos3,numObjects,player1Direction,selfMoveCheckPlayer1,neighborMoveCheckPlayer1, moveTypesPlayer1);
    GamePiece checker3Black = new GamePiece(3,pos3,numObjects,player2Direction, selfMoveCheckPlayer2,neighborMoveCheckPlayer2, moveTypesPlayer2);
    GamePiece checker3Empty = new GamePiece(0,pos3,0,player2Direction,selfMoveCheckPlayer2,new ArrayList<>(), moveTypesPlayer2);

    Coordinate pos4 = new Coordinate(3,3);
    GamePiece checker4Red = new GamePiece(1,pos4,numObjects,player1Direction,selfMoveCheckPlayer1,neighborMoveCheckPlayer1, moveTypesPlayer1);
    GamePiece checker4Black = new GamePiece(3,pos4,numObjects,player2Direction, selfMoveCheckPlayer2,neighborMoveCheckPlayer2, moveTypesPlayer2);
    GamePiece checker4Empty = new GamePiece(0,pos4,0,player2Direction,selfMoveCheckPlayer2,new ArrayList<>(), moveTypesPlayer2);

    Coordinate pos5 = new Coordinate(4,4);
    GamePiece checker5Black = new GamePiece(3,pos5,numObjects,player2Direction, selfMoveCheckPlayer2,neighborMoveCheckPlayer2, moveTypesPlayer2);
    GamePiece checker5Empty = new GamePiece(0,pos5,0,player2Direction,selfMoveCheckPlayer2,new ArrayList<>(), moveTypesPlayer2);

    //alternate diagonals
    Coordinate pos6 = new Coordinate(2,0);
    GamePiece checker6Red = new GamePiece(1,pos6,numObjects,player1Direction,selfMoveCheckPlayer1,neighborMoveCheckPlayer1, moveTypesPlayer1);
    GamePiece checker6Empty = new GamePiece(0,pos6,0,player2Direction,selfMoveCheckPlayer2,new ArrayList<>(), moveTypesPlayer2);

    Coordinate pos7 = new Coordinate(3,1);
    GamePiece checker7Black = new GamePiece(3,pos7,numObjects,player2Direction, selfMoveCheckPlayer2,neighborMoveCheckPlayer2, moveTypesPlayer2);
    GamePiece checker7Empty = new GamePiece(0,pos7,0,player2Direction,selfMoveCheckPlayer2,new ArrayList<>(), moveTypesPlayer2);

    Coordinate pos8 = new Coordinate(4,0);
    GamePiece checker8Empty = new GamePiece(0,pos8,0,player2Direction,selfMoveCheckPlayer2,new ArrayList<>(), moveTypesPlayer2);

    Coordinate pos10 = new Coordinate(3,1);
    GamePiece checker10Red = new GamePiece(1,pos10,numObjects,player1Direction,selfMoveCheckPlayer1,neighborMoveCheckPlayer1, moveTypesPlayer1);
    GamePiece checker10Black = new GamePiece(3,pos10,numObjects,player2Direction, selfMoveCheckPlayer2,neighborMoveCheckPlayer2, moveTypesPlayer2);
    GamePiece checker10Empty = new GamePiece(0,pos10,0,player2Direction,selfMoveCheckPlayer2,new ArrayList<>(), moveTypesPlayer2);

    Coordinate pos11 = new Coordinate(2,4);
    GamePiece checker11Empty = new GamePiece(0,pos11,0,player2Direction,selfMoveCheckPlayer2,new ArrayList<>(), moveTypesPlayer2);

    Coordinate pos12 = new Coordinate(1,3);
    GamePiece checker12Red = new GamePiece(1,pos12,numObjects,player1Direction,selfMoveCheckPlayer1,neighborMoveCheckPlayer1, moveTypesPlayer1);
    GamePiece checker12Black = new GamePiece(3,pos12,numObjects,player2Direction, selfMoveCheckPlayer2,neighborMoveCheckPlayer2, moveTypesPlayer2);
    GamePiece checker12Empty = new GamePiece(0,pos12,0,player2Direction,selfMoveCheckPlayer2,new ArrayList<>(), moveTypesPlayer2);

    Coordinate pos13 = new Coordinate(0,4);
    GamePiece checker13Empty = new GamePiece(0,pos13,0,player2Direction,selfMoveCheckPlayer2,new ArrayList<>(), moveTypesPlayer2);

    GamePiece king =  new GamePiece(2,new Coordinate(2,2),numObjects,bothDirections,selfMoveCheckPlayer1,neighborMoveCheckPlayer1,moveTypesPlayer1);
    GamePiece king2 =  new GamePiece(2,new Coordinate(0,0),numObjects,bothDirections,selfMoveCheckPlayer1,neighborMoveCheckPlayer1,moveTypesPlayer1);
    GamePiece king3 =  new GamePiece(4,new Coordinate(2,2),numObjects,bothDirections,selfMoveCheckPlayer2,neighborMoveCheckPlayer2,moveTypesPlayer2);
    GamePiece king4 =  new GamePiece(4,new Coordinate(0,0),numObjects,bothDirections,selfMoveCheckPlayer2,neighborMoveCheckPlayer2,moveTypesPlayer2);


    @Test
    void testCalculateAllPossibleWhenNoMoves() {
        List<Coordinate> moves = new ArrayList<>();
        List<GamePiece> neighbors = new ArrayList<>(List.of(checker2Empty, checker3Empty, checker6Red, checker4Empty, checker5Black));
        //test empty id
        List<Coordinate> movesCalculated = checker1Red.calculateAllPossibleMoves(neighbors, 0);
        assertEquals(moves, movesCalculated);

        //test opponent id
        movesCalculated = checker1Red.calculateAllPossibleMoves(neighbors, 3);
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
        neighbors = new ArrayList<>(List.of(checker2Black, checker3Empty, checker4Black, checker5Empty));
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
        moves = new ArrayList<>(List.of(new Coordinate(0,0), new Coordinate(2,2)));
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
        moves = new ArrayList<>(List.of(new Coordinate(0,0), new Coordinate(2,2)));
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
        moves = new ArrayList<>(List.of(new Coordinate(0,0), new Coordinate(4,4), new Coordinate(0,4),new Coordinate(4,0)));
        neighbors = new ArrayList<>(List.of(checker1Empty, checker2Black,  checker4Black, checker5Empty, checker10Black, checker12Black, checker13Empty, checker8Empty));
        movesCalculated = king.calculateAllPossibleMoves(neighbors, 1);
        assertEquals(moves, movesCalculated);

        //test double jump
        moves = new ArrayList<>(List.of(new Coordinate(2,2), new Coordinate(4,4)));
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
        moves = new ArrayList<>(List.of(new Coordinate(0,0), new Coordinate(4,4), new Coordinate(0,4),new Coordinate(4,0)));
        neighbors = new ArrayList<>(List.of(checker1Empty, checker2Red,  checker4Red, checker5Empty, checker10Red, checker12Red, checker13Empty, checker8Empty));
        movesCalculated = king3.calculateAllPossibleMoves(neighbors, 3);
        assertEquals(moves, movesCalculated);

        //test double jump
        moves = new ArrayList<>(List.of(new Coordinate(2,2), new Coordinate(4,4)));
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
        checker1Red = new GamePiece(1,pos1,numObjects,player1Direction,selfMoveCheckPlayer1,neighborMoveCheckPlayer1, moveTypesPlayer1);
        checker2Empty = new GamePiece(0,pos2,0,player2Direction,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

        //test 1 jump
        neighbors = new ArrayList<>(List.of(checker2Black, checker3Empty, checker4Red, checker12Empty));
        checker1Red.makeMove(new Coordinate(2,2), neighbors, 1);
        assertEquals(new Coordinate(2,2), checker1Red.getPosition());
        assertEquals(new Coordinate(0,0), checker3Empty.getPosition());
        assertEquals(0, checker2Black.getState());
        checker1Red = new GamePiece(1,pos1,numObjects,player1Direction,selfMoveCheckPlayer1,neighborMoveCheckPlayer1, moveTypesPlayer1);
        checker2Black = new GamePiece(3,pos2,numObjects,player2Direction, selfMoveCheckPlayer2,neighborMoveCheckPlayer2, moveTypesPlayer2);
        checker3Empty = new GamePiece(0,pos3,0,player2Direction,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

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
        checker5Black = new GamePiece(3,pos5,numObjects,player2Direction, selfMoveCheckPlayer2,neighborMoveCheckPlayer2, moveTypesPlayer2);
        checker4Empty = new GamePiece(0,pos4,0,player2Direction,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

        //test 1 jump
        neighbors = new ArrayList<>(List.of(checker2Empty, checker3Empty, checker4Red, checker1Black));
        checker5Black.makeMove(new Coordinate(2,2), neighbors, 3);
        assertEquals(new Coordinate(2,2), checker5Black.getPosition());
        assertEquals(new Coordinate(4,4), checker3Empty.getPosition());
        assertEquals(0, checker4Red.getState());
        checker5Black = new GamePiece(3,pos5,numObjects,player2Direction, selfMoveCheckPlayer2,neighborMoveCheckPlayer2, moveTypesPlayer2);
        checker4Red = new GamePiece(1,pos4,numObjects,player1Direction,selfMoveCheckPlayer1,neighborMoveCheckPlayer1, moveTypesPlayer1);
        checker3Empty = new GamePiece(0,pos3,0,player2Direction,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());


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
