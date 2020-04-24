package ooga.model.Data;

import ooga.model.data.JSONFileReader;
import ooga.model.engine.exceptions.InvalidFileFormatException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class BadDataTest {
    private String badInitialConfig = "initialConfigNotMatchBoardDimensions.json";

    @Test
    void testBadInitialConfig(){
        JSONFileReader notEnoughRows = new JSONFileReader(badInitialConfig, "3 x 3");
        assertThrows(InvalidFileFormatException.class,() -> notEnoughRows.parseFile());

        JSONFileReader notEnoughCols = new JSONFileReader(badInitialConfig, "4 x 4");
        assertThrows(InvalidFileFormatException.class,() -> notEnoughCols.parseFile());

        JSONFileReader oneMissingInput = new JSONFileReader(badInitialConfig, "5 x 5");
        assertThrows(InvalidFileFormatException.class,() -> oneMissingInput.parseFile());
    }
}
