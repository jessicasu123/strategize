package ooga.model.engine.Player;


/**
 * As of right now, this player has an ID associated with it which corresponds to all of the pieces that it has
 * Later more utility may be added to this class
 */
public class UserPlayer implements Player{
    private int myID;

    /**
     * Creates a user player
     * @param id - the id of this player (the state of the game piece's that belong to this player)
     */
    public UserPlayer(int id){
        myID = id;
    }

    @Override
    /**
     * this keeps track of which id belongs to each player
     * @return the id of this player
     */
    public int getPlayerID() {
        return myID;
    }

}
