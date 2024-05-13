package graphic;

import component.*;
import control.GameControl;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import setting.Config;
import util.Vector2D;

import java.util.*;

public class GameRender extends Pane {

    private static GameControl gameControl;
    private static HashMap<Races, ArrayList<Base>> entities;
    private static GameRender instance;
    private static final double cameraTurnSpeed = 10;
    private static double cameraAngle = 0;
    private static double cameraTargetAngle = 0;
    private static Player player;
    private static Crystal crystal;
    private static Races currentRace = Races.TERRAN;

    public GameRender() {

        gameControl = GameControl.getInstance();
        player = gameControl.getPlayer();
        crystal = gameControl.getCrystal();

        entities = gameControl.getEntities();

        this.getChildren().add(player.getRenderGroup());
        this.getChildren().add(crystal.getRenderGroup());

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
        double rotateAngle = cameraTargetAngle - cameraAngle;
//        updateEntity(player, rotateAngle);
//        updateEntity(crystal, rotateAngle);

        for(Races race : Races.values()){
            for(int i = 0;i < entities.get(race).size();i++){
                Base entity = entities.get(race).get(i);
                updateEntity(entity, rotateAngle);
            }
        }

        if(Math.abs(rotateAngle) > 1){
            cameraAngle += rotateAngle * 0.1;
            //System.out.println("WALK : " + rotateAngle);
        }



    }

    private static void updateEntity(Base entity, double rotateAngle){

        if(entity.isDestroyed()){
            GameControl.getInstance().removeEntity(entity);
            return;
        }

        if(Math.abs(rotateAngle) > 1) {

            // Cooler
            Vector2D rotateVector = crystal.getPosition().subtract(entity.getPosition()).getNormalize().multiply(rotateAngle * 5).rotateBy90(false);
            
            //Vector2D rotateVector = crystal.getPosition().subtract(entity.getPosition()).multiply(rotateAngle * 0.1).rotateBy90(clockwise);
//            if(entity instanceof Player){
//                //System.out.println("In vector : " + crystal.getPosition().subtract(entity.getPosition()).getNormalize());
//                System.out.println("Player rotate vector : " + rotateVector.toString());
//            }

            entity.setCameraMovementVector(rotateVector);

        }else{
            entity.setCameraMovementVector(Vector2D.ZERO);
        }

        entity.step(Config.timeStep);
        entity.updateSprite();

    }

    public static double getCameraTargetAngle() {
        return cameraTargetAngle;
    }

    public static void setCameraTargetAngle(double cameraTargetAngle) {
        GameRender.cameraTargetAngle = cameraTargetAngle;
    }

    public static Races getCurrentRace() {
        return currentRace;
    }

    public static void setCurrentRace(Races currentRace) {
        GameRender.currentRace = currentRace;
    }

    public static void goToNextRaces(){
        switch (currentRace){
            case TERRAN -> setCurrentRace(Races.ZERG);
            case ZERG -> setCurrentRace(Races.PROTOSS);
            case PROTOSS -> setCurrentRace(Races.TERRAN);
        }
    }

    public static void goToPreviousRaces(){
        switch (currentRace){
            case TERRAN -> setCurrentRace(Races.PROTOSS);
            case ZERG -> setCurrentRace(Races.TERRAN);
            case PROTOSS -> setCurrentRace(Races.ZERG);
        }
    }


}
