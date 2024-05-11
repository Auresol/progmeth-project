import control.KeyInputControl;
import control.MainControl;
import control.MouseInputControl;
import graphic.MainRender;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import setting.Config;

import java.security.Key;

public class Main extends Application {
    private static MainRender mainRender;
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Set window size

        // Create the main pane for rendering

        // Create the stage (main window)
        Stage stage = new Stage();
        stage.setTitle("JavaFX - Basic Player and Enemy");
        stage.setWidth(Config.width);
        stage.setHeight(Config.height);

        // Create the scene and set it on the stage
        Scene scene = new Scene(MainRender.getInstance());
        stage.setScene(scene);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, KeyInputControl.getInstance());
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, MouseInputControl.getInstance());
        //scene.setOnKeyPressed(this::handle);


        // Show the stage
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}