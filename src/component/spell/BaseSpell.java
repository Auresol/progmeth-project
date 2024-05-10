package component.spell;

import component.Races;
import javafx.animation.Timeline;
import util.Vector2D;

import java.util.Timer;
import java.util.TimerTask;

public class BaseSpell {
    private String name;
    private Vector2D position;
    private Races races;

    public BaseSpell(String name, Vector2D position, Races races) {
        this.name = name;
        this.position = position;
        this.races = races;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public Races getRaces() {
        return races;
    }

    public void setRaces(Races races) {
        this.races = races;
    }
}
