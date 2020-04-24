//package ooga.model.engine.pieces.newPieces.IntegrationTests;
//
//import ooga.model.engine.Coordinate;
//import ooga.model.engine.pieces.newPieces.*;
//import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.ConvertibleNeighborFinder;
//import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.FindConvertibleNeighborAtEndCoord;
//import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.FindNeighborsUntilNoObjects;
//import ooga.model.engine.pieces.newPieces.MoveChecks.CheckNotImmovable;
//import ooga.model.engine.pieces.newPieces.MoveChecks.CheckNumObjects;
//import ooga.model.engine.pieces.newPieces.MoveChecks.CheckOwnPiece;
//import ooga.model.engine.pieces.newPieces.MoveChecks.MoveCheck;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class ChopsticksIntegrationTest {
//    List<Integer> player1 = new ArrayList<>(List.of(1,2));
//    List<Integer> player1Direction = new ArrayList<>(List.of(-1));
//    List<Integer> player2 = new ArrayList<>(List.of(3,4));
//    List<Integer> player2Direction = new ArrayList<>(List.of(1));
//    List<Integer> player1StatesToIgnore = new ArrayList<>(List.of());
//    List<Integer> player2StatesToIgnore = new ArrayList<>(List.of());
//    ConvertibleNeighborFinder player1Finder = new FindConvertibleNeighborAtEndCoord();
//    ConvertibleNeighborFinder player2Finder = new FindConvertibleNeighborAtEndCoord();
//
//    MoveCheck ownPiecePlayer1 = new CheckOwnPiece(player1);
//    MoveCheck ownPiecePlayer2 = new CheckOwnPiece(player2);
//    MoveCheck immovablePlayer1 = new CheckNotImmovable(-1);
//    MoveCheck immovablePlayer2 = new CheckNotImmovable(-1);
//    MoveCheck numObjectsCheck = new CheckNumObjects(0);
//
//    MoveType forceMoveAgainPlayer1 = new ForceMoveAgain(player1,player1Finder);
//    MoveType forceMoveAgainPlayer2 = new ForceMoveAgain(player2,player2Finder);
//    MoveType specialCapturePlayer1 = new SpecialCapture(player1,player1Finder);
//    MoveType specialCapturePlayer2 = new SpecialCapture(player2,player2Finder);
//    MoveType changeNeighborsPlayer1 = new ChangeNeighborObjects(player1Finder);
//    MoveType changeNeighborsPlayer2 = new ChangeNeighborObjects(player2Finder);
//    MoveType clear = new ClearObjects();
//
//    List<MoveCheck> selfMoveCheckPlayer1 = new ArrayList<>(List.of(ownPiecePlayer1, immovablePlayer1, numObjectsCheck));
//    List<MoveCheck> selfMoveCheckPlayer2 = new ArrayList<>(List.of(ownPiecePlayer2, immovablePlayer2, numObjectsCheck));
//    List<MoveCheck> neighborMoveCheck = new ArrayList<>();
//    List<MoveType> moveTypesPlayer1 = new ArrayList<>(List.of(forceMoveAgainPlayer1, changeNeighborsPlayer1,specialCapturePlayer1,clear));
//    List<MoveType> moveTypesPlayer2 = new ArrayList<>(List.of(forceMoveAgainPlayer2, changeNeighborsPlayer2,specialCapturePlayer2,clear));
//
//
//    GamePiece goalPocket1 = new GamePiece(4,new Coordinate(0,0),0,player2Direction,selfMoveCheckPlayer2,neighborMoveCheck, moveTypesPlayer2);
//    GamePiece goalPocket2 = new GamePiece(2,new Coordinate(1,7),0,player1Direction,selfMoveCheckPlayer1,neighborMoveCheck, moveTypesPlayer1);
//
//    GamePiece emptySquare1 = new GamePiece(0,new Coordinate(0,7),0,player2Direction,selfMoveCheckPlayer2,neighborMoveCheck, new ArrayList<>());
//    GamePiece emptySquare2 = new GamePiece(0,new Coordinate(1,0),0,player1Direction,selfMoveCheckPlayer1,neighborMoveCheck, new ArrayList<>());
//
//    GamePiece topSqaure1 = new GamePiece(3,new Coordinate(0,1),4,player2Direction,selfMoveCheckPlayer2,neighborMoveCheck, moveTypesPlayer2);
//    GamePiece topSqaure2 = new GamePiece(3,new Coordinate(0,2),4,player2Direction,selfMoveCheckPlayer2,neighborMoveCheck, moveTypesPlayer2);
//    GamePiece topSqaure3 = new GamePiece(3,new Coordinate(0,3),4,player2Direction,selfMoveCheckPlayer2,neighborMoveCheck, moveTypesPlayer2);
//    GamePiece topSqaure4 = new GamePiece(3,new Coordinate(0,4),4,player2Direction,selfMoveCheckPlayer2,neighborMoveCheck, moveTypesPlayer2);
//    GamePiece topSqaure5 = new GamePiece(3,new Coordinate(0,5),4,player2Direction,selfMoveCheckPlayer2,neighborMoveCheck, moveTypesPlayer2);
//    GamePiece topSqaure6 = new GamePiece(3,new Coordinate(0,6),4,player2Direction,selfMoveCheckPlayer2,neighborMoveCheck, moveTypesPlayer2);
//
//    GamePiece bottomSqaure1 = new GamePiece(1,new Coordinate(1,1),4,player1Direction,selfMoveCheckPlayer1,neighborMoveCheck, moveTypesPlayer1);
//    GamePiece bottomSqaure2 = new GamePiece(1,new Coordinate(1,2),4,player1Direction,selfMoveCheckPlayer1,neighborMoveCheck, moveTypesPlayer1);
//    GamePiece bottomSqaure3 = new GamePiece(1,new Coordinate(1,3),4,player1Direction,selfMoveCheckPlayer1,neighborMoveCheck, moveTypesPlayer1);
//    GamePiece bottomSqaure4 = new GamePiece(1,new Coordinate(1,4),4,player1Direction,selfMoveCheckPlayer1,neighborMoveCheck, moveTypesPlayer1);
//    GamePiece bottomSqaure5 = new GamePiece(1,new Coordinate(1,5),4,player1Direction,selfMoveCheckPlayer1,neighborMoveCheck, moveTypesPlayer1);
//    GamePiece bottomSqaure6 = new GamePiece(1,new Coordinate(1,6),4,player1Direction,selfMoveCheckPlayer1,neighborMoveCheck, moveTypesPlayer1);
//
//    List<GamePiece> allPieces = new ArrayList<>(List.of(goalPocket1,goalPocket2,emptySquare1,emptySquare2,topSqaure1,topSqaure2,topSqaure3,topSqaure4,
//            topSqaure5,topSqaure6, bottomSqaure1, bottomSqaure2, bottomSqaure3,bottomSqaure4,bottomSqaure5,bottomSqaure6));
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
