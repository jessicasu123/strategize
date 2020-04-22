package ooga.model.engine.Agent.winTypes;

import java.util.List;

public interface WinType {

    boolean isWin(List<Integer> playerStates, List<List<Integer>> boardStateInfo, boolean noMovesLeft);
}
