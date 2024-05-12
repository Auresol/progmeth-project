package graphic;

import javafx.scene.layout.Pane;

public class MainRender extends Pane {
    private static MainRender instance;

    public MainRender(){};
    public static MainRender getInstance(){
        if(instance == null){
            instance = new MainRender();
        }
        return instance;
    }


}
