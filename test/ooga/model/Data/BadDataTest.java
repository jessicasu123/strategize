package ooga.model.Data;

import ooga.model.data.JSONFileReader;
import ooga.model.engine.exceptions.InvalidFileFormatException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BadDataTest {

    @Test
    void testGoodConfig(){
        String goodFile = "tic-tac-toe.json";
        JSONFileReader good = new JSONFileReader(goodFile, "3 x 3");
        assertDoesNotThrow(good::parseFile);
    }
    @Test
    void testBadInitialConfig(){
        String badInitialConfig = "initialConfigNotMatchBoardDimensions.json";
        JSONFileReader notEnoughRows = new JSONFileReader(badInitialConfig, "3 x 3");
        assertThrows(InvalidFileFormatException.class, notEnoughRows::parseFile);

        JSONFileReader notEnoughCols = new JSONFileReader(badInitialConfig, "4 x 4");
        assertThrows(InvalidFileFormatException.class, notEnoughCols::parseFile);

        JSONFileReader oneMissingInput = new JSONFileReader(badInitialConfig, "5 x 5");
        assertThrows(InvalidFileFormatException.class, oneMissingInput::parseFile);
    }

    @Test
    void testBadObjectConfig(){
        String badObjectConfig = "objectConfigNotMatchBoardDimensions.json";
        JSONFileReader notEnoughRows = new JSONFileReader(badObjectConfig, "3 x 3");
        assertThrows(InvalidFileFormatException.class, notEnoughRows::parseFile);

        JSONFileReader notEnoughCols = new JSONFileReader(badObjectConfig, "4 x 4");
        assertThrows(InvalidFileFormatException.class, notEnoughCols::parseFile);

        JSONFileReader oneMissingInput = new JSONFileReader(badObjectConfig, "5 x 5");
        assertThrows(InvalidFileFormatException.class, oneMissingInput::parseFile);
    }

    @Test
    void testBadBoardWeights(){
        String badBoardWeights = "boardWeightsNotMatchBoardDimensions.json";
        JSONFileReader notEnoughRows = new JSONFileReader(badBoardWeights, "3 x 3");
        assertThrows(InvalidFileFormatException.class, notEnoughRows::parseFile);

        JSONFileReader notEnoughCols = new JSONFileReader(badBoardWeights, "4 x 4");
        assertThrows(InvalidFileFormatException.class, notEnoughCols::parseFile);

        JSONFileReader oneMissingInput = new JSONFileReader(badBoardWeights, "5 x 5");
        assertThrows(InvalidFileFormatException.class, oneMissingInput::parseFile);
    }
}
