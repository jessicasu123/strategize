package ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder;

import ooga.model.engine.exceptions.InvalidConvertibleNeighborFinderException;

import java.util.List;

/**
 * This factory is responsible for creating a ConvertibleNeighborFinder object
 * depending on a NeighborConverterType specified in the game configuration file.
 */
public class ConvertibleNeighborFinderFactory {
    /**
     * @param converterType - the type of this converter
     * @param toIgnore - a list of states to ignore when finding which to convert
     * @return a convertible neighbor finder based on the parameters
     */
    public ConvertibleNeighborFinder createNeighborhoodConverterFinder(String converterType, List<Integer> toIgnore) {
        switch (converterType) {
            case "FindAllFlippableNeighbors":
                return new FindAllFlippableNeighbors();
            case "FindNeighborsUntilNoObjects":
                return new FindNeighborsUntilNoObjects(toIgnore);
            case "FindNeighborsBetweenCoordinates":
                return new FindNeighborsBetweenCoordinates();
            case "FindNeighborByEndCoord":
                return new FindConvertibleNeighborAtEndCoord();
            default:
                throw new InvalidConvertibleNeighborFinderException(converterType + " is not a valid neighborhood converter type.");
        }
    }
}
