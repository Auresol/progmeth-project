package graphic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MainRender extends Pane {
    private static MainRender instance;

    public MainRender(){};
    public static Parent load() throws Exception {
        FXMLLoader loader = new FXMLLoader(MainRender.class.getResource("/start.fxml"));

        return loader.load();
    }

    public static MainRender getInstance() throws IOException {
        if(instance == null){
            instance = new MainRender();
        }
        return instance;
    }


}
