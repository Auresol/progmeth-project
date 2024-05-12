package component.spell;

import component.Base;
import component.Races;
import javafx.animation.Timeline;
import util.Vector2D;

import java.util.Timer;
import java.util.TimerTask;

public abstract class BaseSpell extends Base {

    public BaseSpell(String name, String imageUrl, Vector2D position, double speed, double imageScale, Races races) {
        super(name, imageUrl, position, speed, imageScale, races);
    }

    public void cast(){};
    public void applyEffect(){};
}
