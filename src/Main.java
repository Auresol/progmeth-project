import graphic.MainRender;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    private static MainRender mainRender;
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Set window size
        double width = 800;
        double height = 600;

        // Create the main pane for rendering
        MainRender mainRender = new MainRender(width, height);

        // Create the stage (main window)
        Stage stage = new Stage();
        stage.setTitle("JavaFX - Basic Player and Enemy");
        stage.setWidth(width);
        stage.setHeight(height);

        // Create the scene and set it on the stage
        Scene scene = new Scene(mainRender);
        stage.setScene(scene);

        // Show the stage
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}