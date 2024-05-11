package control;

import component.*;

import component.spell.BaseSpell;
import component.spell.Fireball;
import component.spell.LightningOrb;
import graphic.MainRender;
import util.Vector2D;

import java.util.ArrayList;
import java.util.HashMap;

// All key event is
public class MainControl {
    private static MainControl instance;
    private KeyInputControl keyInputControl;
    private Player player;
    private Crystal crystal;
    private BaseSpell selectedSpell;
    private HashMap<Races, ArrayList<Base>> entities;

    public MainControl() {
        this.player = new Player(Vector2D.MID_SCREEN);
        //this.crystal = new Crystal();
        entities = new HashMap<>();
        entities.put(Races.TERRAN, new ArrayList<>());
        entities.put(Races.ZERG, new ArrayList<>());
        entities.put(Races.PROTOSS, new ArrayList<>());
    }

//    public MainControl(Player player, Crystal crystal) {
//        this.player = player;
//        this.crystal = crystal;
//    }

    public static MainControl getInstance(){
        if(instance == null){
            instance = new MainControl();
        }
        return instance;
    }
    public void useSpell(Vector2D position){
        selectedSpell = new LightningOrb(position, Races.TERRAN);
        addEntity(Races.TERRAN,selectedSpell);
        selectedSpell.cast();
    }
    public void addEntity(Races races, Base entity){
        entities.get(races).add(entity);
        MainRender.getInstance().getChildren().add(entity.getImageView());

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
