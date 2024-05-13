package control;

import com.sun.webkit.ThemeClient;
import component.*;

import component.zerg.Baneling;
import component.terran.Medic;
import component.terran.Solider;
import graphic.GameRender;
import javafx.application.Platform;
import component.spell.*;
import util.Vector2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

// All key event is
public class GameControl {
    private static GameControl instance;
    private KeyInputControl keyInputControl;
    private Player player;
    private Crystal crystal;
    private BaseSpell selectedSpell;
    private HashMap<Races, ArrayList<Base>> entities;

    public GameControl() {
        this.player = new Player(Vector2D.MID_SCREEN);
        this.crystal = new Crystal();
        entities = new HashMap<>();
        entities.put(Races.TERRAN, new ArrayList<>());
        entities.put(Races.ZERG, new ArrayList<>());
        entities.put(Races.PROTOSS, new ArrayList<>());
        entities.put(Races.ALL, new ArrayList<>());

        entities.get(Races.ALL).add(player);
        entities.get(Races.ALL).add(crystal);

        System.out.println(entities);

        startGame();
    }

    public void startGame(){
        Thread thread = new Thread(() -> {
            try {
                while(true) {
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

                    Thread.sleep(2000);
                }
            }catch (Exception e){
                System.out.println(e);
            }
        });

        thread.start();
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
//
//        selectedSpell = new Fireball(mousePosition, GameRender.getCurrentRace());
//        //selectedSpell.setDirection(mousePosition.subtract(getPlayer().getPosition()).getNormalize());
//        addEntity(selectedSpell);
//        selectedSpell.cast();
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

}
