import control.GameControl;
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
import util.Goto;

public class Main extends Application {
    private static GameRender gameRender;
    private static Main instance;
    private static Stage stage;
    //private static Scene scene;
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Set window size

        // Create the main pane for rendering
        //gameRender = GameRender.getInstance();

        // Create the stage (main window)
        primaryStage = new Stage();
        primaryStage.setTitle("JavaFX - Basic Player and Enemy");
        primaryStage.setWidth(Config.width);
        primaryStage.setHeight(Config.height);
        primaryStage.setResizable(false);

        primaryStage.setOnCloseRequest(event -> {
            // Handle potential tasks before closing (optional)
            // You can add confirmation dialog or save data here (if applicable)

            // Close the application
            System.exit(0);
        });

        // Create the scene and set it on the stage

        //stage.setScene(new Scene(MainRender.load()))
        //Scene gameScene = new Scene(gameRender, Config.width, Config.width);
        Goto.getInstance().setStage(primaryStage);
        Goto.getInstance().gotoMain();
        //stage.setScene(gameScene);

        //gameScene.addEventFilter(KeyEvent.ANY, KeyInputControl.getInstance());
        //gameScene.addEventFilter(MouseEvent.MOUSE_CLICKED, MouseInputControl.getInstance());


        // Show the stage
        //stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}