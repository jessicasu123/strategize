package ooga.model.engine;

public class PlayerInformationHolder {
    private final int myPlayerID;
    private final int myPlayerSpecialID;
    private String myPlayerImage;
    private String myPlayerSpecialImage;
    private boolean movesInPosDirection;

    public PlayerInformationHolder(int playerID, int playerSpecialID, String playerImage, String playerSpecialImage,
                                   boolean posDirection){
        myPlayerID = playerID;
        myPlayerSpecialID = playerSpecialID;
        myPlayerImage = playerImage;
        myPlayerSpecialImage = playerSpecialImage;
        movesInPosDirection = posDirection;
    }

    public int getPlayerID() {
        return myPlayerID;
    }

    public int getSpecialPlayerID() {
        return myPlayerSpecialID;
    }

    public String getPlayerImage(){
        return myPlayerImage;
    }

    public String getSpecialPlayerImage(){
        return myPlayerSpecialImage;
    }

    public boolean movesInPosDirection(){
        return movesInPosDirection;
    }

    public void setPlayerImage(String image){
        myPlayerImage = image;
    }

    public void setPlayerSpecialImage(String image){
        myPlayerSpecialImage = image;
    }

}
