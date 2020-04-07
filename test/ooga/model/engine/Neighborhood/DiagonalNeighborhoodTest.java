package ooga.model.engine.Neighborhood;

import ooga.model.engine.Coordinate;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class DiagonalNeighborhoodTest {
    DiagonalNeighborhood diagonalNeighborhood = new DiagonalNeighborhood(4,4);

    @Test
    void testRightCenterDiag() {
        List<Coordinate> rightDiag = diagonalNeighborhood.getNeighbors(1,1);
        List<Coordinate> desiredCoords = new ArrayList<>();
        //all the coordinates on the right center diagonal
        List<Coordinate> rightCenterDiag = Arrays.asList(new Coordinate[]{new Coordinate(0, 0),
                new Coordinate(2, 2), new Coordinate(3, 3)});
        //all the coordinates on the left diagonal
        List<Coordinate> leftDiag = Arrays.asList(new Coordinate[]{new Coordinate(0, 2),
                new Coordinate(2, 0)});
        desiredCoords.addAll(rightCenterDiag);
        desiredCoords.addAll(leftDiag);
        assertEquals(rightDiag, desiredCoords);
    }

    @Test
    void testLeftCenterDiag() {
        List<Coordinate> leftDiag = diagonalNeighborhood.getNeighbors(1,2);
        List<Coordinate> desiredCoords = new ArrayList<>();
        //all the coordinates on the left center diagonal
        List<Coordinate> leftCenterDiag = Arrays.asList(new Coordinate[]{new Coordinate(0, 3),
                new Coordinate(2, 1), new Coordinate(3, 0)});
        //all the coordinates on the right diagonal
        List<Coordinate> rightDiag = Arrays.asList(new Coordinate[]{new Coordinate(0, 1),
                new Coordinate(2, 3)});
        desiredCoords.addAll(rightDiag);
        desiredCoords.addAll(leftCenterDiag);
        assertEquals(leftDiag, desiredCoords);
    }

    @Test
    void testDiagAboveCenter() {
        List<Coordinate> aboveCenterDiag = diagonalNeighborhood.getNeighbors(0,2);
        List<Coordinate> desiredCoords = new ArrayList<>();
        //all the coordinates on the right diagonal
        List<Coordinate> rightDiag = Arrays.asList(new Coordinate[]{new Coordinate(1, 3),});
        //all the coordinates on the left diagonal
        List<Coordinate> leftDiag = Arrays.asList(new Coordinate[]{new Coordinate(1, 1),
                new Coordinate(2, 0)});
        desiredCoords.addAll(rightDiag);
        desiredCoords.addAll(leftDiag);
        assertEquals(aboveCenterDiag, desiredCoords);
    }

    @Test
    void testDiagBelowCenter() {
        List<Coordinate> belowCenterDiag = diagonalNeighborhood.getNeighbors(2,0);
        List<Coordinate> desiredCoords = new ArrayList<>();
        //all the coordinates on the right diagonal
        List<Coordinate> rightDiag = Arrays.asList(new Coordinate[]{new Coordinate(3, 1),});
        //all the coordinates on the left diagonal
        List<Coordinate> leftDiag = Arrays.asList(new Coordinate[]{new Coordinate(0, 2),
                new Coordinate(1, 1)});
        desiredCoords.addAll(rightDiag);
        desiredCoords.addAll(leftDiag);
        assertEquals(belowCenterDiag, desiredCoords);
    }


    @Test
    void testTopRightCorner() {
        //left diag but no right diag
        List<Coordinate> topRight = diagonalNeighborhood.getNeighbors(0,3);
        List<Coordinate> desiredCoords = new ArrayList<>();
        //all the coordinates on the left diagonal
        List<Coordinate> leftDiag = Arrays.asList(new Coordinate[]{new Coordinate(1, 2),
                new Coordinate(2, 1), new Coordinate(3, 0)});
        desiredCoords.addAll(leftDiag);
        assertEquals(topRight, desiredCoords);
    }

    @Test
    void testBottomRightCorner() {
        //right diag but no left diag
        List<Coordinate> bottomRight = diagonalNeighborhood.getNeighbors(3,3);
        List<Coordinate> desiredCoords = new ArrayList<>();
        //all the coordinates on the right diagonal
        List<Coordinate> rightDiag = Arrays.asList(new Coordinate[]{new Coordinate(0, 0),
                new Coordinate(1, 1), new Coordinate(2, 2)});
        desiredCoords.addAll(rightDiag);
        assertEquals(bottomRight, desiredCoords);
    }


}
