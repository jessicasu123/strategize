package ooga.model.engine;
/**
 * PURPOSE OF INTERFACE:
 *  - to ensure continuity amongst all parts of the program that require a x,y coordinate
 */
public interface CoordinateFramework {
    /**
     * METHOD PURPOSE:
     *  - get the X coordinate to know the position
     * @return - x coordinate
     */
    int getXCoord();

    /**
     * METHOD PURPOSE:
     *  - get the Y coordinate to know the position
     * @return - y coordinate
     */
    int getYCoord();


    /**
     * METHOD PURPOSE:
     *  - to see if two coordinates are equal
     * @param o - the object comparing to
     * @return if they have the same x-coordinate and the same y-coordinate
     */
    @Override
    boolean equals(Object o);

}
