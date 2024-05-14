package control;

import graphic.MainRender;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainControl {
    private static MainControl instance;
    private static MainRender mainRender;

    public MainControl(){
        mainRender = MainRender.getInstance();
    };
    public static MainControl getInstance(){
        if(instance == null){
            instance = new MainControl();
        }
        return instance;
    }

    public void load(Stage stage) throws Exception {

        FadeTransition ft = new FadeTransition(Duration.millis(3000), mainRender); // Adjust duration as needed
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.show();
            }
        });

        Scene scene = new Scene(mainRender);
        stage.setScene(scene);
        //stage.setTitle("My Game - Home Screen");
        stage.show();

        ft.play();
    }
}
