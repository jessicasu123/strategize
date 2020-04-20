package ooga.model.engine;

/**
 * This class is responsible for representing a coordinate,
 * which consists of an row position and a column position.
 */
public class Coordinate implements CoordinateFramework, Comparable<Coordinate>{
    private int row;
    private int col;

    public Coordinate (int startRow, int startCol) {
        row = startRow;
        col = startCol;
    }

    /**
     * @return the row position of the coordinate
     */
    @Override
    public int getRow() {
        return row;
    }

    /**
     * @return the column position of the coordinate
     */
    @Override
    public int getCol() {
        return col;
    }

    /**
     * @param o - the coordinate being compared to
     * @return whether two coordinates are equal (have the same x and y positions)
     */
    @Override
    public boolean equals(Object o) {
        Coordinate otherCoord = (Coordinate) o;
        return this.getRow() == otherCoord.getRow() &&
                this.getCol() == otherCoord.getCol();
    }

    /**
     * @return - String representation of the coordinate: (x,y)
     */
    @Override
    public String toString() {
        return "(" + Integer.toString(row) + ", " + Integer.toString(col) + ")";
    }

    /**
     *
     * @param o - the coordinate to compare to
     * @return this > o if the x coordinate is greater or the x coord is the same and the y coordinate is greater
     * otherwise they are the same
     */
    @Override
    public int compareTo(Coordinate o) {
        if(this.getRow() != o.getRow()){
            return this.getRow() - o.getRow();
        }else if(this.getCol() != o.getCol()){
            return this.getCol() - o.getCol();
        }
        return 0;
    }

}
