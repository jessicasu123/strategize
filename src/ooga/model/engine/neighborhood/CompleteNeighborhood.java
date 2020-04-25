package ooga.model.engine.neighborhood;

import ooga.model.engine.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * This neighborhood is for calculating information about all the coordinates
 * in a grid
 *
 * @author Holly Ansel
 */
public class CompleteNeighborhood extends Neighborhood {

    public CompleteNeighborhood(int rows, int cols){
        super(rows, cols);
    }

    /**
     * Getting all neighbors on from all the rows and columns
     * @param r - the row of the current position
     * @param c - the column of the current position
     * @return - list of all the coordinates
     */
    @Override
    public List<Coordinate> getNeighbors(int r, int c) {
        Coordinate myLoc = new Coordinate(r,c);
        List<Coordinate> neighbors = new ArrayList<>();
        for(int i = 0; i < numRows; i++ ){
            for(int j = 0; j < numCols; j++){
                Coordinate curr = new Coordinate(i,j);
                if(!neighbors.contains(curr) && !curr.equals(myLoc)){
                    neighbors.add(curr);
                }
            }
        }
        return neighbors;
    }
}
