package ooga.model.engine.neighborhood;

import ooga.model.engine.Coordinate;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class HorizontalNeighborhoodTest {
    HorizontalNeighborhood horizontalNeighborhood = new HorizontalNeighborhood(3,3);
    HorizontalNeighborhood horizontalRectangle = new HorizontalNeighborhood(3,4);
    @Test
    void testFirstInRow() {
        List<Coordinate> firstCoords = horizontalNeighborhood.getNeighbors(0,0);
        //neighbors should be the other two in top row - (0,1) and (0,2)
        List<Coordinate> desiredCoords = Arrays.asList(new Coordinate[]{new Coordinate(0, 1),
                new Coordinate(0, 2)});
        assertEquals(firstCoords, desiredCoords);
    }

    @Test
    void testMiddleInRow() {
        List<Coordinate> middleCoords = horizontalNeighborhood.getNeighbors(0,1);
        //neighbors should be the first (0,0) and last (0,2)
        List<Coordinate> desiredCoords = Arrays.asList(new Coordinate[]{new Coordinate(0, 0),
                new Coordinate(0, 2)});
        assertEquals(middleCoords, desiredCoords);
    }

    @Test
    void testLastInRow() {
        List<Coordinate> lastCoords = horizontalNeighborhood.getNeighbors(0,2);
        //neighbors should be the first (0,0) and seoncd (0,1)
        List<Coordinate> desiredCoords = Arrays.asList(new Coordinate[]{new Coordinate(0, 0),
                new Coordinate(0, 1)});
        assertEquals(lastCoords, desiredCoords);
    }

    @Test
    void testRowRectangularBoard() {
        List<Coordinate> coords = horizontalRectangle.getNeighbors(0,0);
        List<Coordinate> desiredCoords = Arrays.asList(new Coordinate[]{new Coordinate(0, 1),
                new Coordinate(0, 2), new Coordinate(0, 3)});
        assertTrue(coords.size()==3);
        assertEquals(coords, desiredCoords);
    }
}
