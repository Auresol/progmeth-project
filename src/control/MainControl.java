package control;

import com.sun.tools.javac.Main;
import graphic.MainRender;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.nio.file.Paths;

public class MainControl {
    private static MainControl instance;
    private static MainRender mainRender;
    private static Scene scene;
    private static MediaPlayer mediaPlayer;

    public MainControl(){
        mainRender = MainRender.getInstance();

        scene = new Scene(mainRender);
    };
    public static MainControl getInstance(){
        if(instance == null){
            instance = new MainControl();
        }
        return instance;
    }

    public void load(Stage stage) {

        FadeTransition ft = new FadeTransition(Duration.millis(3000), mainRender); // Adjust duration as needed
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.show();
            }
        });


        stage.setScene(scene);
        //stage.setTitle("My Game - Home Screen");
        mediaPlayer = new MediaPlayer(new Media(getClass().getResource("/sound/startMenu.mp3").toExternalForm()));
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        playBackgroundMusic();
        stage.show();

        ft.play();
    }

    private static void playBackgroundMusic() {

        mediaPlayer.setOnReady(() -> Platform.runLater(() -> mediaPlayer.play()));
    }

    public static void stopBackgroundMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}
