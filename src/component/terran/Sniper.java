package component.terran;

import component.Races;
import control.GameControl;
import util.Vector2D;

public class Sniper extends BaseTerranEnemy{
    private static final double BASE_SPEED = 6;
    private static final double BASE_MAX_HEALTH = 5;
    private static final double BASE_DAMAGE = 3;
    private static final double BASE_MIN_ATTACK_RANGE = 350;
    private static final double BASE_MAX_ATTACK_RANGE = 490;
    private static final double BASE_ATTACK_FREQUENCY = 1;
    private static final double IMAGE_SCALE = 3;

    public Sniper(Vector2D position) {
        super("Sniper","Sniper.gif", position, BASE_MAX_HEALTH,BASE_SPEED, IMAGE_SCALE, BASE_MIN_ATTACK_RANGE, BASE_MAX_ATTACK_RANGE, BASE_DAMAGE, BASE_ATTACK_FREQUENCY, Races.TERRAN);
        setTarget(GameControl.getInstance().getPlayer());
    }
}
