package ooga.model.engine.Neighborhood;

import ooga.model.engine.Coordinate;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class VerticalNeighborhoodTest {
    VerticalNeighborhood verticalNeighborhoodSquare = new VerticalNeighborhood(3,3);
    VerticalNeighborhood verticalNeighborhoodRect = new VerticalNeighborhood(3,4);

    @Test
    void testFirstInColumn() {
        List<Coordinate> firstCoords = verticalNeighborhoodSquare.getNeighbors(0, 0);
        //neighbors should be the other bottom two in column - (1,0) and (2,0)
        List<Coordinate> desiredCoords = Arrays.asList(new Coordinate[]{new Coordinate(1, 0),
                new Coordinate(2, 0)});
        assertEquals(firstCoords, desiredCoords);
    }

    @Test
    void testMiddleInColumn() {
        List<Coordinate> middleCoords = verticalNeighborhoodSquare.getNeighbors(1, 0);
        //neighbors should be the other bottom two in column - (1,0) and (2,0)
        List<Coordinate> desiredCoords = Arrays.asList(new Coordinate[]{new Coordinate(0, 0),
                new Coordinate(2, 0)});
        assertEquals(middleCoords, desiredCoords);
    }

    @Test
    void testBottomInColumn() {
        List<Coordinate> bottomCoords = verticalNeighborhoodSquare.getNeighbors(2, 0);
        //neighbors should be the other bottom two in column - (1,0) and (2,0)
        List<Coordinate> desiredCoords = Arrays.asList(new Coordinate[]{new Coordinate(0, 0),
                new Coordinate(1, 0)});
        assertEquals(bottomCoords, desiredCoords);
    }

    @Test
    void testColumnInRectangularBoard() {
        List<Coordinate> bottomCoords = verticalNeighborhoodRect.getNeighbors(0, 0);
        //neighbors should be the other bottom two in column - (1,0) and (2,0)
        List<Coordinate> desiredCoords = Arrays.asList(new Coordinate[]{new Coordinate(1, 0),
                new Coordinate(2, 0)});
        assertEquals(bottomCoords, desiredCoords);
    }

    }
