package ooga.controller;

import ooga.model.data.FileHandler;
import ooga.model.engine.agent.Agent;
import ooga.model.engine.agent.evaluationFunctions.EvaluationFunction;
import ooga.model.engine.agent.evaluationFunctions.EvaluationFunctionFactory;
import ooga.model.engine.agent.winTypes.WinType;
import ooga.model.engine.agent.winTypes.WinTypeFactory;
import ooga.model.engine.neighborhood.Neighborhood;
import ooga.model.engine.neighborhood.NeighborhoodFactory;
import ooga.model.engine.pieces.GamePieceCreator;
import ooga.model.engine.pieces.MoveType;
import ooga.model.engine.pieces.MoveTypeFactory;
import ooga.model.engine.pieces.convertibleNeighborFinder.ConvertibleNeighborFinder;
import ooga.model.engine.pieces.convertibleNeighborFinder.ConvertibleNeighborFinderFactory;
import ooga.model.engine.pieces.moveChecks.MoveCheck;
import ooga.model.engine.pieces.moveChecks.MoveCheckFactory;
import ooga.model.engine.player.PlayerInfoHolder;
import ooga.model.exceptions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Creator {
    private FileHandler myFileHandler;
    private boolean isUserPlayer1;
    private PlayerInfoHolder myUserPlayerInfoHolder;
    private PlayerInfoHolder myAgentPlayerInfoHolder;
    private int myEmptyState;

    public Creator(FileHandler fileHandler, boolean isUserPlayer1, int emptyState) {
        myFileHandler = fileHandler;
        isUserPlayer1 = isUserPlayer1;
        myEmptyState = emptyState;
    }

    public List<Neighborhood> createNeighborhoods(int numRows, int numCols) throws InvalidNeighborhoodException {
        List<Neighborhood> neighborhoods = new ArrayList<>();
        NeighborhoodFactory neighborFactory = new NeighborhoodFactory();
        for(String neighborhood: myFileHandler.getNeighborhood()){
            neighborhoods.add(neighborFactory.createNeighborhood(neighborhood, numRows, numCols));
        }
        return neighborhoods;
    }

//    public PlayerInfoHolder createPlayerInfo(int playerID, Map<Integer, String> stateToImageMapping) throws InvalidConvertibleNeighborFinderException, InvalidMoveTypeException, InvalidMoveCheckException {
//        addPlayerStateImageInfoToMap(playerID, stateToImageMapping);
//        return makePlayer(playerID);
//    }

    /**Based on user selection, assigns the user and agent players to player 1 or 2 */
    public void createUserAndAgentPlayers(Map<Integer, String> stateToImageMapping) throws InvalidMoveCheckException, InvalidConvertibleNeighborFinderException, InvalidMoveTypeException {
        myUserPlayerInfoHolder = isUserPlayer1 ? makePlayer(1) : makePlayer(2);
        myAgentPlayerInfoHolder = isUserPlayer1 ? makePlayer(2) : makePlayer(1);
        addPlayerStateImageInfoToMap(1, stateToImageMapping);
        addPlayerStateImageInfoToMap(2, stateToImageMapping);
    }

    public GamePieceCreator makeGamePieceCreator(){
        return new GamePieceCreator(myUserPlayerInfoHolder, myAgentPlayerInfoHolder);
    }

    private PlayerInfoHolder makePlayer(int player) throws InvalidMoveCheckException, InvalidConvertibleNeighborFinderException, InvalidMoveTypeException {
        List<Integer> playerStates = myFileHandler.getPlayerStateInfo(player);
        int immovableState = myFileHandler.getImmovableStateForPlayer(player);
        List<MoveCheck> selfMoveChecks = createSelfMoveCheckForPlayer(playerStates, immovableState);
        List<MoveCheck> neighborMoveChecks = createNeighborMoveCheckForPlayer(playerStates, immovableState);
        List<MoveType> moveTypes = createMoveTypesForPlayer(player, playerStates);
        List<Integer> directions = myFileHandler.getDirectionForPlayer(player);
        boolean isPlayer1 = (isUserPlayer1 && player == 1) || (!isUserPlayer1 && player == 1);
        return new PlayerInfoHolder(playerStates, directions, selfMoveChecks, neighborMoveChecks, moveTypes, isPlayer1);
    }

    /** Creates a ConvertibleNeighborFinder based on specification from the data file */
    private ConvertibleNeighborFinder createConvertibleNeighborFinderForPlayer(List<Integer> stateToIgnore) throws InvalidConvertibleNeighborFinderException{
        String finderType = myFileHandler.getConverterType();
        return new ConvertibleNeighborFinderFactory().createNeighborhoodConverterFinder(finderType, stateToIgnore);
    }

    /**
     * Creates various MoveTypes specified in the game data file based on player selected
     * @param player for whom MoveTypes are being created
     * @param playerStates used by specific MoveTypes to consider a player's alternate states
     * @return a list of MoveTypes
     */
    private List<MoveType> createMoveTypesForPlayer(int player, List<Integer> playerStates) throws InvalidMoveTypeException, InvalidConvertibleNeighborFinderException {
        List<MoveType> moveTypes = new ArrayList<>();
        List<String> moveTypeNames = myFileHandler.getMoveTypes();
        boolean convertToEmptyState = myFileHandler.convertToEmptyState();
        List<Integer> statesToIgnore = myFileHandler.getStatesToIgnoreForPlayer(player);
        ConvertibleNeighborFinder neighborFinder = createConvertibleNeighborFinderForPlayer(statesToIgnore);
        int promotionRow = myFileHandler.getPromotionRowForPlayer(player);
        boolean onlyChangeOpponent = myFileHandler.getOnlyChangeOpponent();
        int objToCompare = myFileHandler.getNeighborNumObjectsToCompare();
        for(String moveTypeName: moveTypeNames){
            MoveType move = new MoveTypeFactory().createMoveType(moveTypeName,neighborFinder, myEmptyState,
                    convertToEmptyState,promotionRow,playerStates,onlyChangeOpponent, objToCompare);
            moveTypes.add(move);
        }
        return moveTypes;
    }

    /** Calls createMoveCheck to create MoveChecks the piece must perform on itself to calculate valid moves */
    private List<MoveCheck> createSelfMoveCheckForPlayer(List<Integer> playerStates, int immovableState) throws InvalidMoveCheckException {
        List<String> selfMoveCheck = myFileHandler.getSelfMoveChecks();
        return createMoveCheck(selfMoveCheck, playerStates, immovableState);
    }

    /** Calls createMoveCheck to create MoveChecks the piece must perform on its neighbors to calculate valid moves */
    private List<MoveCheck> createNeighborMoveCheckForPlayer(List<Integer> playerStates, int immovableState) throws InvalidMoveCheckException {
        List<String> neighborMoveChecks = myFileHandler.getNeighborMoveChecks();
        return createMoveCheck(neighborMoveChecks, playerStates, immovableState);
    }

    private List<MoveCheck> createMoveCheck(List<String> moveCheckNames, List<Integer> playerStates, int immovableState) throws InvalidMoveCheckException {
        List<MoveCheck> moveChecks = new ArrayList<>();
        int objToCompare = myFileHandler.getSelfNumObjectsToCompare();
        for (String moveCheckName: moveCheckNames) {
            MoveCheck moveCheck = new MoveCheckFactory().createMoveCheck(moveCheckName, myEmptyState,
                    playerStates,objToCompare, immovableState);
            moveChecks.add(moveCheck);
        }
        return moveChecks;
    }

    /** Creates the agent to carry out evaluations on the game state and potential moves */
    public Agent createAgent(List<List<Integer>> startingConfig) throws InvalidEvaluationFunctionException, InvalidWinTypeException {
        int winValue = myFileHandler.getWinValue();
        WinType winType = createWinType(winValue, startingConfig);
        List<EvaluationFunction> allEvals = createEvaluationFunctions(winValue, startingConfig);
        List<Integer> agentInfo = myAgentPlayerInfoHolder.getPlayerStates();
        List<Integer> userInfo = myUserPlayerInfoHolder.getPlayerStates();
        return new Agent(winType, allEvals, agentInfo, userInfo);
    }

    /** Creates the evaluation functions to be used by the agent to evaluate various moves, as specified in the game data file */
    private List<EvaluationFunction> createEvaluationFunctions(int winValue, List<List<Integer>> startingConfig) throws InvalidEvaluationFunctionException{
        int specialPieceIndex = myFileHandler.getSpecialPieceIndex();
        int userDirection = myUserPlayerInfoHolder.getDirections().get(0);
        int agentDirection = myAgentPlayerInfoHolder.getDirections().get(0);
        List<List<Integer>> boardWeights = myFileHandler.getBoardWeights();
        List<String> evalFunctions = myFileHandler.getEvaluationFunctions();
        List<EvaluationFunction> allEvals = new ArrayList<>();
        boolean checkCurrConfig = myFileHandler.shouldCheckCurrConfig();
        List<Integer> agentInfo = myAgentPlayerInfoHolder.getPlayerStates();
        List<Integer> userInfo = myUserPlayerInfoHolder.getPlayerStates();
        for(String eval: evalFunctions){
            EvaluationFunction evalFunc = new EvaluationFunctionFactory().createEvaluationFunction(eval,
                    specialPieceIndex, agentInfo, userInfo, boardWeights, agentDirection,userDirection,
                    winValue, checkCurrConfig,startingConfig);
            allEvals.add(evalFunc);
        }
        return allEvals;
    }

    /** Creates the WinTypes to be used by the agent to evaluate win status, as specified in the data file */
    private WinType createWinType(int winValue, List<List<Integer>> startingConfig) throws InvalidWinTypeException{
        String winTypeStr = myFileHandler.getWinType();
        int specialPieceIndex = myFileHandler.getSpecialPieceIndex();
        boolean checkCurrConfig = myFileHandler.shouldCheckCurrConfig();
        return new WinTypeFactory().createWinType(winTypeStr, myEmptyState, specialPieceIndex, winValue, checkCurrConfig, startingConfig);
    }

    private void addPlayerStateImageInfoToMap(int playerNum, Map<Integer, String> stateToImageMapping){
        Map<Integer, String> playerMapping = myFileHandler.getStateImageMapping(playerNum);
        for(Map.Entry<Integer, String> entry: playerMapping.entrySet()){
            stateToImageMapping.put(entry.getKey(), entry.getValue());
        }
    }
}