package component.terran;

import component.Races;
import control.GameControl;
import util.Vector2D;

public class Thor extends BaseTerranEnemy{

    private static final double BASE_SPEED = 22.5;
    private static final double BASE_MAX_HEALTH = 100;
    private static final double BASE_DAMAGE = 2;
    private static final double BASE_MIN_ATTACK_RANGE = 200;
    private static final double BASE_MAX_ATTACK_RANGE = 280;
    private static final double BASE_ATTACK_FREQUENCY = 1;
    private static final double IMAGE_SCALE = 1.5;
    public Thor(Vector2D position) {
        super("Thor", "Thor.gif", position, BASE_MAX_HEALTH, BASE_SPEED, IMAGE_SCALE, BASE_MIN_ATTACK_RANGE, BASE_MAX_ATTACK_RANGE, BASE_DAMAGE, BASE_ATTACK_FREQUENCY, Races.TERRAN);
        setTarget(GameControl.getInstance().getCrystal());
    }
}
