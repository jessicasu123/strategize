package ooga.model.Data;

import ooga.model.data.JSONFileReader;
import ooga.model.engine.Agent.evaluationFunctions.EvaluationFunctionFactory;
import ooga.model.engine.exceptions.InvalidEvaluationFunctionException;
import ooga.model.engine.exceptions.InvalidFileFormatException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
        String badInitialConfig = "badData/initialConfigNotMatchBoardDimensions.json";
        JSONFileReader notEnoughRows = new JSONFileReader(badInitialConfig, "3 x 3");
        assertThrows(InvalidFileFormatException.class, notEnoughRows::parseFile);

        JSONFileReader notEnoughCols = new JSONFileReader(badInitialConfig, "4 x 4");
        assertThrows(InvalidFileFormatException.class, notEnoughCols::parseFile);

    }

    @Test
    void testBadObjectConfig(){
        String badObjectConfig = "badData/objectConfigNotMatchBoardDimensions.json";
        JSONFileReader notEnoughRows = new JSONFileReader(badObjectConfig, "3 x 3");
        assertThrows(InvalidFileFormatException.class, notEnoughRows::parseFile);

        JSONFileReader notEnoughCols = new JSONFileReader(badObjectConfig, "4 x 4");
        assertThrows(InvalidFileFormatException.class, notEnoughCols::parseFile);
    }

    @Test
    void testBadBoardWeights(){
        String badBoardWeights = "badData/boardWeightsNotMatchBoardDimensions.json";
        JSONFileReader notEnoughRows = new JSONFileReader(badBoardWeights, "3 x 3");
        assertThrows(InvalidFileFormatException.class, notEnoughRows::parseFile);

        JSONFileReader notEnoughCols = new JSONFileReader(badBoardWeights, "4 x 4");
        assertThrows(InvalidFileFormatException.class, notEnoughCols::parseFile);

    }

    @Test
    void testBadPlayerStates(){
        String badPlayerStates = "badData/playerStatesLengthNotMatch.json";
        JSONFileReader file = new JSONFileReader(badPlayerStates, "3 x 3");
        assertThrows(InvalidFileFormatException.class, file::parseFile);
    }

    @Test
    void testBadImages(){
        String badImages = "badData/playerImagesNotMatchStateLength.json";
        JSONFileReader file = new JSONFileReader(badImages, "3 x 3");
        assertThrows(InvalidFileFormatException.class, file::parseFile);
    }

    @Test
    void testInvalidEvaluationFunction(){
        String badEvalFunction = "badData/invalidEvaluationFunction.json";
        JSONFileReader file = new JSONFileReader(badEvalFunction, "3 x 3");
        file.parseFile();
        //the second item in the list is the unsupported eval function
        List<String> evals = file.getEvaluationFunctions();
        assertThrows(InvalidEvaluationFunctionException.class, () ->
                new EvaluationFunctionFactory().createEvaluationFunction(evals.get(1), 0, new ArrayList<>(),
                        new ArrayList<>(), new ArrayList<>(),0,0,0, true,
                        new ArrayList<>()));
    }
}
