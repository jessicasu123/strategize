package ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NeighborAtEndCoordinateFinderTest {
    NeighborAtEndCoordinateFinder endCoordNeighborFinder = new NeighborAtEndCoordinateFinder();
    int numObjects = 3;
    List<Integer> directionList = new ArrayList<>(List.of(1));
    Coordinate topLeftCoord = new Coordinate(0, 0);
    Coordinate topRightCoord = new Coordinate(0, 1);
    Coordinate bottomLeftCoord = new Coordinate(1, 0);
    Coordinate bottomRightCoord = new Coordinate(1, 1);

    GamePiece topLeftPiece = new GamePiece(2, topLeftCoord, numObjects, directionList, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    GamePiece topRightPiece = new GamePiece(2, topRightCoord, numObjects, directionList, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    GamePiece bottomLeftPiece = new GamePiece(1, bottomLeftCoord, numObjects, directionList, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    GamePiece bottomRightPiece = new GamePiece(1, bottomRightCoord, numObjects, directionList, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

    @Test
    void findNeighborsForTop() {
        // curr = top left
        assertEquals(new ArrayList<>(List.of(topRightPiece)), endCoordNeighborFinder.findNeighborsToConvert(topLeftCoord, topRightCoord, numObjects, 1, 1, new ArrayList<>(List.of(bottomLeftPiece, topRightPiece, bottomRightPiece))));
        assertEquals(new ArrayList<>(List.of(bottomLeftPiece)), endCoordNeighborFinder.findNeighborsToConvert(topLeftCoord, bottomLeftCoord, numObjects, 1, 1, new ArrayList<>(List.of(bottomLeftPiece, topRightPiece, bottomRightPiece))));
        assertEquals(new ArrayList<>(List.of(bottomRightPiece)), endCoordNeighborFinder.findNeighborsToConvert(topLeftCoord, bottomRightCoord, numObjects, 1, 1, new ArrayList<>(List.of(bottomLeftPiece, topRightPiece, bottomRightPiece))));

        // curr = top right
        assertEquals(new ArrayList<>(List.of(topLeftPiece)), endCoordNeighborFinder.findNeighborsToConvert(topRightCoord, topLeftCoord, numObjects, 1, 1, new ArrayList<>(List.of(bottomLeftPiece, topLeftPiece, bottomRightPiece))));
        assertEquals(new ArrayList<>(List.of(bottomLeftPiece)), endCoordNeighborFinder.findNeighborsToConvert(topRightCoord, bottomLeftCoord, numObjects, 1, 1, new ArrayList<>(List.of(bottomLeftPiece, topLeftPiece, bottomRightPiece))));
        assertEquals(new ArrayList<>(List.of(bottomRightPiece)), endCoordNeighborFinder.findNeighborsToConvert(topRightCoord, bottomRightCoord, numObjects, 1, 1, new ArrayList<>(List.of(bottomLeftPiece, topLeftPiece, bottomRightPiece))));
    }

    @Test
    void findNeighborsForBottom() {
        // curr = bottom left
        assertEquals(new ArrayList<>(List.of(topLeftPiece)), endCoordNeighborFinder.findNeighborsToConvert(bottomLeftCoord, topLeftCoord, numObjects, 1, 1, new ArrayList<>(List.of(bottomRightPiece, topLeftPiece, topRightPiece))));
        assertEquals(new ArrayList<>(List.of(topRightPiece)), endCoordNeighborFinder.findNeighborsToConvert(bottomLeftCoord, topRightCoord, numObjects, 1, 1, new ArrayList<>(List.of(bottomRightPiece, topLeftPiece, topRightPiece))));
        assertEquals(new ArrayList<>(List.of(bottomRightPiece)), endCoordNeighborFinder.findNeighborsToConvert(bottomLeftCoord, bottomRightCoord, numObjects, 1, 1, new ArrayList<>(List.of(bottomRightPiece, topLeftPiece, topRightPiece))));

        // curr = bottom right
        assertEquals(new ArrayList<>(List.of(topLeftPiece)), endCoordNeighborFinder.findNeighborsToConvert(bottomRightCoord, topLeftCoord, numObjects, 1, 1, new ArrayList<>(List.of(bottomLeftPiece, topLeftPiece, topRightPiece))));
        assertEquals(new ArrayList<>(List.of(topRightPiece)), endCoordNeighborFinder.findNeighborsToConvert(bottomRightCoord, topRightCoord, numObjects, 1, 1, new ArrayList<>(List.of(bottomLeftPiece, topLeftPiece, topRightPiece))));
        assertEquals(new ArrayList<>(List.of(bottomLeftPiece)), endCoordNeighborFinder.findNeighborsToConvert(bottomRightCoord, bottomLeftCoord, numObjects, 1, 1, new ArrayList<>(List.of(bottomLeftPiece, topLeftPiece, topRightPiece))));
    }
}