package ooga.model.engine.Neighborhood;

import ooga.model.engine.InvalidNeighborhoodException;
import ooga.model.engine.Neighborhood.DiagonalNeighborhood;
import ooga.model.engine.Neighborhood.HorizontalNeighborhood;
import ooga.model.engine.Neighborhood.Neighborhood;
import ooga.model.engine.Neighborhood.VerticalNeighborhood;

/**
 * This class is responsible for creating different types of
 * neighborhood objects, following the factory pattern.
 */
public class NeighborhoodFactory {
    /**
     * Creates a new Neighborhood object.
     * @param neighborhoodType - type of neighborhood (horizontal, vertical, diagonal)
     * @param numRows - number of rows for the Board
     * @param numCols - number of columns for the Board
     * @return new Neighborhood subclass
     * @throws InvalidNeighborhoodException
     */
    public Neighborhood createNeighborhood(String neighborhoodType, int numRows, int numCols) throws InvalidNeighborhoodException {
        switch (neighborhoodType) {
            case "horizontal":
                return new HorizontalNeighborhood(numRows, numCols);
            case "vertical":
                return new VerticalNeighborhood(numRows, numCols);
            case "diagonal":
                return new DiagonalNeighborhood(numRows, numCols);
            default:
                throw new InvalidNeighborhoodException(neighborhoodType + " is not a valid neighborhood type.");
        }
    }

}