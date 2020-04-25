package ooga.model.engine;
/**
 * PURPOSE OF INTERFACE:
 *  - to ensure continuity amongst all parts of the program that require a row, col coordinate
 */
public interface CoordinateFramework {
    /**
     * METHOD PURPOSE:
     *  - get the row coordinate to know the position
     * @return - row coordinate
     */
    int getRow();

    /*
     * @deprecated int getXCoord();
     */

    /**
     * METHOD PURPOSE:
     *  - get the column coordinate to know the position
     * @return - column coordinate
     */
    int getCol();

    /*
     * @deprecated int getYCoord();
     */

    /**
     * METHOD PURPOSE:
     *  - to see if two coordinates are equal
     * @param o - the object comparing to
     * @return if they have the same row-coordinate and the same col-coordinate
     */
    @Override
    boolean equals(Object o);

}
