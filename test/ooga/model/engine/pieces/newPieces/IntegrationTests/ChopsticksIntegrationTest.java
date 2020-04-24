//package ooga.model.engine.pieces.newPieces.IntegrationTests;
//
//import ooga.model.engine.Coordinate;
//import ooga.model.engine.pieces.newPieces.*;
//import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.ConvertibleNeighborFinder;
//import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.FindConvertibleNeighborAtEndCoord;
//import ooga.model.engine.pieces.newPieces.MoveChecks.*;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class ChopsticksIntegrationTest {
//    List<Integer> player1States = new ArrayList<>(List.of(1));
//    List<Integer> player1Direction = new ArrayList<>(List.of(-1));
//    List<Integer> player2States = new ArrayList<>(List.of(2));
//    List<Integer> player2Direction = new ArrayList<>(List.of(1));
//    ConvertibleNeighborFinder neighborFinder = new FindConvertibleNeighborAtEndCoord();
//
//    MoveCheck ownPiecePlayer1 = new CheckOwnPiece(player1States);
//    MoveCheck ownPiecePlayer2 = new CheckOwnPiece(player2States);
//    MoveCheck ownNumObjects = new CheckNumObjects(0);
//    MoveCheck neighborNumObjects = new CheckNumObjects(5);
//    MoveCheck checkOpponentPiecePlayer1 = new CheckOpponentPiece(0, player1States);
//    MoveCheck checkOpponentPiecePlayer2 = new CheckOpponentPiece(0, player2States);
//
//    MoveType changeNeighbors = new ChangeNeighborObjects(neighborFinder);
//    MoveType clear = new ClearObjects();
//
//    List<MoveCheck> selfMoveCheckPlayer1 = new ArrayList<>(List.of(ownPiecePlayer1, ownNumObjects));
//    List<MoveCheck> selfMoveCheckPlayer2 = new ArrayList<>(List.of(ownPiecePlayer2, ownNumObjects));
//    List<MoveCheck> neighborMoveCheckPlayer1 = new ArrayList<>(List.of(neighborNumObjects, checkOpponentPiecePlayer1));
//    List<MoveCheck> neighborMoveCheckPlayer2 = new ArrayList<>(List.of(neighborNumObjects, checkOpponentPiecePlayer2));
//    List<MoveType> moveTypes = new ArrayList<>(List.of(changeNeighbors, clear));
//
//    GamePiece topSqaure1 = new GamePiece(3,new Coordinate(0,1),4,player2Direction,selfMoveCheckPlayer2,neighborMoveCheckPlayer2, moveTypes);
//    GamePiece topSqaure2 = new GamePiece(3,new Coordinate(0,2),4,player2Direction,selfMoveCheckPlayer2,neighborMoveCheckPlayer2, moveTypes);
//    GamePiece bottomSqaure1 = new GamePiece(1,new Coordinate(1,1),4,player1Direction,selfMoveCheckPlayer1,neighborMoveCheckPlayer1, moveTypes);
//    GamePiece bottomSqaure2 = new GamePiece(1,new Coordinate(1,2),4,player1Direction,selfMoveCheckPlayer1,neighborMoveCheckPlayer1, moveTypes);
//
//    List<GamePiece> allPieces = new ArrayList<>(List.of(topSqaure1,topSqaure2, bottomSqaure1, bottomSqaure2));
//
//
//    @Test
//    void calculateAllPossibleMovesTop() {
//        //simple test
//        List<Coordinate> moves = new ArrayList<>(List.of(new Coordinate(0,6)));
//        allPieces.remove(topSqaure6);
//        List<Coordinate> movesCalculated = topSqaure6.calculateAllPossibleMoves(allPieces,3);
//        assertEquals(moves, movesCalculated);
//        allPieces.add(topSqaure6);
//
//
//        //test ends in goal
//        moves = new ArrayList<>(List.of(new Coordinate(0,4)));
//        allPieces.remove(topSqaure4);
//        movesCalculated = topSqaure4.calculateAllPossibleMoves(allPieces,3);
//        assertEquals(moves, movesCalculated);
//        allPieces.add(topSqaure4);
//
//        //test wrap around
//        moves = new ArrayList<>(List.of(new Coordinate(0,1)));
//        allPieces.remove(topSqaure1);
//        movesCalculated = topSqaure1.calculateAllPossibleMoves(allPieces,3);
//        assertEquals(moves, movesCalculated);
//        allPieces.add(topSqaure1);
//
//    }
//
//
//    @Test
//    void calculateAllPossibleMovesBottom() {
//        //simple test
//        List<Coordinate> moves = new ArrayList<>(List.of(new Coordinate(1,1)));
//        allPieces.remove(bottomSqaure1);
//        List<Coordinate> movesCalculated = bottomSqaure1.calculateAllPossibleMoves(allPieces,1);
//        assertEquals(moves, movesCalculated);
//        allPieces.add(bottomSqaure1);
//
//
//        //test ends in goal
//        moves = new ArrayList<>(List.of(new Coordinate(1,3)));
//        allPieces.remove(bottomSqaure3);
//        movesCalculated = bottomSqaure3.calculateAllPossibleMoves(allPieces,1);
//        assertEquals(moves, movesCalculated);
//        allPieces.add(bottomSqaure3);
//
//        //test wrap around
//        moves = new ArrayList<>(List.of(new Coordinate(1,6)));
//        allPieces.remove(bottomSqaure6);
//        movesCalculated = bottomSqaure6.calculateAllPossibleMoves(allPieces,1);
//        assertEquals(moves, movesCalculated);
//        allPieces.add(bottomSqaure6);
//
//    }
//
//    @Test
//    void testCalculateAllPossibleWhenNoMoves() {
//        List<Coordinate> moves = new ArrayList<>();
//        //test empty id
//        List<Coordinate> movesCalculated = bottomSqaure1.calculateAllPossibleMoves(allPieces,0);
//        assertEquals(moves, movesCalculated);
//
//        //test opponent id
//        movesCalculated = bottomSqaure1.calculateAllPossibleMoves(allPieces,3);
//        assertEquals(moves, movesCalculated);
//
//        //test moving empty square
//        movesCalculated = emptySquare1.calculateAllPossibleMoves(allPieces,0);
//        assertEquals(moves, movesCalculated);
//        movesCalculated = emptySquare1.calculateAllPossibleMoves(allPieces,1);
//        assertEquals(moves, movesCalculated);
//
//        //test moving goal square
//        movesCalculated = goalPocket1.calculateAllPossibleMoves(allPieces,1);
//        assertEquals(moves, movesCalculated);
//        movesCalculated = goalPocket1.calculateAllPossibleMoves(allPieces,2);
//        assertEquals(moves, movesCalculated);
//    }
//
//    @Test
//    void makeMoveTop() {
//        //test normal move
//        allPieces.remove(topSqaure6);
//        topSqaure6.makeMove(new Coordinate(0,6), allPieces,3);
//        assertEquals(new Coordinate(0,6), topSqaure6.getPosition());
//        assertEquals(0, topSqaure6.getNumObjects());
//        assertEquals(5, topSqaure5.getNumObjects());
//        assertEquals(5, topSqaure4.getNumObjects());
//        assertEquals(5, topSqaure3.getNumObjects());
//        assertEquals(5, topSqaure2.getNumObjects());
//        topSqaure2 = new GamePiece(3,new Coordinate(0,2),4,player2Direction,selfMoveCheckPlayer2,neighborMoveCheck, moveTypesPlayer2);
//        topSqaure3 = new GamePiece(3,new Coordinate(0,3),4,player2Direction,selfMoveCheckPlayer2,neighborMoveCheck, moveTypesPlayer2);
//        topSqaure4 = new GamePiece(3,new Coordinate(0,4),4,player2Direction,selfMoveCheckPlayer2,neighborMoveCheck, moveTypesPlayer2);
//        topSqaure5 = new GamePiece(3,new Coordinate(0,5),4,player2Direction,selfMoveCheckPlayer2,neighborMoveCheck, moveTypesPlayer2);
//        topSqaure6 = new GamePiece(3,new Coordinate(0,6),4,player2Direction,selfMoveCheckPlayer2,neighborMoveCheck, moveTypesPlayer2);
//
//        allPieces.add(topSqaure6);
//        allPieces = new ArrayList<>(List.of(goalPocket1,goalPocket2,emptySquare1,emptySquare2,topSqaure1,topSqaure2,topSqaure3,topSqaure4,
//                topSqaure5,topSqaure6, bottomSqaure1, bottomSqaure2, bottomSqaure3,bottomSqaure4,bottomSqaure5,bottomSqaure6));
//
//        //test move that ends in goal state
//        allPieces.remove(topSqaure4);
//        topSqaure4.makeMove(new Coordinate(0,4), allPieces,3);
//        assertEquals(new Coordinate(0,4), topSqaure4.getPosition());
//        assertEquals(0, topSqaure4.getNumObjects());
//        assertEquals(5, topSqaure3.getNumObjects());
//        assertEquals(5, topSqaure2.getNumObjects());
//        assertEquals(5, topSqaure1.getNumObjects());
//        assertEquals(1, goalPocket1.getNumObjects());
//        topSqaure2 = new GamePiece(3,new Coordinate(0,2),4,player2Direction,selfMoveCheckPlayer2,neighborMoveCheck, moveTypesPlayer2);
//        topSqaure3 = new GamePiece(3,new Coordinate(0,3),4,player2Direction,selfMoveCheckPlayer2,neighborMoveCheck, moveTypesPlayer2);
//        topSqaure4 = new GamePiece(3,new Coordinate(0,4),4,player2Direction,selfMoveCheckPlayer2,neighborMoveCheck, moveTypesPlayer2);
//        topSqaure1 = new GamePiece(3,new Coordinate(0,1),4,player2Direction,selfMoveCheckPlayer2,neighborMoveCheck, moveTypesPlayer2);
//        goalPocket1 = new GamePiece(4,new Coordinate(0,0),0,player2Direction,selfMoveCheckPlayer2,neighborMoveCheck, moveTypesPlayer2);
//        allPieces.add(topSqaure4);
//        allPieces = new ArrayList<>(List.of(goalPocket1,goalPocket2,emptySquare1,emptySquare2,topSqaure1,topSqaure2,topSqaure3,topSqaure4,
//                topSqaure5,topSqaure6, bottomSqaure1, bottomSqaure2, bottomSqaure3,bottomSqaure4,bottomSqaure5,bottomSqaure6));
//
//        //test wrap around
//        allPieces.remove(topSqaure1);
//        topSqaure1.makeMove(new Coordinate(0,1), allPieces,3);
//        assertEquals(new Coordinate(0,1), topSqaure1.getPosition());
//        assertEquals(0, topSqaure1.getNumObjects());
//        assertEquals(5, bottomSqaure1.getNumObjects());
//        assertEquals(5, bottomSqaure2.getNumObjects());
//        assertEquals(5, bottomSqaure3.getNumObjects());
//        assertEquals(1, goalPocket1.getNumObjects());
//        bottomSqaure1 = new GamePiece(1,new Coordinate(1,1),4,player1Direction,selfMoveCheckPlayer1,neighborMoveCheck, moveTypesPlayer1);
//        bottomSqaure2 = new GamePiece(1,new Coordinate(1,2),4,player1Direction,selfMoveCheckPlayer1,neighborMoveCheck, moveTypesPlayer1);
//        bottomSqaure3 = new GamePiece(1,new Coordinate(1,3),4,player1Direction,selfMoveCheckPlayer1,neighborMoveCheck, moveTypesPlayer1);
//        topSqaure1 = new GamePiece(3,new Coordinate(0,1),4,player2Direction,selfMoveCheckPlayer2,neighborMoveCheck, moveTypesPlayer2);
//        goalPocket1 = new GamePiece(4,new Coordinate(0,0),0,player2Direction,selfMoveCheckPlayer2,neighborMoveCheck, moveTypesPlayer2);
//
//        allPieces.add(topSqaure1);
//        allPieces = new ArrayList<>(List.of(goalPocket1,goalPocket2,emptySquare1,emptySquare2,topSqaure1,topSqaure2,topSqaure3,topSqaure4,
//                topSqaure5,topSqaure6, bottomSqaure1, bottomSqaure2, bottomSqaure3,bottomSqaure4,bottomSqaure5,bottomSqaure6));
//
//    }
//
//    @Test
//    void makeMoveBottom() {
//        //test normal move
//        allPieces.remove(bottomSqaure1);
//        bottomSqaure1.makeMove(new Coordinate(1,1), allPieces,1);
//        assertEquals(new Coordinate(1,1), bottomSqaure1.getPosition());
//        assertEquals(0, bottomSqaure1.getNumObjects());
//        assertEquals(5, bottomSqaure2.getNumObjects());
//        assertEquals(5, bottomSqaure3.getNumObjects());
//        assertEquals(5, bottomSqaure4.getNumObjects());
//        assertEquals(5, bottomSqaure5.getNumObjects());
//        bottomSqaure1 = new GamePiece(1,new Coordinate(1,1),4,player1Direction,selfMoveCheckPlayer1,neighborMoveCheck, moveTypesPlayer1);
//        bottomSqaure2 = new GamePiece(1,new Coordinate(1,2),4,player1Direction,selfMoveCheckPlayer1,neighborMoveCheck, moveTypesPlayer1);
//        bottomSqaure3 = new GamePiece(1,new Coordinate(1,3),4,player1Direction,selfMoveCheckPlayer1,neighborMoveCheck, moveTypesPlayer1);
//        bottomSqaure4 = new GamePiece(1,new Coordinate(1,4),4,player1Direction,selfMoveCheckPlayer1,neighborMoveCheck, moveTypesPlayer1);
//        bottomSqaure5 = new GamePiece(1,new Coordinate(1,5),4,player1Direction,selfMoveCheckPlayer1,neighborMoveCheck, moveTypesPlayer1);
//
//        allPieces.add(bottomSqaure1);
//        allPieces = new ArrayList<>(List.of(goalPocket1,goalPocket2,emptySquare1,emptySquare2,topSqaure1,topSqaure2,topSqaure3,topSqaure4,
//                topSqaure5,topSqaure6, bottomSqaure1, bottomSqaure2, bottomSqaure3,bottomSqaure4,bottomSqaure5,bottomSqaure6));
//
//        //test move that ends in goal state
//        allPieces.remove(bottomSqaure3);
//        bottomSqaure3.makeMove(new Coordinate(1,3), allPieces,1);
//        assertEquals(new Coordinate(1,3), bottomSqaure3.getPosition());
//        assertEquals(0, bottomSqaure3.getNumObjects());
//        assertEquals(5, bottomSqaure4.getNumObjects());
//        assertEquals(5, bottomSqaure5.getNumObjects());
//        assertEquals(5, bottomSqaure6.getNumObjects());
//        assertEquals(1, goalPocket2.getNumObjects());
//        bottomSqaure3 = new GamePiece(1,new Coordinate(1,3),4,player1Direction,selfMoveCheckPlayer1,neighborMoveCheck, moveTypesPlayer1);
//        bottomSqaure4 = new GamePiece(1,new Coordinate(1,4),4,player1Direction,selfMoveCheckPlayer1,neighborMoveCheck, moveTypesPlayer1);
//        bottomSqaure5 = new GamePiece(1,new Coordinate(1,5),4,player1Direction,selfMoveCheckPlayer1,neighborMoveCheck, moveTypesPlayer1);
//        bottomSqaure6 = new GamePiece(1,new Coordinate(1,6),4,player1Direction,selfMoveCheckPlayer1,neighborMoveCheck, moveTypesPlayer1);
//        goalPocket2 = new GamePiece(2,new Coordinate(1,7),0,player1Direction,selfMoveCheckPlayer1,neighborMoveCheck, moveTypesPlayer1);
//        allPieces.add(bottomSqaure3);
//        allPieces = new ArrayList<>(List.of(goalPocket1,goalPocket2,emptySquare1,emptySquare2,topSqaure1,topSqaure2,topSqaure3,topSqaure4,
//                topSqaure5,topSqaure6, bottomSqaure1, bottomSqaure2, bottomSqaure3,bottomSqaure4,bottomSqaure5,bottomSqaure6));
//
//        //test wrap around
//        allPieces.remove(bottomSqaure6);
//        bottomSqaure6.makeMove(new Coordinate(1,6), allPieces,1);
//        assertEquals(new Coordinate(1,6), bottomSqaure6.getPosition());
//        assertEquals(0, bottomSqaure6.getNumObjects());
//        assertEquals(1, goalPocket2.getNumObjects());
//        assertEquals(5, topSqaure6.getNumObjects());
//        assertEquals(5, topSqaure5.getNumObjects());
//        assertEquals(5, topSqaure4.getNumObjects());
//        bottomSqaure6 = new GamePiece(1,new Coordinate(1,6),4,player1Direction,selfMoveCheckPlayer1,neighborMoveCheck, moveTypesPlayer1);
//        goalPocket2 = new GamePiece(2,new Coordinate(1,7),0,player1Direction,selfMoveCheckPlayer1,neighborMoveCheck, moveTypesPlayer1);
//        topSqaure4 = new GamePiece(3,new Coordinate(0,4),4,player2Direction,selfMoveCheckPlayer2,neighborMoveCheck, moveTypesPlayer2);
//        topSqaure5 = new GamePiece(3,new Coordinate(0,5),4,player2Direction,selfMoveCheckPlayer2,neighborMoveCheck, moveTypesPlayer2);
//        topSqaure6 = new GamePiece(3,new Coordinate(0,6),4,player2Direction,selfMoveCheckPlayer2,neighborMoveCheck, moveTypesPlayer2);
//        allPieces.add(bottomSqaure6);
//        allPieces = new ArrayList<>(List.of(goalPocket1,goalPocket2,emptySquare1,emptySquare2,topSqaure1,topSqaure2,topSqaure3,topSqaure4,
//                topSqaure5,topSqaure6, bottomSqaure1, bottomSqaure2, bottomSqaure3,bottomSqaure4,bottomSqaure5,bottomSqaure6));
//
//    }
//    @Test
//    void makeMoveSpecialCapture() {
//        bottomSqaure1 = new GamePiece(1,new Coordinate(1,1),2,player1Direction,selfMoveCheckPlayer1,neighborMoveCheck, moveTypesPlayer1);
//        bottomSqaure2 = new GamePiece(1,new Coordinate(1,2),4,player1Direction,selfMoveCheckPlayer1,neighborMoveCheck, moveTypesPlayer1);
//        bottomSqaure3 = new GamePiece(1,new Coordinate(1,3),0,player1Direction,selfMoveCheckPlayer1,neighborMoveCheck, moveTypesPlayer1);
//        goalPocket2 = new GamePiece(2,new Coordinate(1,7),0,player1Direction,selfMoveCheckPlayer1,neighborMoveCheck, moveTypesPlayer1);
//        topSqaure3 = new GamePiece(3,new Coordinate(0,3),4,player2Direction,selfMoveCheckPlayer2,neighborMoveCheck, moveTypesPlayer2);
//        allPieces = new ArrayList<>(List.of(goalPocket1,goalPocket2,emptySquare1,emptySquare2,topSqaure1,topSqaure2,topSqaure3,topSqaure4,
//                topSqaure5,topSqaure6, bottomSqaure1, bottomSqaure2, bottomSqaure3,bottomSqaure4,bottomSqaure5,bottomSqaure6));
//        bottomSqaure1.makeMove(new Coordinate(1,1), allPieces,1);
//        assertEquals(new Coordinate(1,1), bottomSqaure1.getPosition());
//        assertEquals(0, bottomSqaure1.getNumObjects());
//        assertEquals(5, goalPocket2.getNumObjects());
//        assertEquals(5, bottomSqaure2.getNumObjects());
//        assertEquals(0, bottomSqaure3.getNumObjects());
//        assertEquals(0, topSqaure3.getNumObjects());
//
//    }
//
//}
