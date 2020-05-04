package ooga.model.engine.pieces.convertibleNeighborFinder;

import ooga.model.exceptions.InvalidConvertibleNeighborFinderException;

import java.util.List;
/**
 * CODE MASTERPIECE (PT 3):
 * The ConvertibleNeighborFinderFactory goes hand-in-hand with the flexibility provided
 * by ConvertibleNeighborFinder interface, allows for DATA-DRIVEN DESIGN, and
 * throws appropriate EXCEPTIONS. This class also follows the design pattern of the FACTORY METHOD.
 * - FLEXIBILITY/DATA-DRIVEN
 *      - The parameter String converterType can be represented by a String identifier in an initial JSON
 *      game configuration file, meaning a user can customize his/her own game by changing how the convertible
 *      neighbors are found.
 *  -EXCEPTIONS
 *      - If the converterType from the JSON file is NOT a supported ConvertibleNeighborFinder,
 *      then an Exception will be thrown to let the user know to fix the configuration file.
 */

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
            case "NeighborsInAllDirectionsFinder":
                return new NeighborsInAllDirectionsFinder();
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
