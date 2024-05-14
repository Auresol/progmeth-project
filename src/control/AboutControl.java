package control;

import graphic.AboutRender;
import graphic.GameEndRender;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.nio.file.Paths;

public class AboutControl {

    private static AboutControl instance;
    private static Scene scene;
    private static MediaPlayer mediaPlayer;
    private static AboutRender aboutRender;

    public AboutControl(){
        aboutRender = AboutRender.getInstance();
        scene = new Scene(aboutRender);
    }

    public static AboutControl getInstance(){
        if(instance == null){
            instance = new AboutControl();
        }
        return instance;
    }

    public void load(Stage stage){

        stage.setScene(scene);
        stage.show();
    }

}
