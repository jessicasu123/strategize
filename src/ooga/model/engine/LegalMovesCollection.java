package ooga.model.engine;

import java.util.*;

/**
 * Used to encapsulate the data structure for representing
 * legal moves.
 */
public class LegalMovesCollection {
    private Map<Coordinate, List<Coordinate>> legalMoves;

    public LegalMovesCollection() {
        legalMoves = new TreeMap<>();
    }

    /**
     * adding to the legal moves collection
     * @param key - the key to be added
     * @param value - the value associated with the key
     */
    public void add(Coordinate key, List<Coordinate> value) {
        legalMoves.put(key, value);
    }

    /**
     * @return - number of keys
     */
    public int getSize() {
        return legalMoves.size();
    }

    /**
     * @return a list representing all the values (all the legal moves)
     */
    public List<Coordinate> getAllPossibleMoves() {
        List<Coordinate> possibleMoves = new ArrayList<>();
        for (List<Coordinate> moves: legalMoves.values()) {
            possibleMoves.addAll(moves);
        }
        return Collections.unmodifiableList(possibleMoves);
    }

    /**
     * @return list of all keys --> unmodifiable
     */
    public Collection<Coordinate> getKeys() {
        return Collections.unmodifiableCollection(legalMoves.keySet());
    }

    /**
     * @param key - the key being accessed
     * @return - the value associated with the key
     */
    public List<Coordinate> getValueFromKey(Coordinate key) {
        return legalMoves.get(key);
    }
}
