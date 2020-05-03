package ooga.model.engine.agent.evaluationFunctions;

import ooga.model.engine.Board;
import ooga.model.engine.BoardConfiguration;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PositionWeightsTest {

    @Test
    void testWeightsWithCornerStableAndDangerZoneConsiderations() {
        //SQUARE WEIGHTS FOR CORNER, STABLE, DANGER ZONES (EX. OTHELLO)
        List<Integer> weights_row1 = new ArrayList<>(List.of(4,-2,3,3,3,3,-2,4));
        List<Integer> weights_row2 = new ArrayList<>(List.of(-2,-2,-1,-1,-1,-1,-2,-2));
        List<Integer> weights_row3 = new ArrayList<>(List.of(3,-1,0,0,0,0,-1,3));
        List<Integer> weights_row4 = new ArrayList<>(List.of(3,-1,0,0,0,0,-1,3));
        List<Integer> weights_row5 = new ArrayList<>(List.of(3,-1,0,0,0,0,-1,3));
        List<Integer> weights_row6 = new ArrayList<>(List.of(3,-1,0,0,0,0,-1,3));
        List<Integer> weights_row7 = new ArrayList<>(List.of(-2,-2,-1,-1,-1,-1,-2,-2));
        List<Integer> weights_row8 = new ArrayList<>(List.of(4,-2,3,3,3,3,-2,4));
        BoardConfiguration othelloWeights = new BoardConfiguration(new ArrayList<>(List.of(weights_row1,weights_row2,weights_row3,weights_row4,
                weights_row5,weights_row6,weights_row7,weights_row8)));

        List<Integer> user = new ArrayList<>(List.of(1));
        List<Integer> agent = new ArrayList<>(List.of(2));
        int maxDirection = 1;
        int minDirection = 1;
        List<Integer> row1 = new ArrayList<>(List.of(2,1,2,2,2,2,1,2));
        List<Integer> row2 = new ArrayList<>(List.of(1,1,1,1,1,0,0,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,0,1,0,0,0,0,0));
        List<Integer> row4 = new ArrayList<>(List.of(0,0,1,0,2,0,0,0));
        List<Integer> row5 = new ArrayList<>(List.of(0,0,1,0,2,2,2,0));
        List<Integer> row6 = new ArrayList<>(List.of(0,0,1,0,2,0,0,0));
        List<Integer> row7 = new ArrayList<>(List.of(0,0,1,1,1,1,0,0));
        List<Integer> row8 = new ArrayList<>(List.of(1,2,2,2,2,2,2,2));
        BoardConfiguration boardConfig = new BoardConfiguration(new ArrayList<>(List.of(row1,row2,row3,row4,row5,row6,row7,row8)));
        //player 2 (max player) has better positions on the board - 3 corners, as well as the entire
        //stable border rows on the top and bottom
        PositionWeights othelloPosWeights = new PositionWeights(othelloWeights, agent, user, maxDirection, minDirection);
        assertEquals(42, othelloPosWeights.evaluate(boardConfig,boardConfig, false));
    }


    //SQUARE WEIGHTS FOR PROXIMITY TO OPPONENT SIDE (EX. CHECKERS)
    List<Integer> weights_row1 = new ArrayList<>(List.of(0,-2,0,-2));
    List<Integer> weights_row2 = new ArrayList<>(List.of(-1,0,-1,0));
    List<Integer> weights_row3 = new ArrayList<>(List.of(0,1,0,1));
    List<Integer> weights_row4 = new ArrayList<>(List.of(2,0,2,0));
    BoardConfiguration checkersWeights = new BoardConfiguration(new ArrayList<>(List.of(weights_row1,weights_row2,weights_row3,weights_row4)));

    @Test
    void testWeightsWithProximityToOpponentSide() {
        List<Integer> user = new ArrayList<>(List.of(3,4));
        List<Integer> agent = new ArrayList<>(List.of(1,2));
        int maxDirection = 1;
        int minDirection = -1;
        PositionWeights checkersPosWeightsEval = new PositionWeights(checkersWeights, agent, user, maxDirection, minDirection);

        //test better position for pawns for max
        List<Integer> row1 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row2 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,1,0,1));
        List<Integer> row4 = new ArrayList<>(List.of(3,0,3,0));
        BoardConfiguration boardConfig = new BoardConfiguration(new ArrayList<>(List.of(row1,row2,row3,row4)));
        assertEquals(6, checkersPosWeightsEval.evaluate(boardConfig,boardConfig, false));

        //test better position for pawns for min
        row1 = new ArrayList<>(List.of(0,1,0,1));
        row2 = new ArrayList<>(List.of(3,0,3,0));
        row3 = new ArrayList<>(List.of(0,0,0,0));
        row4 = new ArrayList<>(List.of(0,0,0,0));
        boardConfig = new BoardConfiguration(new ArrayList<>(List.of(row1,row2,row3,row4)));
        assertEquals(-6,checkersPosWeightsEval.evaluate(boardConfig,boardConfig, false));

        //test better position for kings for min
        row1 = new ArrayList<>(List.of(0,2,0,2));
        row2 = new ArrayList<>(List.of(4,0,4,0));
        row3 = new ArrayList<>(List.of(0,0,0,0));
        row4 = new ArrayList<>(List.of(0,0,0,0));
        boardConfig = new BoardConfiguration(new ArrayList<>(List.of(row1,row2,row3,row4)));
        assertEquals(-6,checkersPosWeightsEval.evaluate(boardConfig,boardConfig, false));

        //test better position for kings for max
        row1 = new ArrayList<>(List.of(0,0,0,0));
        row2 = new ArrayList<>(List.of(0,0,0,0));
        row3 = new ArrayList<>(List.of(0,2,0,2));
        row4 = new ArrayList<>(List.of(4,0,4,0));
        boardConfig = new BoardConfiguration(new ArrayList<>(List.of(row1,row2,row3,row4)));
        assertEquals(6,checkersPosWeightsEval.evaluate(boardConfig,boardConfig, false));

    }

}
