package ooga.model.engine;

/**
 * This class is responsible for representing a coordinate,
 * which consists of an x position and a y position.
 */
public class Coordinate implements CoordinateFramework, Comparable<Coordinate>{
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

    /**
     * @return - String representation of the coordinate: (x,y)
     */
    @Override
    public String toString() {
        return "(" + Integer.toString(xPos) + ", " + Integer.toString(yPos) + ")";
    }

    /**
     *
     * @param o - the coordinate to compare to
     * @return this > o if the x coordinate is greater or the x coord is the same and the y coordinate is greater
     * otherwise they are the same
     */
    @Override
    public int compareTo(Coordinate o) {
        if(this.getXCoord() != o.getXCoord()){
            return this.getXCoord() - o.getXCoord();
        }else if(this.getYCoord() != o.getYCoord()){
            return this.getYCoord() - o.getYCoord();
        }
        return 0;
    }



}
