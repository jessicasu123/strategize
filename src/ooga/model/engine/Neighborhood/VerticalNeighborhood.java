package ooga.model.engine.Neighborhood;

import ooga.model.engine.Coordinate;
import ooga.model.engine.Neighborhood.Neighborhood;

import java.util.ArrayList;
import java.util.List;

/**
 * This neighborhood is for calculating vertical (column) neighbors.
 *
 * @author Jessica Su, Holly Ansel
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

    /**
     * This method is responsible for getting ALL the vertical columns
     * given a certain board configuration.
     * @param config - the List of Lists of integers representing an entire board.
     * @return - a List of the columns in the board, where each column is represented as a List of integers
     */
    public List<List<Integer>> getAllVerticals(List<List<Integer>> config) {
        List<List<Integer>> allCols = new ArrayList<>();
        for(int i = 0; i < config.get(0).size(); i++) {
            List<Integer> col = new ArrayList<>();
            for (List<Integer> row : config) {
                col.add(row.get(i));
            }
            allCols.add(col);
        }
        return allCols;
    }

}
