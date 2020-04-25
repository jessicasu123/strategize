package ooga.model.data;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

class JSONFileReaderTest {
    private String fileNameGame = "checkers.json";
    JSONFileReader fr;
    {
        try {
            fr = new JSONFileReader(fileNameGame, "8 x 8");
            fr.parseFile();
        } catch (Exception e) {
            System.out.println("file not found");
        }
    }

    JSONFileReaderTest() throws FileNotFoundException {
    }

    @Test
    void getMaxObjectsPerSquareTest(){
        assertEquals(1, fr.getMaxObjectsPerSquare());
    }

    @Test
    void getStatesToIgnoreForPlayerTest(){
        assertEquals(List.of(0), fr.getStatesToIgnoreForPlayer(1));
        assertEquals(List.of(0), fr.getStatesToIgnoreForPlayer(2));
    }

    @Test
    void getConverterTypeTest(){
        assertEquals("NeighborsBetweenCoordinatesFinder", fr.getConverterType());
    }

    @Test
    void getSelfMoveChecksTest(){
        List<String> movechecks = new ArrayList<>();
        movechecks.add("OwnPieceCheck");
        assertEquals(movechecks,fr.getSelfMoveChecks());
    }

    @Test
    void getNeighborMoveChecksTest(){
        List<String> movechecks = new ArrayList<>();
        movechecks.add("StepCheck");
        movechecks.add("JumpCheck");
        assertEquals(movechecks,fr.getNeighborMoveChecks());
    }

    @Test
    void getMoveTypeTest(){
        List<String> movechecks = new ArrayList<>();
        movechecks.add("ChangeOpponentPiecesMove");
        movechecks.add("PositionMove");
        movechecks.add("PromotionMove");
        assertEquals(movechecks,fr.getMoveTypes());
    }

    @Test
    void getDirectionForPlayerTest(){
        List<Integer> direction = new ArrayList<>();
        direction.add(-1);
        assertEquals(direction,fr.getDirectionForPlayer(1));
        direction = new ArrayList<>();
        direction.add(1);
        assertEquals(direction, fr.getDirectionForPlayer(2));
    }

    @Test
    void getNeighborNumObjectsToCompareTest(){
        assertEquals(0, fr.getNeighborNumObjectsToCompare());
    }

    @Test
    void getSelfNumObjectsToCompareTest(){
        assertEquals(0, fr.getSelfNumObjectsToCompare());
    }

    @Test
    void convertToEmptyStateTest(){
        assertEquals(true, fr.convertToEmptyState());
    }

    @Test
    void getPromotionRowForPlayer1Test(){
        assertEquals(0, fr.getPromotionRowForPlayer(1));
    }

    @Test
    void getNumRowsPerSquareTest(){
        assertEquals(1, fr.getNumRowsPerSquare());
    }

    @Test
    void getPlayerStateInfoTest(){
        List<Integer> states1 = new ArrayList<>();
        List<Integer> states2 = new ArrayList<>();
        states1.add(3);
        states1.add(4);
        states2.add(1);
        states2.add(2);
        assertEquals(states1, fr.getPlayerStateInfo(1));
        assertEquals(states2, fr.getPlayerStateInfo(2));
    }

    @Test
    void getBoardWeightsTest(){
        List<Integer> row1 = new ArrayList<>(List.of(0,-4,0,-4,0,-4,0,-4));
        List<Integer> row2 = new ArrayList<>(List.of(-3,0,-3,0,-3,0,-3,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,-2,0,-2,0,-2,0,-2));
        List<Integer> row4 = new ArrayList<>(List.of(-1,0,-1,0,-1,0,-1,0));
        List<Integer> row5 = new ArrayList<>(List.of(0,1,0,1,0,1,0,1));
        List<Integer> row6 = new ArrayList<>(List.of(2,0,2,0,2,0,2,0));
        List<Integer> row7 = new ArrayList<>(List.of(0,3,0,3,0,3,0,3));
        List<Integer> row8 = new ArrayList<>(List.of(4,0,4,0,4,0,4,0));
        List<List<Integer>> weights = new ArrayList<List<Integer>>();
        weights.addAll(Arrays.asList(row1,row2,row3,row4,row5,row6,row7,row8));
        assertEquals(weights, fr.getBoardWeights());
    }


    @Test
    void getObjectConfigTest(){
        List<Integer> row1 = new ArrayList<>(List.of(0,1,0,1,0,1,0,1));
        List<Integer> row2 = new ArrayList<>(List.of(1,0,1,0,1,0,1,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,1,0,1,0,1,0,1));
        List<Integer> row4 = new ArrayList<>(List.of(1,0,1,0,1,0,1,0));
        List<Integer> row5 = new ArrayList<>(List.of(0,1,0,1,0,1,0,1));
        List<Integer> row6 = new ArrayList<>(List.of(1,0,1,0,1,0,1,0));
        List<Integer> row7 = new ArrayList<>(List.of(0,1,0,1,0,1,0,1));
        List<Integer> row8 = new ArrayList<>(List.of(1,0,1,0,1,0,1,0));
        List<List<Integer>> weights = new ArrayList<List<Integer>>();
        weights.addAll(Arrays.asList(row1,row2,row3,row4,row5,row6,row7,row8));
        assertEquals(weights, fr.getObjectConfig());
    }

    @Test
    void getWinValueTest(){
        assertEquals(0, fr.getWinValue());
    }

    @Test
    void getWinTypeTest(){
        assertEquals("NoPiecesForOpponent", fr.getWinType());
    }

    @Test
    void getEvaluationFunctionsTest(){
        List<String> functions = new ArrayList<>(List.of("MorePieces","PositionWeights","SumOfDistances"));
        assertEquals(functions, fr.getEvaluationFunctions());
    }

    @Test
    void getSpecialPieceIndexTest(){
        assertEquals(1, fr.getSpecialPieceIndex());
    }

    @Test
    void getEmptyStateTest(){
        assertEquals(0, fr.getEmptyState());
    }


    @Test
    void doPiecesMoveTest(){
        assertEquals(true, fr.doPiecesMove());
    }

    @Test
    void getStateImageMappingTest(){
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(3,"redCircle.png");
        map.put(4,"redCrown.png");
        assertEquals(map, fr.getStateImageMapping(1));
    }

    @Test
    void getSpecialColorTest(){
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(4,"white");
        assertEquals(map, fr.getSpecialStateColorMapping(1));
    }

    @Test
    void hasMultiplePiecesPerSqTest(){
        assertEquals(false, fr.hasMultiplePiecesPerSquare());
    }

    @Test
    void getGameType() throws IOException {
        assertEquals("Checkers",fr.getGameType());
    }


    @Test
    void shouldCheckCurrConfigTest(){
        assertEquals(true, fr.shouldCheckCurrConfig());
    }

    @Test
    void getNeighborhood() throws IOException {
        List<String> neighb = new ArrayList<String>();
        neighb.add("diagonal");
        assertEquals(neighb,fr.getNeighborhood());
    }

    @Test
    void loadFileConfigurationTest() throws IOException, ParseException {
        List<Integer> row1 = new ArrayList<>(List.of(0,1,0,1,0,1,0,1));
        List<Integer> row2 = new ArrayList<>(List.of(1,0,1,0,1,0,1,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,1,0,1,0,1,0,1));
        List<Integer> row4 = new ArrayList<>(List.of(0,0,0,0,0,0,0,0));
        List<Integer> row5 = new ArrayList<>(List.of(0,0,0,0,0,0,0,0));
        List<Integer> row6 = new ArrayList<>(List.of(3,0,3,0,3,0,3,0));
        List<Integer> row7 = new ArrayList<>(List.of(0,3,0,3,0,3,0,3));
        List<Integer> row8 = new ArrayList<>(List.of(3,0,3,0,3,0,3,0));
        List<List<Integer>> config = new ArrayList<List<Integer>>();
        config.addAll(Arrays.asList(row1,row2,row3,row4,row5,row6,row7,row8));
        assertEquals(config, fr.loadFileConfiguration());
    }

    @Test
    void loadFilePropertiesTest(){
        Map<String, String> map = new HashMap<String, String>();
//        map.put("Keys","ConsecutivePieces");
//        map.put("possibleMove","");
//        map.put("Gametype","Tic-Tac-Toe");
//        map.put("Height","3");
//        map.put("Color2","Black");
//        map.put("Color1","Black");
//        map.put("Width","3");
//        map.put("Neighborhood","");
//        map.put("InitialConfig", "0,0,0;0,0,0;0,0,0");
//        map.put("BoardWeights", "0,0,0;0,0,0;0,0,0");
//        map.put("ObjectConfig", "1,1,1;1,1,1;1,1,1");
//        map.put("MultiplePiecesPerSquare", "false");
//        map.put("SquareClickType", "empty");
        //assertEquals(map, fr.loadFileProperties());
    }

    @Test
    void saveToFile() {
        fr.saveToFile("checkersgame", fr.loadFileConfiguration(), fr.getObjectConfig());
        JSONFileReader fr2 = new JSONFileReader("checkersgame.json","8 x 8");
        fr2.parseFile();
        assertEquals(fr.loadFileConfiguration(),fr2.loadFileConfiguration());
        assertEquals(fr.loadFileProperties(),fr2.loadFileProperties());
        assertEquals(fr.getNeighborhood(),fr2.getNeighborhood());
        assertEquals(fr.getBoardWeights(),fr2.getBoardWeights());
        assertEquals(fr.getGameType(),fr2.getGameType());
        assertEquals(fr.getObjectConfig(),fr2.getObjectConfig());
        assertEquals(fr.getMaxObjectsPerSquare(),fr2.getMaxObjectsPerSquare());
        assertEquals(fr.getMoveTypes(),fr2.getMoveTypes());
    }
}