package component.terran;

import component.Races;
import control.GameControl;
import util.Vector2D;

public class Solider extends BaseTerranEnemy {

    private static final double BASE_SPEED = 15;
    private static final double BASE_MAX_HEALTH = 10;
    private static final double BASE_DAMAGE = 1;
    private static final double BASE_MIN_ATTACK_RANGE = 50;
    private static final double BASE_MAX_ATTACK_RANGE = 70;
    private static final double BASE_ATTACK_FREQUENCY = 1;
    private static final double IMAGE_SCALE = 3;

    public Solider(Vector2D position) {
        super("Solider","Soldier.gif", position, BASE_MAX_HEALTH,BASE_SPEED, IMAGE_SCALE, BASE_MIN_ATTACK_RANGE, BASE_MAX_ATTACK_RANGE, BASE_DAMAGE, BASE_ATTACK_FREQUENCY, Races.TERRAN);
        setTarget(GameControl.getInstance().getPlayer());
    }

}
