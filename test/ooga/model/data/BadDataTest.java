package ooga.model.data;

import ooga.model.engine.Board;
import ooga.model.engine.BoardConfiguration;
import ooga.model.engine.agent.evaluationFunctions.EvaluationFunctionFactory;
import ooga.model.engine.agent.winTypes.WinTypeFactory;
import ooga.model.engine.Coordinate;
import ooga.model.engine.neighborhood.NeighborhoodFactory;
import ooga.model.engine.pieces.convertibleNeighborFinder.ConvertibleNeighborFinder;
import ooga.model.engine.pieces.convertibleNeighborFinder.ConvertibleNeighborFinderFactory;
import ooga.model.engine.pieces.GamePiece;
import ooga.model.engine.pieces.moveChecks.MoveCheckFactory;
import ooga.model.engine.pieces.MoveTypeFactory;
import ooga.model.exceptions.*;
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
    void testMissingParameter(){
        String badFile = "badData/MissingParameter.json";
        JSONFileReader missingParam = new JSONFileReader(badFile,"3 x 3");
        assertThrows(InvalidFileFormatException.class, missingParam::parseFile);

        missingParam = new JSONFileReader(badFile,"4 x 4");
        assertThrows(InvalidFileFormatException.class, missingParam::parseFile);
    }

    @Test
    void testNonSupportedMoveCheck(){
        String badFile = "badData/nonSupportedMoveCheck.json";
        JSONFileReader badmovecheck = new JSONFileReader(badFile,"3 x 3");

        badmovecheck.parseFile();
        String finder = badmovecheck.getConverterType();
        assertThrows(InvalidMoveCheckException.class, () -> new MoveCheckFactory().
                createMoveCheck(finder, 1,new ArrayList<>(),1,1));
    }

    @Test
    void testNonSupportedMoveType(){
        String badFile = "badData/nonSupportedMoveType.json";
        JSONFileReader badmovetype = new JSONFileReader(badFile,"3 x 3");

        badmovetype.parseFile();
        String finder = badmovetype.getConverterType();
        ConvertibleNeighborFinder cf = new ConvertibleNeighborFinder() {
            @Override
            public List<GamePiece> findNeighborsToConvert(Coordinate currCoordinate, Coordinate endCoordinate, int numObjects, int playerID, int direction, List<GamePiece> neighbors) {
                return null;
            }
        };
        assertThrows(InvalidMoveTypeException.class, () -> new MoveTypeFactory().createMoveType(finder,cf,1,true,1,new ArrayList<>(),false,1));
    }

    @Test
    void testNonSupportedNeighborhoodType(){
        String badFile = "badData/badNeighborhood.json";
        JSONFileReader badmove = new JSONFileReader(badFile,"3 x 3");

        badmove.parseFile();
        assertThrows(InvalidNeighborhoodException.class, () -> new NeighborhoodFactory().
                createNeighborhood("",3,3));
    }

    @Test
    void testDirectionLength(){
        String badInitialConfig = "badData/badDirectionLength.json";
        JSONFileReader badlength = new JSONFileReader(badInitialConfig, "3 x 3");
        assertThrows(InvalidFileFormatException.class, badlength::parseFile);

        JSONFileReader badlength2 = new JSONFileReader(badInitialConfig, "4 x 4");
        assertThrows(InvalidFileFormatException.class, badlength2::parseFile);
    }

    @Test
    void testWrongInfo(){
        String badInitialConfig = "badData/wrongInfo.json";
        JSONFileReader badInfo = new JSONFileReader(badInitialConfig, "3 x 3");
        assertThrows(InvalidFileFormatException.class, badInfo::parseFile);

        JSONFileReader badInfo2 = new JSONFileReader(badInitialConfig, "4 x 4");
        assertThrows(InvalidFileFormatException.class, badInfo2::parseFile);
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
                        new ArrayList<>(), new BoardConfiguration(1,1),0,0,0, true,
                        new BoardConfiguration(1,1)));
    }
    @Test
    void testInvalidWinType(){
        String badWinType = "badData/invalidWinType.json";
        JSONFileReader file = new JSONFileReader(badWinType, "3 x 3");
        file.parseFile();
        String win = file.getWinType();
        assertThrows(InvalidWinTypeException.class, () -> new WinTypeFactory().createWinType(win,0,
                0,0, true,new BoardConfiguration(0,0)));
    }
    @Test
    void testInvalidConvertibleNeighborFinder(){
        String badFinderType = "badData/invalidConvertibleNeighborFinder.json";
        JSONFileReader file = new JSONFileReader(badFinderType, "3 x 3");
        file.parseFile();
        String finder = file.getConverterType();
        assertThrows(InvalidConvertibleNeighborFinderException.class, () -> new ConvertibleNeighborFinderFactory().
                createNeighborhoodConverterFinder(finder, new ArrayList<>()));
    }

}
