package ooga.model.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * As mentioned in ImmutableGrid this shows an example of how
 * we should have implemented the List of List data structure
 *
 * The Grid allows values to be updated, so when a grid is referred
 * to without the abstraction it can be altered accordingly
 *
 * This class overall acts to keep track of integers in a grid formation
 * This allows the data structure used to be encapsulated
 *
 */
public class Grid implements ImmutableGrid {
    public static final int FILLER = -1;
    private List<List<Integer>> myConfig;
    private int myRows;
    private int myCols;

    /**
     * Creates the grid and initially fills all the spots with a filler number
     * @param numRows the number of rows to make the grid
     * @param numCols the number of columns to make the grid
     */
    public Grid(int numRows, int numCols){
       myRows = numRows;
       myCols = numCols;
       myConfig = new ArrayList<>();
       List<Integer> row = new ArrayList<>(Collections.nCopies(myCols, FILLER));
       for(int i = 0; i < myRows; i++){
           myConfig.add(new ArrayList<>(row));
       }
    }

    /**
     * If want to transfer a specific configuration to a grid can use this constructor
     * @param config - a 2d list to use as the grid
     */
    public Grid(List<List<Integer>> config){
        myConfig = new ArrayList<>();
        for(List<Integer> row: config){
            myConfig.add(new ArrayList<>(row));
        }
        myRows = myConfig.size();
        if(myRows > 0){
            myCols = myConfig.get(0).size();
        }else{
            myCols = 0;
        }
    }

    /**
     * @return a copy of the current grid
     */
    @Override
    public Grid copy(){
        return new Grid(Collections.unmodifiableList(List.copyOf(myConfig)));
    }

    /**
     * updates a spot in the grid
     * @param row - the row position of where to updates
     * @param col - the col position of where to updates
     * @param newVal - the new value to update to
     */
    public void update(int row, int col, int newVal){
        myConfig.get(row).set(col,newVal);
    }



    /**
     * @param row - the row to look in
     * @param col - the col to look in
     * @return the value stored at the position
     */
    public int getVal(int row, int col){
        return myConfig.get(row).get(col);
    }

    /**
     * @return the number of rows in this grid
     */
    public int numRows(){
        return myRows;
    }

    /**
     * @return the number of cols in this grid
     */
    public int numCols(){
        return myCols;
    }

    /**
     * @param o the object to compare to
     * @return whether the grid comparing is equal to this one, meaning
     * they have all of the same values in the same positions
     */
    @Override
    public boolean equals(Object o){
        if(o.getClass() != this.getClass()){
            return false;
        }
        Grid compare = (Grid) o;
        if(this.numRows() != compare.numRows() || this.numCols() != compare.numCols()){
            return false;
        }
        for(int r = 0; r < numRows(); r++){
            for(int c = 0; c < numCols(); c++){
                if(this.getVal(r,c) != compare.getVal(r,c)){
                    return false;
                }
            }
        }
        return true;
    }

}
