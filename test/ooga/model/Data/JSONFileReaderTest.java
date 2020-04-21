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
    JSONFileReader fr;

    {
        try {
            fr = new JSONFileReader(fileNameGame, "3 x 3");
            fr.parseFile();
        } catch (Exception e) {
            System.out.println("file not found");
        }
    }

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
    void loadFilePropertiesTest(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("WinType","ConsecutivePieces");
        map.put("possibleMove","");
        map.put("Gametype","Tic-Tac-Toe");
        map.put("Height","3");
        map.put("Color2","Black");
        map.put("Color1","Black");
        map.put("Width","3");
        map.put("Neighborhood","");
        map.put("InitialConfig", "0,0,0;0,0,0;0,0,0");
        map.put("BoardWeights", "0,0,0;0,0,0;0,0,0");
        assertEquals(map, fr.loadFileProperties());
    }

    @org.junit.jupiter.api.Test
    void saveToFile() {
    }
}