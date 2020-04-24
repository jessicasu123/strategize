package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.MancalaGamePiece;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.ConvertibleNeighborFinder;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.FindConvertibleNeighborAtEndCoord;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.FindNeighborsUntilNoObjects;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChangeNeighborObjectsTest {
    Coordinate c1 = new Coordinate(0,1);
    Coordinate c2 = new Coordinate(1,1);

    List<Integer> directions = new ArrayList<>(List.of(1));
    GamePiece piece1 = new GamePiece(1, c1,3, directions, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    GamePiece piece2 = new GamePiece(2, c2,1, directions, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

    FindConvertibleNeighborAtEndCoord neighborAtEndCoordConverter = new FindConvertibleNeighborAtEndCoord();
    ChangeNeighborObjects changeSingleNeighborObjects = new ChangeNeighborObjects(neighborAtEndCoordConverter);

    List<GamePiece> neighbors = new ArrayList<>(List.of(piece1, piece2));

    List<Integer> posDir = new ArrayList<>(List.of(1));
    List<Integer> negDir = new ArrayList<>(List.of(-1));
    List<Integer> noDir = new ArrayList<>(List.of(0));

    Coordinate coord00 = new Coordinate(0,0);
    Coordinate coord01 = new Coordinate(0,1);
    Coordinate coord02 = new Coordinate(0,2);
    Coordinate coord03 = new Coordinate(0,3);
    Coordinate coord04 = new Coordinate(0,4);
    Coordinate coord05 = new Coordinate(0,5);
    Coordinate coord06 = new Coordinate(0,6);
    Coordinate coord07 = new Coordinate(0,7);

    Coordinate coord10 = new Coordinate(1,0);
    Coordinate coord11 = new Coordinate(1,1);
    Coordinate coord12 = new Coordinate(1,2);
    Coordinate coord13 = new Coordinate(1,3);
    Coordinate coord14 = new Coordinate(1,4);
    Coordinate coord15 = new Coordinate(1,5);
    Coordinate coord16 = new Coordinate(1,6);
    Coordinate coord17 = new Coordinate(1,7);

    GamePiece goal1 = new GamePiece(4,coord00, 0,negDir,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece goal2 = new GamePiece(2, coord17, 0,posDir, new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    GamePiece empty1= new GamePiece(0,coord07,0, noDir,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece empty2 = new GamePiece(0,coord10,0, noDir,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    GamePiece topSquare1 = new GamePiece(3,coord01, 4,negDir,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece topSquare2 = new GamePiece(3,coord02, 4,negDir,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece topSquare3 = new GamePiece(3,coord03, 4,negDir,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece topSquare4 = new GamePiece(3,coord04, 4,negDir, new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece topSquare5 = new GamePiece(3,coord05, 4,negDir, new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece topSquare6 = new GamePiece(3,coord06, 4,negDir,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());


    GamePiece bottomSquare1 = new GamePiece(1,coord11, 4,posDir, new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece bottomSquare2 = new GamePiece(1,coord12, 4,posDir,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece bottomSquare3 = new GamePiece(1,coord13,4, posDir,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece bottomSquare4 = new GamePiece(1,coord14, 4,posDir,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece bottomSquare5 = new GamePiece(1,coord15, 4,posDir, new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece bottomSquare6 = new GamePiece(1,coord16,4, posDir, new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    List<GamePiece> allPieces = new ArrayList<>(List.of(goal1,goal2,empty1,empty2,topSquare1,topSquare2,topSquare3,topSquare4,
            topSquare5,topSquare6, bottomSquare1, bottomSquare2, bottomSquare3,bottomSquare4,bottomSquare5,bottomSquare6));

    List<Integer> toIgnore = new ArrayList<>(List.of(0,4));
    ConvertibleNeighborFinder neighborsUntilNoObjects = new FindNeighborsUntilNoObjects(toIgnore);
    ChangeNeighborObjects changeMultipleNeighborObjects = new ChangeNeighborObjects(neighborsUntilNoObjects);


    @Test
    void completeMoveType() {
        changeSingleNeighborObjects.completeMoveType(piece1, c2, neighbors, 1, 1);
        assertEquals(4, piece2.getNumObjects());
        assertEquals(3, piece1.getNumObjects());

        allPieces.remove(topSquare6);
        changeMultipleNeighborObjects.completeMoveType(topSquare1, new Coordinate(0,6), allPieces, 3, 1);
        topSquare6.makeMove(new Coordinate(0,6), allPieces,3);
        assertEquals(new Coordinate(0,6), topSquare6.getPosition());
        assertEquals(5, topSquare5.getNumObjects());
        assertEquals(5, topSquare4.getNumObjects());
        assertEquals(5, topSquare3.getNumObjects());
        assertEquals(5, topSquare2.getNumObjects());

    }
}