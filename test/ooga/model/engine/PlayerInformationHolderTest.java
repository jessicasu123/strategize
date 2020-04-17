package ooga.model.engine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerInformationHolderTest {
    PlayerInformationHolder player = new PlayerInformationHolder(1,2,"hi", "bye", true);
    @Test
    void getPlayerID() {
        assertEquals(1, player.getPlayerID());
    }

    @Test
    void getSpecialPlayerID() {
        assertEquals(2, player.getSpecialPlayerID());
    }

    @Test
    void getPlayerImage() {
        assertEquals("hi", player.getPlayerImage());
    }

    @Test
    void getSpecialPlayerImage() {
        assertEquals("bye", player.getSpecialPlayerImage());
    }

    @Test
    void movesInPosDirection() {
        assertEquals(true, player.movesInPosDirection());
    }

    @Test
    void setPlayerImage() {
        player.setPlayerImage("hello");
        assertEquals("hello", player.getPlayerImage());
    }

    @Test
    void setPlayerSpecialImage() {
        player.setPlayerSpecialImage("goodbye");
        assertEquals("goodbye", player.getSpecialPlayerImage());
    }
}