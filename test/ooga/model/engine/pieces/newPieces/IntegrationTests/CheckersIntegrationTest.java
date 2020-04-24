package ooga.model.engine.pieces.newPieces.IntegrationTests;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.ConvertableNeighborFinder;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.FindNeighborsBetweenCoordinates;
import ooga.model.engine.pieces.newPieces.MoveChecks.CheckOwnPiece;
import ooga.model.engine.pieces.newPieces.MoveChecks.MoveCheck;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CheckersIntegrationTest {

    ConvertableNeighborFinder myFinder = new FindNeighborsBetweenCoordinates();
    MoveCheck ownPiece = new CheckOwnPiece();
}
