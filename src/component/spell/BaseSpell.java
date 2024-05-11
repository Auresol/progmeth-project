package component.spell;

import component.Base;
import component.Races;
import javafx.animation.Timeline;
import util.Vector2D;

import java.util.Timer;
import java.util.TimerTask;

public abstract class BaseSpell extends Base {
    private Races races;

    public BaseSpell(String name, String imageUrl, Vector2D position, Races races) {
        super(name, imageUrl, position);
        setRaces(races);
    }

    public Races getRaces() {
        return races;
    }

    public void setRaces(Races races) {
        this.races = races;
    }

    public void cast(){};
}
