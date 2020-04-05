package ooga.model.engine;

/**
 * This class is responsible for representing a coordinate,
 * which consists of an x position and a y position.
 */
public class Coordinate implements CoordinateFramework {
    private int xPos;
    private int yPos;

    public Coordinate (int startX, int startY) {
        xPos = startX;
        yPos = startY;
    }

    /**
     * @return the x position of the coordinate
     */
    @Override
    public int getXCoord() {
        return xPos;
    }

    /**
     * @return the y position of the coordinate
     */
    @Override
    public int getYCoord() {
        return yPos;
    }

    /**
     * @param o - the coordinate being compared to
     * @return whether two coordinates are equal (have the same x and y positions)
     */
    @Override
    public boolean equals(Object o) {
        Coordinate otherCoord = (Coordinate) o;
        return this.getXCoord() == otherCoord.getXCoord() &&
                this.getYCoord() == otherCoord.getYCoord();
    }
}
