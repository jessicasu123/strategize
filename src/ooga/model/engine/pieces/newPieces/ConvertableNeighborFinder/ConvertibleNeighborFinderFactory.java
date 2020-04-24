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
    public ConvertibleNeighborFinder createNeighborhoodConverterFinder(String converterType, List<Integer> toIgnore) throws InvalidConvertibleNeighborFinderException {
        switch (converterType) {
            case "FlippableNeighborFinder":
                return new FlippableNeighborFinder();
            case "NeighborsUntilNoObjectsFinder":
                return new NeighborsUntilNoObjectsFinder(toIgnore);
            case "NeighborsBetweenCoordinatesFinder":
                return new NeighborsBetweenCoordinatesFinder();
            case "NeighborAtEndCoordinateFinder":
                return new NeighborAtEndCoordinateFinder();
            case "":
                return null;
            default:
                throw new InvalidConvertibleNeighborFinderException(converterType + " is not a valid neighborhood converter type.");
        }
    }
}
