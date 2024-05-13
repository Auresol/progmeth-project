package control;

import component.*;

import component.spell.BaseSpell;
import component.spell.Fireball;
import component.spell.LightningOrb;
import component.spell.Tornado;
import component.terran.Baneling;
import component.terran.Medic;
import component.terran.Solider;
import component.terran.Thor;
import graphic.GameRender;
import javafx.application.Platform;
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
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //System.out.println("Update");
                Vector2D initialPosition = new Vector2D();
                Solider solider = new Solider(initialPosition);
                solider.setDirection(crystal.getPosition().subtract(initialPosition));
                addEntity(Races.TERRAN, solider);

                initialPosition = new Vector2D();
                Medic medic = new Medic(initialPosition);
                medic.setDirection(crystal.getPosition().subtract(initialPosition));
                addEntity(Races.TERRAN, medic);

//                initialPosition = new Vector2D();
//                Thor thor = new Thor(initialPosition);
//                medic.setDirection(crystal.getPosition().subtract(initialPosition));
//                addEntity(Races.TERRAN, thor);

                initialPosition = new Vector2D();
                Baneling baneling = new Baneling(initialPosition);
                medic.setDirection(crystal.getPosition().subtract(initialPosition));
                addEntity(Races.TERRAN, baneling);

            }
        },0,4000);
    }

    public static GameControl getInstance(){
        if(instance == null){
            instance = new GameControl();
        }
        return instance;
    }
    public void useSpell(Vector2D mousePosition){
        selectedSpell = new Fireball(mousePosition, Races.TERRAN);
        //selectedSpell.setDirection(mousePosition.subtract(getPlayer().getPosition()).getNormalize());
        addEntity(Races.TERRAN,selectedSpell);
        selectedSpell.cast();
    }


    public void addEntity(Races races, Base entity){
        entities.get(races).add(entity);
        Platform.runLater(() -> {
            GameRender.getInstance().getChildren().add(entity.getRenderGroup());
            GameRender.getInstance().getChildren().add(entity.getInvisibleRenderGroup());
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
