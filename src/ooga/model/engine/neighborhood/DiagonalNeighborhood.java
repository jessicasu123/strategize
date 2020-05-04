package ooga.model.engine.neighborhood;

import ooga.model.engine.Coordinate;
import ooga.model.engine.Grid;
import ooga.model.engine.ImmutableGrid;
import ooga.model.engine.pieces.GamePiece;

import java.util.ArrayList;
import java.util.List;

/**
 * This neighborhood is for calculating information about diagonals in a grid.
 * This includes the information on diagonals around a specific piece, as well
 * as all the diagonals on the grid.
 *
 * @author Jessica Su, Holly Ansel, Brian Li
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
        allCoordinates = new ArrayList<>();
        getRightDiag(r,c);
        getLeftDiag(r,c);
        return allCoordinates;
    }

    /**
     * Gets ALL the diagonals of a certain length from an entire board.
     * @param config - the current board configuration
     * @param diagonalLength - the length of the diagonal being looked for
     * @return
     */
    public ImmutableGrid getAllDiagonals(ImmutableGrid config, int diagonalLength) {
        boolean isSquare = config.numRows() == config.numCols();
        if (isSquare) {
            return getDiagonalsForSquareGrid(config);
        } else {
            return getDiagonalsForRectangularGrid(config, diagonalLength);
        }
    }

    private ImmutableGrid getDiagonalsForSquareGrid(ImmutableGrid config) {
        List<Integer> leftDiag = new ArrayList<>();
        List<Integer> rightDiag = new ArrayList<>();
        for(int i = 0; i < config.numRows(); i++){
            leftDiag.add(config.getVal(i,i));
            rightDiag.add(config.getVal(i,config.numRows() - 1 - i));
        }
        return new Grid(new ArrayList<>(List.of(leftDiag, rightDiag)));
    }

    private ImmutableGrid getDiagonalsForRectangularGrid(ImmutableGrid config, int diagonalLength) {
        List<List<Integer>> allDiag = new ArrayList<>();
        int rows = config.numRows();
        int cols = config.numCols();
        int remainder = diagonalLength - 1;
        for (int row = rows - diagonalLength; row >= 0; row--) {
            for (int col = cols - diagonalLength; col >= 0; col--) {
                List<Integer> leftDiag = new ArrayList<>();
                List<Integer> rightDiag = new ArrayList<>();
                for (int i = 0; i < diagonalLength; i++) {
                    rightDiag.add(config.getVal(row + i,col - i + remainder));
                    leftDiag.add(config.getVal(row + i,col + i));
                }
                allDiag.add(leftDiag);
                allDiag.add(rightDiag);
            }
        }
        return new Grid(allDiag);
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
