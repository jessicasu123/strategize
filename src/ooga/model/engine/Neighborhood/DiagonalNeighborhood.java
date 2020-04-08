package ooga.model.engine.Neighborhood;

import ooga.model.engine.Coordinate;
import ooga.model.engine.Neighborhood.Neighborhood;
import ooga.model.engine.pieces.GamePiece;

import java.util.ArrayList;
import java.util.List;

/**
 * This neighborhood is for calculating diagonal neighbors.
 */
public class DiagonalNeighborhood extends Neighborhood {
    public DiagonalNeighborhood(int rows, int cols) {
        super(rows, cols);
    }

    /**
     * Getting all neighbors on BOTH diagonals (right and left)
     * @param r - the row of the current position
     * @param c - the column of the current position
     * @return - list of all the coordinates on the diagonals
     */
    @Override
    public List<Coordinate> getNeighbors(int r, int c) {
        getRightDiag(r,c);
        getLeftDiag(r,c);
        return allCoordinates;
    }

    /**
     * Getting the right diagonal
     * @param r - current row
     * @param c - current column
     */
    private void getRightDiag(int r, int c) {
        int row = 0;
        int col = 0;
        int boundChecker, upperLim;
        if (c > r) { //to the right of center diag
            col = c - r; //col offset on row 0
            boundChecker = col; //ends on last column
            upperLim = numCols;
        }
        else { //to the left or ON center diag
            row = r - c; //row offset on col 0
            boundChecker = row; //ends on last row
            upperLim = numRows;
        }
        List<GamePiece> mainDiag = new ArrayList<>();
        while (boundChecker < upperLim) {
            addCoord(r,c,row,col);
            row++;
            col++;
            boundChecker++;
        }

    }

    /**
     * Getting the left diagonal
     * @param r - current row
     * @param c - current column
     */
    private void getLeftDiag(int r, int c) {
        int row = 0;
        int col = numCols-1;
        int lowerlim;
        boolean colBound = false;
        boolean rowBound = false;
        List<GamePiece> minorDiag = new ArrayList<>();
        if (r+c<=numCols-1) { //to the left or ON center diag
            lowerlim = 0; //ends on col 0
            col = r+c;
            colBound = true;
        }
        else { //to the right of center diag
            lowerlim = numRows; //ends on last row
            row = (r+c)-(numCols-1);
            rowBound = true;
        }
        while ((row < lowerlim && rowBound) || (col >= lowerlim && colBound)){
            addCoord(r,c,row,col);
            row++;
            col--;
        }

    }
}
