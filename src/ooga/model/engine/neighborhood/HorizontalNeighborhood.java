package ooga.model.engine.neighborhood;

import ooga.model.engine.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * This neighborhood is for calculating horizontal (row) neighbors.
 */
public class HorizontalNeighborhood extends Neighborhood {
    public HorizontalNeighborhood(int rows, int cols) {
        super(rows, cols);
    }

    /**
     * Returns a list of all the neighbors in the same ROW
     * @param r - the row of the current position
     * @param c - the column of the current position
     * @return - coordinates for horizontal row neighbors
     */
    @Override
    public List<Coordinate> getNeighbors(int r, int c) {
        allCoordinates = new ArrayList<>();
        boolean addedRow = false;
        for (int row = 0; row < numRows; row++) {
            if (addedRow) break;
            if (row==r) {
                for (int col = 0; col < numCols;col++) {
                    addCoord(r,c,row,col);
                }
                addedRow=true;
            }
        }
        return allCoordinates;
    }
}
