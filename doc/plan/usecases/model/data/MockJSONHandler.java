package ooga.model.data;

import java.util.List;
import java.util.Map;

public class MockJSONHandler implements FileHandler {


    public MockJSONHandler(String filename){

    }

    @Override
    public List<List<Integer>> loadFileConfiguration() {
        return null;
    }

    @Override
    public Map<String, String> loadFileProperties() {
        return null;
    }

    @Override
    public void saveToFile(String fileName, Map<String, String> properties, List<List<Integer>> configurationInfo) {

    }
}
