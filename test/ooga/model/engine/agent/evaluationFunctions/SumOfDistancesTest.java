package ooga.model.engine.agent.evaluationFunctions;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SumOfDistancesTest {
    List<Integer> user = new ArrayList<>(List.of(3,4));
    List<Integer> agent = new ArrayList<>(List.of(1,2));
    EvaluationFunction sumOfDistances = new SumOfDistances(1,agent, user);

    @Test
    void testEvaluateForMax() {
        List<Integer> row1 = new ArrayList<>(List.of(0,0,1,0));
        List<Integer> row2 = new ArrayList<>(List.of(0,2,0,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,0,3,0));
        List<Integer> row4 = new ArrayList<>(List.of(0,4,0,0));
        List<List<Integer>> boardConfig = new ArrayList<>(List.of(row1,row2,row3,row4));
        assertEquals((-1 * ((2 + 2) - (2 + 4))),sumOfDistances.evaluate(boardConfig,boardConfig,false));
    }

    @Test
    void testEvaluateForMin() {
        List<Integer> row1 = new ArrayList<>(List.of(0,0,2,0));
        List<Integer> row2 = new ArrayList<>(List.of(0,1,0,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,0,4,0));
        List<Integer> row4 = new ArrayList<>(List.of(0,3,0,0));
        List<List<Integer>> boardConfig = new ArrayList<>(List.of(row1,row2,row3,row4));
        assertEquals((-1 * ((2 + 4) - (2 + 2))),sumOfDistances.evaluate(boardConfig,boardConfig,false));
    }
}