package ooga.model.engine;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper class to hide the data structure used
 * to represent information about the board (ex. states, num objects, possible moves).
 */
public class BoardConfiguration {
    private List<List<Integer>> myConfiguration;
    private int myRows;
    private int myCols;

    public BoardConfiguration(int numRows, int numCols) {
        myRows = numRows;
        myCols = numCols;
        initializeConfiguration();
    }

    /**
     * Alternate constructor mainly used so that all the tests still compile
     * without changing every List<List<Integer>> to a BoardConfiguration object.
     * @param config - List of List of integers to represent the config
     */
    public BoardConfiguration(List<List<Integer>> config) {
        myConfiguration = config;
    }

    //initialize to all empty values first
    private void initializeConfiguration() {
        myConfiguration = new ArrayList<>();
        for (int r = 0; r < myRows;r++) {
            List<Integer> row = new ArrayList<>();
            for (int c = 0; c < myCols;c++) {
                row.add(0);
            }
            myConfiguration.add(row);
        }
    }

    /**
     * Can be used by other classes to update the values that need
     * to be communicated by the configuration.
     * @param row - row position where the new value should go
     * @param col - column position where the new value should go
     * @param value - the new value (ex. state, num objects, 1 for possible move) that should be in the row, col position
     */
    public void setValue(int row, int col, int value) {
        myConfiguration.get(row).set(col, value);
    }

    /**
     * Can be used by other classes to access a value
     * in the configuration at a certain row, col position.
     * @param row - the row position where the value is being accessed from
     * @param col - the column position where the value is being accessed from
     * @return the value in the row, col position in the configuration
     */
    public int getValue(int row, int col) {
        return myConfiguration.get(row).get(col);
    }

    public int getNumRows() {
        return myRows;
    }

    public int getNumCols() {
        return myCols;
    }
}
