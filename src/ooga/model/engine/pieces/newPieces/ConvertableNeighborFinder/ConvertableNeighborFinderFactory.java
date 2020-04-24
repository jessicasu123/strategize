package ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder;

import ooga.model.engine.exceptions.InvalidConvertableNeighborFinderException;

import java.util.List;

public class ConvertableNeighborFinderFactory {

    public ConvertableNeighborFinder createNeighborhoodConverterFinder(String converterType, List<Integer> toIgnore) {
        switch (converterType) {
            case "FindAllFlippableNeighbors":
                return new FindAllFlippableNeighbors();
            case "FindNeighborsUntilNoObjects":
                return new FindNeighborsUntilNoObjects(toIgnore);
            case "FindNeighborsBetweenCoordinates":
                return new FindNeighborsBetweenCoordinates();
            case "FindNeighborByEndCoord":
                return new FindConvertableNeighborAtEndCoord();
            default:
                throw new InvalidConvertableNeighborFinderException(converterType + " is not a valid neighborhood converter type.");
        }
    }
}
