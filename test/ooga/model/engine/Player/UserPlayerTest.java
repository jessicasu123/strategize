package ooga.model.engine.Player;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserPlayerTest {
    private UserPlayer myUser = new UserPlayer(1);
    private UserPlayer myUser2 = new UserPlayer(5);
    @Test
    void getPlayerID() {
        assertEquals(1, myUser.getPlayerID());
        assertEquals(5, myUser2.getPlayerID());
    }
}