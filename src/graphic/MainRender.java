package graphic;

import component.Races;
import component.Render;
import control.MainControl;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import util.Vector2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MainRender extends Pane {

    private static MainControl mainControl;
    private static ArrayList<Object> terranUnits;

    public MainRender(double width, double height) {

        mainControl = MainControl.getInstance();
        terranUnits = mainControl.getUnits().get(Races.TERRAN);

        this.getChildren().add(mainControl.getPlayer().getImageView());
        for(Object render : terranUnits){
            if(render instanceof Render){
                this.getChildren().add(((Render) render).getImageView());
            }
        }

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //System.out.println("Update");
                MainRender.update();
            }
        },0,100);
    }

    public static void update(){
        mainControl.getPlayer().updateSprite();
        for(Object render : terranUnits){
            if(render instanceof Render){
                ((Render) render).updateSprite();
            }
        }
    }
}
