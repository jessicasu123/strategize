package ooga.model.engine.Player;

/**
 * This interface shows all of the functionality that all players much have
 * which currently is just an ID
 */
public interface Player {
    /**
     * this keeps track of which id belongs to each player
     * @return the id of this player
     */
    int getPlayerID();

    /**TODO: do we want to have the calculate move method in the interface and implement it as a do nothing method?
     * this would allow for us not rely on type dependencies
     */


}
