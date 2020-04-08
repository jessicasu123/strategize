package ooga.model.engine.Neighborhood;

import ooga.model.engine.Coordinate;
import ooga.model.engine.Neighborhood.Neighborhood;

import java.util.List;

/**
 * This neighborhood is for calculating vertical (column) neighbors.
 */
public class VerticalNeighborhood extends Neighborhood {
    public VerticalNeighborhood(int rows, int cols) {
        super(rows, cols);
    }

    /**
     * Finds all the neighbors in the same COLUMN
     * @param r - the row of the current position
     * @param c - the column of the current position
     * @return list of coordinates representing vertical neighbors
     */
    @Override
    public List<Coordinate> getNeighbors(int r, int c) {
        for (int row = 0; row < numRows;row++) {
            for (int col = 0; col < numCols;col++) {
                if (col==c) {
                    addCoord(r,c,row,col);
                }
            }
        }
        return allCoordinates;
    }
}
