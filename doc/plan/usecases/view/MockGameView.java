package ooga.view;

import ooga.controller.ControllerFramework;
import ooga.controller.MockController;

public class MockGameView implements GameViewFramework {
    ControllerFramework myController;
    public MockGameView(MockController control){
        myController = control;
    }

    @Override
    public void update() {
        myController.getGameVisualInfo();
    }


}
