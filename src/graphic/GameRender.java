package graphic;

import component.*;
import control.GameControl;
import control.KeyInputControl;
import control.MouseInputControl;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import setting.Config;
import util.Goto;
import util.Vector2D;

import java.util.*;

public class GameRender extends Pane {

    private static GameControl gameControl;
    private HashMap<Races, ArrayList<Base>> entities;
    private static GameRender instance;
    private static double cameraAngle = 0;
    private static double cameraTargetAngle = 0;
    private Player player;
    private Crystal crystal;
    private Text waveText;
    private static Races currentRace = Races.TERRAN;
    private static ImageView terranBackground;
    private static ImageView zergBackground;
    private static ImageView protossBackground;

    public GameRender() {

        terranBackground = new ImageView(new Image("ui/terran3.png"));
        terranBackground.setPreserveRatio(true);
        terranBackground.setFitWidth(Config.width);

        zergBackground = new ImageView(new Image("ui/zerg3.png"));
        zergBackground.setPreserveRatio(true);
        zergBackground.setFitWidth(Config.width);

        protossBackground = new ImageView(new Image("ui/protoss2.png"));
        protossBackground.setPreserveRatio(true);
        protossBackground.setFitWidth(Config.width);

        waveText = new Text("Wave 1");
        Font customFont = Font.loadFont(getClass().getClassLoader().getResource("ARCADECLASSIC.TTF").toExternalForm(), 250);
        waveText.setFill(Color.WHITE);
        waveText.setFont(customFont);
        //waveText.setTextAlignment(TextAlignment.CENTER);
        waveText.setLayoutX(Config.width/2 - 340);
        waveText.setLayoutY(Config.height/2);

        waveText.setVisible(true);

        System.out.println(this.getChildren());
    }
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

        this.getChildren().add(terranBackground);
        this.getChildren().add(zergBackground);
        zergBackground.setVisible(false);
        this.getChildren().add(protossBackground);
        protossBackground.setVisible(false);

        Label waveText = new Label("Wave 1");
        Font customFont = Font.loadFont(getClass().getClassLoader().getResource("ARCADECLASSIC.TTF").toExternalForm(), 200);
        //waveText.setPrefWidth(Config.width/4);
        waveText.setStyle("-fx-text-fill: white");
        waveText.setFont(customFont);
        //waveText.setTextAlignment(TextAlignment.CENTER);

//        waveText = new Text("Wave 1");
//        Font customFont = Font.loadFont(getClass().getClassLoader().getResource("ARCADECLASSIC.TTF").toExternalForm(), 150);
//        waveText.setFill(Color.WHITE);
//        waveText.setFont(customFont);
//        //waveText.setTextAlignment(TextAlignment.CENTER);
//        waveText.setLayoutX(Config.width/2 + 100);
//        waveText.setLayoutY(Config.height/2 + 100);
//
//        waveText.setVisible(true);

        this.getChildren().add(player.getRenderGroup());
        this.getChildren().add(crystal.getRenderGroup());
        this.getChildren().add(waveText);

        waveText.setVisible(false);

        //KeyInputControl keyInputControl = KeyInputControl.getInstance();
        //this.setOnKeyPressed(KeyInputControl.getInstance());
        //this.setOnKeyReleased(keyInputControl);

        Thread thread = new Thread(() -> {
            try {
                while (gameControl.isPlaying()) {
                    Platform.runLater(this::update);
                    Thread.sleep((long) (1000 * Config.timeStep));
                }
                //System.out.println("End of render");

            }catch (Exception e){};
        });

        thread.start();
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

    public double getCameraTargetAngle() {
        return cameraTargetAngle;
    }

    public void setCameraTargetAngle(double cameraTargetAngle) {
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

    public void nextWave(){
        //System.out.println(this.getChildren());
        Platform.runLater(() -> {
            this.getChildren().remove(waveText);
            this.getChildren().add(waveText);
        });
        waveText.setVisible(true);
        waveText.setText("Wave " + GameControl.getWave());

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
                Platform.runLater(() -> {
                    waveText.setVisible(false);
                });
            }catch (Exception e){}
        });

        thread.start();
    }

    public static void goToNextRaces(){
        switch (currentRace){
            case TERRAN -> setCurrentRace(Races.ZERG);
            case ZERG -> setCurrentRace(Races.PROTOSS);
            case PROTOSS -> setCurrentRace(Races.TERRAN);
        }

        GameRender.getInstance().changeBackground();
    }

    public static void goToPreviousRaces(){
        switch (currentRace){
            case TERRAN -> setCurrentRace(Races.PROTOSS);
            case ZERG -> setCurrentRace(Races.TERRAN);
            case PROTOSS -> setCurrentRace(Races.ZERG);
        }

        GameRender.getInstance().changeBackground();
    }

    private void changeBackground(){
        terranBackground.setVisible(false);
        zergBackground.setVisible(false);
        protossBackground.setVisible(false);

        switch (currentRace){
            case TERRAN -> terranBackground.setVisible(true);
            case ZERG -> zergBackground.setVisible(true);
            case PROTOSS -> protossBackground.setVisible(true);
        }
    }


}
