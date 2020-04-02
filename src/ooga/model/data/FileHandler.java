package ooga.model.data;


import java.util.List;
import java.util.Map;

/**
 * PURPOSE OF INTERFACE:
 *  -
 *  -
 *  -
 */
public interface FileHandler {


    /**
     * METHOD PURPOSE:
     *  -
     */
    List<List<Integer>> loadFileConfiguration();

    /**
     * METHOD PURPOSE:
     *  -
     */
    Map<String, String> loadFileProperties();


    /**
     * METHOD PURPOSE:
     *  -
     */
    void saveToFile(String fileName,  Map<String, String> properties, List<List<Integer>> configurationInfo);
}
