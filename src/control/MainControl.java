package control;

import component.*;

import util.Vector2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

// All key event is
public class MainControl {
    private static MainControl instance;
    private Player player;
    private Crystal crystal;
    private HashMap<Races, ArrayList<Object>> units;

    public MainControl() {
        this.player = new Player(Vector2D.ZERO,1);
        this.crystal = new Crystal(Vector2D.ZERO);
        units = new HashMap<>();
        units.put(Races.TERRAN, new ArrayList<>());
        units.put(Races.ZERG, new ArrayList<>());
        units.put(Races.PROTOSS, new ArrayList<>());
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

    public Crystal getCrystal() {
        return crystal;
    }

    public void setCrystal(Crystal crystal) {
        this.crystal = crystal;
    }

    public HashMap<Races, ArrayList<Object>> getUnits() {
        return units;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
