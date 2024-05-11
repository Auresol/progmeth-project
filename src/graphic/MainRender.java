package graphic;

import component.Base;
import component.Races;
import control.MainControl;
import javafx.scene.layout.Pane;
import setting.Config;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainRender extends Pane {

    private static MainControl mainControl;
    private static ArrayList<Base> terranEntities;
    private static MainRender instance;

    public MainRender() {

        mainControl = MainControl.getInstance();
        ArrayList<Base> terranEntities = mainControl.getEntities().get(Races.TERRAN);

        this.getChildren().add(mainControl.getPlayer().getImageView());
        for(Base base : terranEntities){
            this.getChildren().add(base.getImageView());

        }

        //KeyInputControl keyInputControl = KeyInputControl.getInstance();
        //this.setOnKeyPressed(KeyInputControl.getInstance());
        //this.setOnKeyReleased(keyInputControl);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //System.out.println("Update");
                MainRender.update();
            }
        },0,100);
    }
    public static MainRender getInstance(){
        if(instance == null){
            instance = new MainRender();
        }
        return instance;
    }

    public static void update(){
        mainControl.getPlayer().updateSprite();
        if(terranEntities == null){
            return;
        }
        for(Base base : terranEntities){
            base.updateSprite();
        }
    }
}
