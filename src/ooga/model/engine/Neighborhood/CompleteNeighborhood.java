package ooga.model.engine.Neighborhood;

import ooga.model.engine.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class CompleteNeighborhood extends Neighborhood {

    public CompleteNeighborhood(int rows, int cols){
        super(rows, cols);
    }
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
