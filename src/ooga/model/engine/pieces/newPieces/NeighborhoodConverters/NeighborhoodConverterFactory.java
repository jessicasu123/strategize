package ooga.model.engine.pieces.newPieces.NeighborhoodConverters;

import ooga.model.engine.exceptions.InvalidNeighborhoodConverterException;

import java.util.List;

public class NeighborhoodConverterFactory {

    public NeighborConverterFinder createNeighborhoodConverterFinder(String converterType, List<Integer> toIgnore) {
        switch (converterType) {
            case "FindAllFlippableNeighbors":
                return new FindAllFlippableNeighbors();
            case "FindNeighbordUntilNoObjects":
                return new FindNeighborsUntilNoObjects(toIgnore);
            case "FindNeighborsBetweenCoordinates":
                return new FindNeighborsBetweenCoordinates();
            case "FindNeighborByEndCoord":
                return new FindNeighborAtEndCoord();
            default:
                throw new InvalidNeighborhoodConverterException(converterType + " is not a valid neighborhood converter type.");
        }
    }
}
