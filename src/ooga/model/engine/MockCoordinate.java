package ooga.model.engine;

public class MockCoordinate implements Coordinate {

    public MockCoordinate(int startX, int startY){

    }

    @Override
    public int getXCoord() {
        return 0;
    }

    @Override
    public int getYCoord() {
        return 0;
    }

    @Override
    public boolean equals(Object o){
        return false;
    }

}
