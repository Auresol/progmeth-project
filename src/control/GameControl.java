package control;

import com.sun.webkit.ThemeClient;
import component.*;

import component.protoss.Tempest;
import component.zerg.Baneling;
import component.terran.Medic;
import component.terran.Solider;
import graphic.GameRender;
import javafx.application.Platform;
import component.spell.*;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import util.Goto;
import util.Vector2D;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

// All key event is
public class GameControl {
    private static GameControl instance;
    private static GameRender gameRender;
    private static MediaPlayer mediaPlayer;
    private static Scene scene;
    private Player player;
    private Crystal crystal;
    private HashMap<Races, ArrayList<Base>> entities;
    private boolean playing = false;
    private boolean lose = false;
    private static int wave = 0;

    public GameControl() {
        gameRender = GameRender.getInstance();
        scene = new Scene(gameRender);
    }

    public void load(Stage stage){
        this.player = new Player(Vector2D.MID_SCREEN);
        this.crystal = new Crystal();
        entities = new HashMap<>();
        entities.put(Races.TERRAN, new ArrayList<>());
        entities.put(Races.ZERG, new ArrayList<>());
        entities.put(Races.PROTOSS, new ArrayList<>());
        entities.put(Races.ALL, new ArrayList<>());

        entities.get(Races.ALL).add(player);
        entities.get(Races.ALL).add(crystal);

        mediaPlayer = new MediaPlayer(new Media(Paths.get("res/sound/backgroundMusic.mp3").toUri().toString()));
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        GameControl.playBackgroundMusic();
        
        scene.addEventFilter(KeyEvent.ANY, KeyInputControl.getInstance());
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, MouseInputControl.getInstance());

        stage.setScene(scene);
        stage.show();

        instance.startGame();

    }

    private void startGame(){
        playing = true;
        lose = false;
        Thread thread = new Thread(() -> {
            try {
                while(playing) {
                    //System.out.println("Update");
                    Vector2D initialPosition = new Vector2D();
                    Solider solider = new Solider(initialPosition);
                    solider.setDirection(crystal.getPosition().subtract(initialPosition));
                    addEntity(solider);
                    solider.updateSprite();

                    initialPosition = new Vector2D();
                    Medic medic = new Medic(initialPosition);
                    medic.setDirection(crystal.getPosition().subtract(initialPosition));
                    addEntity(medic);
                    medic.updateSprite();

//                initialPosition = new Vector2D();
//                Thor thor = new Thor(initialPosition);
//                medic.setDirection(crystal.getPosition().subtract(initialPosition));
//                addEntity(Races.TERRAN, thor);

                    initialPosition = new Vector2D();
                    Baneling baneling = new Baneling(initialPosition);
                    medic.setDirection(crystal.getPosition().subtract(initialPosition));
                    addEntity(baneling);
                    baneling.updateSprite();

                    initialPosition = new Vector2D();
                    Tempest tempest = new Tempest(initialPosition);
                    //medic.setDirection(crystal.getPosition().subtract(initialPosition));
                    addEntity(tempest);
                    baneling.updateSprite();

                    //gameRender.shakeCamera(30, 30,40, 1.5);
                    Thread.sleep(2000);
                }
            }catch (Exception e){
                System.out.println(e);
            }
        });

        thread.start();

        gameRender.start();
    }

    public static GameControl getInstance(){
        if(instance == null){
            instance = new GameControl();
        }
        return instance;
    }
    public void useSpell(Vector2D mousePosition, Spell spell){

        switch (spell){
            case FIREBALL -> addEntity(new Fireball(mousePosition, GameRender.getCurrentRace()));
            case TORNADO -> addEntity(new Tornado(mousePosition, GameRender.getCurrentRace()));
            case LIGHTING_ORB -> addEntity(new LightningOrb(mousePosition, GameRender.getCurrentRace()));
        }
    }


    public void addEntity(Base entity){
        Races races = entity.getRaces();
        entities.get(races).add(entity);
        Platform.runLater(() -> {
            GameRender.getInstance().getChildren().add(entity.getRenderGroup());
            if(entity instanceof BaseUnit) {
                GameRender.getInstance().getChildren().add(((BaseUnit)entity).getInvisibleRenderGroup());
            }
        });
    }

    public void removeEntity(Base entity){
        Races races = entity.getRaces();
        entities.get(races).remove(entity);
        Platform.runLater(() -> {
            GameRender.getInstance().getChildren().remove(entity.getRenderGroup());
            if(entity instanceof BaseUnit) {
                GameRender.getInstance().getChildren().remove(((BaseUnit)entity).getInvisibleRenderGroup());
            }
        });
    }

    private static void playBackgroundMusic() {

        mediaPlayer.setOnReady(() -> Platform.runLater(() -> mediaPlayer.play()));
    }

    public static void stopBackgroundMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void endGame(){
        GameControl.getInstance().setPlaying(false);

        for(Races race : Races.values()){
            for(int i = 0;i < entities.get(race).size();i++){
                Base entity = entities.get(race).get(i);
                removeEntity(entity);
            }
        }

        Goto.getInstance().gotoEndGame();
    }


    public Crystal getCrystal() {
        return crystal;
    }

    public void setCrystal(Crystal crystal) {
        this.crystal = crystal;
    }

    public HashMap<Races, ArrayList<Base>> getEntities() {
        return entities;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public static int getWave() {
        return wave;
    }
}
