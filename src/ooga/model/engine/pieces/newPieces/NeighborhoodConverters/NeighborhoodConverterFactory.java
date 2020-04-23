package ooga.model.engine.pieces.newPieces.NeighborhoodConverters;

import ooga.model.engine.exceptions.InvalidNeighborhoodConverterException;

public class NeighborhoodConverterFactory {

    public NeighborConverterFinder createNeighborhoodConverterFinder(String converterType) {
        switch (converterType) {
            case "FindAllFlippableNeighbors":
                return new FindAllFlippableNeighbors();
            default:
                throw new InvalidNeighborhoodConverterException(converterType + " is not a valid neighborhood converter type.");
        }
    }
}
