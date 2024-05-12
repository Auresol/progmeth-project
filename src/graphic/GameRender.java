package graphic;

import component.*;
import control.GameControl;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import setting.Config;
import util.Vector2D;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class GameRender extends Pane {

    private static GameControl gameControl;
    private static ArrayList<Base> terranEntities;
    private static GameRender instance;
    private static final double cameraTurnSpeed = 10;
    private static Vector2D cameraPosition;
    private static Player player;
    private static Crystal crystal;

    public GameRender() {

        gameControl = GameControl.getInstance();
        player = gameControl.getPlayer();
        crystal = gameControl.getCrystal();

        terranEntities = gameControl.getEntities().get(Races.TERRAN);

        this.getChildren().add(player.getRenderGroup());
        this.getChildren().add(crystal.getRenderGroup());

        for(Base base : terranEntities){
            this.getChildren().add(base.getRenderGroup());

        }

        //KeyInputControl keyInputControl = KeyInputControl.getInstance();
        //this.setOnKeyPressed(KeyInputControl.getInstance());
        //this.setOnKeyReleased(keyInputControl);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //System.out.println("Update");
                GameRender.update();
            }
        },0,(long) (1000*Config.timeStep));
    }
    public static GameRender getInstance(){
        if(instance == null){
            instance = new GameRender();
        }
        return instance;
    }

    public static void update(){
//        Vector2D target = player.getPosition().subtract(cameraPosition);
//        player.setCameraMovementVector(player.getCameraMovementVector().add(target));
        player.updateSprite();
        player.step(Config.timeStep);

//        target = crystal.getPosition().subtract(cameraPosition);
//        crystal.setCameraMovementVector(crystal.getCameraMovementVector().add(target));
        crystal.updateSprite();

        if(terranEntities == null){
            return;
        }

        int i = 0;
        while(i < terranEntities.size()){
            Base base = terranEntities.get(i);

            if(base instanceof BaseUnit){
                if(((BaseUnit)base).isDestroyed()){
                    terranEntities.remove(i);
                    Platform.runLater(() -> GameRender.getInstance().getChildren().remove(base.getRenderGroup()));
                    continue;
                }
            }

            base.step(Config.timeStep);
            base.updateSprite();

            i++;
        }
    }

//    public static void computeCameraVector(Base entity){
//        Vector2D cameraTarget = entity.getPosition().subtract(cameraPosition);
//        Vector2D cameraRotatedVector =
//    }
//
//    public static void rotateCamera(int state){
//        if(state == -1){
//            gameControl.getPlayer().setCameraMovementVector(cameraTurnSpeed);
//        }
//    }
}
