import control.KeyInputControl;
import control.MainControl;
import control.MouseInputControl;
import graphic.GameRender;
import graphic.MainRender;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import setting.Config;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Main extends Application {
    private static GameRender gameRender;
    //private static Scene scene;
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Set window size

        // Create the main pane for rendering
        //gameRender = GameRender.getInstance();

        // Create the stage (main window)
        Stage stage = new Stage();
        stage.setTitle("JavaFX - Basic Player and Enemy");
        stage.setWidth(Config.width);
        stage.setHeight(Config.height);
        stage.setResizable(false);

        // Create the scene and set it on the stage

        //stage.setScene(new Scene(MainRender.load()))
        //Scene gameScene = new Scene(gameRender, Config.width, Config.width);
        MainControl.getInstance().load(stage);
        //stage.setScene(gameScene);

        //gameScene.addEventFilter(KeyEvent.ANY, KeyInputControl.getInstance());
        //gameScene.addEventFilter(MouseEvent.MOUSE_CLICKED, MouseInputControl.getInstance());


        // Show the stage
        //stage.show();
    }

    public static void Goto(){

    }

    public static void main(String[] args) {
        launch(args);
    }
}