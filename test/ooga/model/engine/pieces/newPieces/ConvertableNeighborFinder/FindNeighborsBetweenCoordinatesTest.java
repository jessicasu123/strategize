package ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

class FindNeighborsBetweenCoordinatesTest {
    FindNeighborsBetweenCoordinates myFinder = new FindNeighborsBetweenCoordinates();
    List<Integer> direction = new ArrayList<>(List.of(1));
    //main diagonals
    Coordinate coord00 = new Coordinate(0,0);
    GamePiece piece00 = new GamePiece(1,coord00,1,direction,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    Coordinate coord11 = new Coordinate(1,1);
    GamePiece piece11 = new GamePiece(0,coord11,1,direction,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    Coordinate coord22 = new Coordinate(2,2);
    GamePiece piece22 = new GamePiece(0,coord22,1,direction,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    Coordinate coord33 = new Coordinate(3,3);
    GamePiece piece33 = new GamePiece(0,coord33,1,direction,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    Coordinate coord44 = new Coordinate(4,4);
    GamePiece piece44 = new GamePiece(0,coord44,1,direction,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    Coordinate coord55 = new Coordinate(5,5);
    GamePiece piece55 = new GamePiece(0,coord55,1,direction,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    Coordinate coord50 = new Coordinate(5,0);
    GamePiece piece50 = new GamePiece(0,coord50,1,direction,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    Coordinate coord41 = new Coordinate(4,1);
    GamePiece piece41 = new GamePiece(0,coord41,1,direction,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    Coordinate coord32 = new Coordinate(3,2);
    GamePiece piece32 = new GamePiece(0,coord32,1,direction,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    Coordinate coord23 = new Coordinate(2,3);
    GamePiece piece23 = new GamePiece(0,coord23,1,direction,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    Coordinate coord14 = new Coordinate(1,4);
    GamePiece piece14 = new GamePiece(0,coord14,1,direction,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    Coordinate coord05 = new Coordinate(0,5);
    GamePiece piece05 = new GamePiece(0,coord05,1,direction,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    //a few random other squares
    Coordinate coord31 = new Coordinate(3,1);
    GamePiece piece31 = new GamePiece(0,coord31,1,direction,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    Coordinate coord43 = new Coordinate(4,3);
    GamePiece piece43 = new GamePiece(0,coord43,1,direction,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    Coordinate coord21 = new Coordinate(2,1);
    GamePiece piece21 = new GamePiece(0,coord21,1,direction,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    Coordinate coord10 = new Coordinate(1,0);
    GamePiece piece10 = new GamePiece(0,coord10,1,direction,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    Coordinate coord12 = new Coordinate(1,2);
    GamePiece piece12 = new GamePiece(0,coord12,1,direction,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    Coordinate coord45 = new Coordinate(4,5);
    GamePiece piece45 = new GamePiece(0,coord45,1,direction,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    Coordinate coord54 = new Coordinate(5,4);
    GamePiece piece54 = new GamePiece(0,coord54,1,direction,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    List<GamePiece> allPieces = new ArrayList<>(List.of(piece00, piece11, piece22, piece33, piece44, piece55,
            piece50,piece41,piece32,piece23,piece14,piece05,piece31,piece43,piece21,piece10,piece12,piece45));

    List<GamePiece> onlyDiag1 = new ArrayList<>(List.of(piece10,piece21, piece32,piece43, piece54));

    List<GamePiece> onlyDiag2 = new ArrayList<>(List.of(piece50, piece41,piece32,piece23,piece14,piece05));

    List<GamePiece> onlyRows = new ArrayList<>(List.of(piece41,piece43,piece44,piece45));

    List<GamePiece> onlyCols = new ArrayList<>(List.of(piece41,piece31,piece21,piece11));

    //test getting all neighbors in between (rows, cols, diags)
    @Test
    void findNeighborsToConvertAllPiece() {
        List<GamePiece> toConvert = new ArrayList<>(List.of(piece22, piece33, piece23,piece14, piece12));
        assertEquals(toConvert, myFinder.findNeighborsToConvert(coord32,coord05,0,0,1,allPieces));
        assertEquals(toConvert, myFinder.findNeighborsToConvert(coord05,coord32,0,0,1,allPieces));


        toConvert = new ArrayList<>(List.of(piece21));
        assertEquals(toConvert, myFinder.findNeighborsToConvert(coord32,coord10,0,0,1,allPieces));
        assertEquals(toConvert, myFinder.findNeighborsToConvert(coord10,coord32,0,0,1,allPieces));
    }

    @Test
    void findNeighborsToConvertDiagonalsOnly() {
        List<GamePiece> toConvert = new ArrayList<>(List.of(piece23,piece14));
        assertEquals(toConvert, myFinder.findNeighborsToConvert(coord32,coord05,0,0,1, onlyDiag2));
        assertEquals(toConvert, myFinder.findNeighborsToConvert(coord05,coord32,0,0,1, onlyDiag2));

        toConvert = new ArrayList<>(List.of(piece21,piece32, piece43));
        assertEquals(toConvert, myFinder.findNeighborsToConvert(coord54,coord10,0,0,1, onlyDiag1));
        assertEquals(toConvert, myFinder.findNeighborsToConvert(coord10,coord54,0,0,1, onlyDiag1));

        toConvert = new ArrayList<>(List.of(piece32));
        assertEquals(toConvert, myFinder.findNeighborsToConvert(coord41,coord23,0,0,1, onlyDiag2));
        assertEquals(toConvert, myFinder.findNeighborsToConvert(coord23,coord41,0,0,1, onlyDiag2));

        assertEquals(new ArrayList<>(), myFinder.findNeighborsToConvert(coord23,coord14,0,0,1, onlyDiag2));
        assertEquals(new ArrayList<>(), myFinder.findNeighborsToConvert(coord14,coord23,0,0,1, onlyDiag2));

    }

    @Test
    void findNeighborsToConvertRowOnly() {
        List<GamePiece> toConvert = new ArrayList<>(List.of(piece43,piece44));
        assertEquals(toConvert, myFinder.findNeighborsToConvert(coord41,coord45,0,0,1, onlyRows));
        assertEquals(toConvert, myFinder.findNeighborsToConvert(coord45,coord41,0,0,1, onlyRows));

        toConvert = new ArrayList<>(List.of(piece44));
        assertEquals(toConvert, myFinder.findNeighborsToConvert(coord43,coord45,0,0,1, onlyRows));
        assertEquals(toConvert, myFinder.findNeighborsToConvert(coord45,coord43,0,0, 1,onlyRows));
    }

    @Test
    void findNeighborsToConvertColsOnly() {
        List<GamePiece> toConvert = new ArrayList<>(List.of(piece31,piece21));
        assertEquals(toConvert, myFinder.findNeighborsToConvert(coord41,coord11,0,0,1, onlyCols));
        assertEquals(toConvert, myFinder.findNeighborsToConvert(coord11,coord41,0,0,1, onlyCols));

        toConvert = new ArrayList<>(List.of(piece21));
        assertEquals(toConvert, myFinder.findNeighborsToConvert(coord31,coord11,0,0,1, onlyCols));
        assertEquals(toConvert, myFinder.findNeighborsToConvert(coord11,coord31,0,0,1, onlyCols));
    }

}