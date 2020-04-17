package ooga.model.Data;

import ooga.model.data.JSONFileReader;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JSONFileReaderTest {
    private String fileNameGame = "tic-tac-toe.json";
    JSONFileReader fr = new JSONFileReader(fileNameGame);

    JSONFileReaderTest() throws FileNotFoundException {
    }

    @org.junit.jupiter.api.Test
    void getGameType() throws IOException {
        assertEquals("Tic-Tac-Toe",fr.getGameType());
    }

    @org.junit.jupiter.api.Test
    void getNeighborhood() throws IOException {
        List<String> neighb = new ArrayList<String>();
        assertEquals(neighb,fr.getNeighborhood());
    }

    @org.junit.jupiter.api.Test
    void loadFileConfigurationTest() throws IOException, ParseException {
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        for(int i = 0;i<3;i++){
            List<Integer> row = new ArrayList<Integer>();
            for(int j = 0;j<3;j++){
                row.add(0);
            }
            list.add(row);
        }
        assertEquals(list,fr.loadFileConfiguration());
    }

    @org.junit.jupiter.api.Test
    void loadFilePropertiesTest() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("PiecesMove","false");
        map.put("Player1Direction","false");
        map.put("EmptyState","0");
        map.put("State1","1");
        map.put("SpecialState1","-1");
        map.put("State2","2");
        map.put("SpecialState2","-1");
        map.put("possibleMove","");
        map.put("Image2","O.png");
        map.put("Gametype","Tic-Tac-Toe");
        map.put("Image1","X.png");
        map.put("SpecialImage1","");
        map.put("SpecialImage2","");
        map.put("Height","3");
        map.put("Color2","Black");
        map.put("Color1","Black");
        map.put("Width","3");
        map.put("Neighborhood","");
        assertEquals(map, fr.loadFileProperties());
    }

    @org.junit.jupiter.api.Test
    void saveToFile() {
    }
}