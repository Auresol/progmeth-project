package control;

import graphic.GameEndRender;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.nio.file.Paths;

public class GameEndControl {

    private static GameEndControl instance;
    private static Scene scene;
    private static MediaPlayer mediaPlayer;
    private static GameEndRender gameEndRender;

    public GameEndControl(){
        gameEndRender = GameEndRender.getInstance();
        scene = new Scene(gameEndRender);
    }

    public static GameEndControl getInstance(){
        if(instance == null){
            instance = new GameEndControl();
        }
        return instance;
    }

    public void load(Stage stage){

        mediaPlayer = new MediaPlayer(new Media(Paths.get("res/sound/gameEnd.mp3").toUri().toString()));
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        GameEndControl.playBackgroundMusic();

        stage.setScene(scene);
        stage.show();
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
