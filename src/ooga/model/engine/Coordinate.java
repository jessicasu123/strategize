package ooga.model.engine;
/**
 * PURPOSE OF INTERFACE:
 *  -
 *  -
 *  -
 */
public interface Coordinate {

    /**
     * METHOD PURPOSE:
     *  - get the X coordinate to know the position
     */
    int getXCoord();

    /**
     * METHOD PURPOSE:
     *  - get the Y coordinate to know the position
     */
    int getYCoord();


    /**
     * METHOD PURPOSE:
     *  - to see if two coordinates are equal
     */
    @Override
    boolean equals(Object o);


}
