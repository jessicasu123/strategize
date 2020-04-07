package ooga.model.engine;

import java.util.ArrayList;
import java.util.List;

public class DiagonalNeighborhood extends Neighborhood {
    public DiagonalNeighborhood(int rows, int cols) {
        super(rows, cols);
    }

    @Override
    public List<Coordinate> getNeighbors(int r, int c) {
        return allCoordinates;
    }

    private void getRightDiag(int r, int c) {

    }

    private void getLeftDiag(int r, int c) {

    }
}
