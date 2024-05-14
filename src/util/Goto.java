package util;

import com.sun.tools.javac.Main;
import control.AboutControl;
import control.GameControl;
import control.GameEndControl;
import control.MainControl;
import graphic.AboutRender;
import graphic.GameEndRender;
import javafx.stage.Stage;

public class Goto {
    private static Stage stage;
    private static Goto instance;
    public Goto(){}

    public static Goto getInstance(){
        if (instance == null) {
            instance = new Goto();
        }
        return instance;
    }

    public void gotoGame(){
        stopAllMusic();
        GameControl.getInstance().removeAll();
        GameControl.getInstance().load(stage);
    }
    public void gotoMain(){
        stopAllMusic();
        MainControl.getInstance().load(stage);
    }
    public void gotoEndGame(){
        stopAllMusic();
        GameEndRender.getInstance().setScore(GameControl.getWave());
        GameEndControl.getInstance().load(stage);
    }
    public void gotoAbout(){
        AboutControl.getInstance().load(stage);
    }
    private void stopAllMusic(){
        GameControl.stopBackgroundMusic();
        MainControl.stopBackgroundMusic();
        GameEndControl.stopBackgroundMusic();
    }

    public void setStage(Stage stage) {
        Goto.stage = stage;
    }
}
