package ooga.model.engine.Neighborhood;

import ooga.model.engine.Coordinate;
import ooga.model.engine.Neighborhood.Neighborhood;
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
    public List<List<Integer>> getAllDiagonals(List<List<Integer>> config, int diagonalLength) {
        boolean isSquare = config.size() == config.get(0).size();
        if (isSquare) {
            return getDiagonalsForSquareGrid(config);
        } else {
            return getDiagonalsForRectangularGrid(config, diagonalLength);
        }
    }

    private List<List<Integer>> getDiagonalsForSquareGrid(List<List<Integer>> config) {
        List<Integer> leftDiag = new ArrayList<>();
        List<Integer> rightDiag = new ArrayList<>();
        for(int i = 0; i < Math.min(config.size(), config.get(0).size()); i++){
            leftDiag.add(config.get(i).get(i));
            rightDiag.add(config.get(i).get(config.size() - 1 - i));
        }
        return new ArrayList<>(List.of(leftDiag, rightDiag));
    }

    private List<List<Integer>> getDiagonalsForRectangularGrid(List<List<Integer>> config, int diagonalLength) {
        List<List<Integer>> allDiag = new ArrayList<List<Integer>>();
        int rows = config.size();
        int cols = config.get(0).size();
        int remainder = diagonalLength - 1;
        for (int row = rows - diagonalLength; row >= 0; row--) {
            for (int col = cols - diagonalLength; col >= 0; col--) {
                List<Integer> leftDiag = new ArrayList<Integer>();
                List<Integer> rightDiag = new ArrayList<Integer>();
                for (int i = 0; i < diagonalLength; i++) {
                    rightDiag.add(config.get(row + i).get(col - i + remainder));
                    leftDiag.add(config.get(row + i).get(col + i));
                }
                allDiag.add(leftDiag);
                allDiag.add(rightDiag);
            }
        }
        return allDiag;
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
