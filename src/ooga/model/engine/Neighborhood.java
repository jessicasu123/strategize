package ooga.model.engine;

import java.util.ArrayList;
import java.util.List;

public abstract class Neighborhood {
    protected List<Coordinate> allCoordinates;
    protected int numRows;
    protected int numCols;
    public Neighborhood(int rows, int cols) {
        allCoordinates = new ArrayList<>();
        numRows = rows;
        numCols = cols;
    }
    public abstract List<Coordinate> getNeighbors (int r, int c);

    public void addPiece(int origRow, int origCol, int newRow, int newCol) {
        if (newRow != origRow && newCol != origCol) {
            allCoordinates.add(new Coordinate(newRow, newCol));
        }
    }
}
