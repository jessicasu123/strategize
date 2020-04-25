package ooga.model.engine.Neighborhood;

import ooga.model.engine.Coordinate;
import ooga.model.engine.Neighborhood.Neighborhood;

import java.util.List;

/**
 * This neighborhood is for calculating horizontal (row) neighbors.
 *
 * @author Jessica Su
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
