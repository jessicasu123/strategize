package ooga.model.engine.neighborhood;

import ooga.model.engine.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract superclass for a neighborhood (group of coordinates
 * arranged in a certain pattern - ex. horizontally, vertically, diagonally)
 * Each neighborhood that extends this class will find its neighbors differently.
 *
 * @author: Jessica Su
 */
public abstract class Neighborhood {
    protected List<Coordinate> allCoordinates;
    protected int numRows;
    protected int numCols;

    /**
     * Constructor for Neighborhood.
     * @param rows - the number of rows in the Board where neighborhoods needs to be calculated
     * @param cols- the number of columns in the Board where neighborhoods needs to be calculated
     */
    public Neighborhood(int rows, int cols) {
        allCoordinates = new ArrayList<>();
        numRows = rows;
        numCols = cols;
    }

    /**
     * Responsible for finding all the coordinates that are within
     * a certain kind of neighborhood.
     * These neighbors are calculated around, but NOT inclusive of,
     * a certain starting position (r,c).
     * @param r - the row of the current position
     * @param c - the column of the current position
     * @return a list of coordinates that represent the neighbors
     */
    public abstract List<Coordinate> getNeighbors (int r, int c);

    /**
     * Adding a coordinate to the list of all neighbors, EXCLUDING
     * the current coordinate position.
     * @param currRow - the current row position
     * @param currCol - the current column position
     * @param newRow - the potential neighbor row position
     * @param newCol - the potential neighbor column position
     */
    protected void addCoord(int currRow, int currCol, int newRow, int newCol) {
        if (newRow != currRow || newCol != currCol) {
            allCoordinates.add(new Coordinate(newRow, newCol));
        }
    }
}
