package graphic;

import component.*;
import control.GameControl;
import control.KeyInputControl;
import control.MouseInputControl;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import setting.Config;
import util.Goto;
import util.Vector2D;

import java.util.*;

public class GameRender extends Pane {

    private static GameControl gameControl;
    private HashMap<Races, ArrayList<Base>> entities;
    private static GameRender instance;
    private static final double cameraTurnSpeed = 10;
    private static double cameraAngle = 0;
    private static double cameraTargetAngle = 0;
    private Player player;
    private Crystal crystal;
    private static Races currentRace = Races.TERRAN;

    public GameRender() {}
    public static GameRender getInstance(){
        if(instance == null){
            instance = new GameRender();
        }
        return instance;
    }
    public void start(){
        gameControl = GameControl.getInstance();
        player = gameControl.getPlayer();
        crystal = gameControl.getCrystal();

        entities = gameControl.getEntities();

        this.getChildren().add(player.getRenderGroup());
        this.getChildren().add(crystal.getRenderGroup());

        //KeyInputControl keyInputControl = KeyInputControl.getInstance();
        //this.setOnKeyPressed(KeyInputControl.getInstance());
        //this.setOnKeyReleased(keyInputControl);

        Thread thread = new Thread(() -> {
            try {
                while (gameControl.isPlaying()) {
                    Platform.runLater(() -> {
                        update();
                    });
                    Thread.sleep((long) (1000 * Config.timeStep));
                }
                //System.out.println("End of render");

            }catch (Exception e){};
        });

        thread.start();
    }

    private void clear(){
        player = null;
        crystal = null;

        for(Node node : this.getChildren()){
            if(node instanceof Group){
                this.getChildren().remove(node);
            }
        }
    }

    private void update(){
        if(!GameControl.getInstance().isPlaying()){
            return;
        }
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

    private void updateEntity(Base entity, double rotateAngle){

        if(entity.isDestroyed()){
            if(entity instanceof Player || entity instanceof Crystal){
                GameControl.getInstance().endGame();
                return;
            }
            GameControl.getInstance().removeEntity(entity);
            return;
        }

        if(Math.abs(rotateAngle) > 1) {

            // Cooler
            Vector2D rotateVector = this.crystal.getPosition().subtract(entity.getPosition()).getNormalize().multiply(rotateAngle * 5).rotateBy90(false);
            
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

    public void shakeCamera(int numShakes, long duration, double shakeStrengthIn, double decayShakeStrength) {

        Thread thread = new Thread(() -> {

            try {
                TranslateTransition tt = new TranslateTransition(Duration.millis(duration));
                tt.setNode(this);

                double shakeStrength = shakeStrengthIn;
                double originalX = this.getLayoutX(); // Store original X position
                double originalY = this.getLayoutY(); // Store original Y position

                double randomX = originalX;
                double randomY = originalY;
                double previousRandomX = randomX;
                double previousRandomY = randomY;

                for (int i = 0; i < numShakes; i++) {
                    randomX = Math.random() * shakeStrength - shakeStrength / 2;
                    randomY = Math.random() * shakeStrength - shakeStrength / 2;

                    tt.setFromX(previousRandomX);
                    tt.setFromY(previousRandomY);
                    tt.setToX(randomX);
                    tt.setToY(randomY);

                    previousRandomX = randomX;
                    previousRandomY = randomY;

                    shakeStrength -= decayShakeStrength;

                    Platform.runLater(tt::play);
                    Thread.sleep(duration);
                }

                // After shakes, animate back to original position
                TranslateTransition reset = new TranslateTransition(Duration.millis(duration));
                reset.setNode(this);
                reset.setFromX(randomX);
                reset.setFromY(randomY);
                reset.setToX(originalX);
                reset.setToY(originalY);
                //reset.setDelay(Duration.millis(numShakes * duration)); // Delay after shakes
                Platform.runLater(reset::play);
            }catch (Exception e){
                System.out.println(e);
            }
        });

        thread.start();
    }

//    public static void darken(Node node, long duration) {
//        ColorAdjust colorAdjust = new ColorAdjust();
//        node.setEffect(colorAdjust);
//        Timeline timeline = new Timeline();
//        timeline.set(Duration.millis(duration));
//        timeline.play();
//        timeline.getKeyFrames().add(new javafx.animation.KeyFrame(Duration.millis(duration),
//                "brightness", 0, colorAdjust.getBrightness()));
//        colorAdjust.setBrightness(0); // Set final brightness after animation
//    }

    private void displayNextWave(){

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
