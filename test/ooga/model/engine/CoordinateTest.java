package ooga.model.engine;

import ooga.model.Coordinate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class CoordinateTest {
    Coordinate c = new Coordinate(0,1);
    Coordinate c2 = new Coordinate(0,1);
    Coordinate c3 = new Coordinate(0,0);

    @Test
    void testGetXCoord() {
        assertEquals(0, c.getXCoord());
    }

    @Test
    void testGetYCoord() {
        assertEquals(1, c.getYCoord());
    }

    @Test
    void testEquals() {
        //testing two coordinates with same x and y are equal
        assertEquals(true, c.equals(c2));
        //testing two coordinates with different x and y are NOT equal
        assertEquals(false, c.equals(c3));
    }
}
