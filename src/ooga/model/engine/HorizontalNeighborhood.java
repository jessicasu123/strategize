package ooga.model.engine;

import java.util.List;

public class HorizontalNeighborhood extends Neighborhood {
    public HorizontalNeighborhood(int rows, int cols) {
        super(rows, cols);
    }

    @Override
    public List<Coordinate> getNeighbors(int r, int c) {
        return allCoordinates;
    }
}
