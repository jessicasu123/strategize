package ooga.model.engine;

/**
 * Note: This class is not directly part of my masterpiece
 * But I refactored the program to create it because in my masterpiece
 * the previous data structure used was unencapsulated which I felt
 * impeded the design of the agent. So I refactored to create this class
 * so it can be used instead of the list of lists
 *
 * The ImmutableGrid interface allows read only access to the Grid, so
 * that the values cannot be changed
 *
 * Because this was my intention aspects such as the second constructor(in Grid) being public
 * are meant to easily adjust our tests so they still
 * run without having to change everything. If this class were to actually be implemented
 * the tests would have been redone accordingly
 *
 * This class overall acts to keep track of integers in a grid formation
 * This allows the data structure used to be encapsulated
 *
 */
public interface ImmutableGrid {

    /**
     * @param row - the row to look in
     * @param col - the col to look in
     * @return the value stored at the position
     */
    int getVal(int row, int col);

    /**
     * @return the number of rows in this grid
     */
    int numRows();

    /**
     * @return the number of cols in this grid
     */
    int numCols();

    /**
     * @param o the object to compare to
     * @return whether the grid comparing is equal to this one, meaning
     * they have all of the same values in the same positions
     */
    boolean equals(Object o);

    ImmutableGrid copy();

}
