package component.protoss;

import component.Races;
import control.GameControl;
import util.Vector2D;

public class Archons extends BaseProtossEnemy{
    private static final double BASE_SPEED = 15;
    private static final double BASE_MAX_HEALTH = 40;
    private static final double BASE_DAMAGE = 1.3;
    private static final double BASE_MIN_ATTACK_RANGE = 100;
    private static final double BASE_MAX_ATTACK_RANGE = 140;
    private static final double BASE_ATTACK_FREQUENCY = 1;
    private static final double IMAGE_SCALE = 2;
    private static final double BASE_SHIELD_COUNT = 3;

    public Archons(Vector2D position) {
        super("Archons","Archons.gif", position, BASE_MAX_HEALTH,BASE_SPEED, IMAGE_SCALE, BASE_MIN_ATTACK_RANGE, BASE_MAX_ATTACK_RANGE, BASE_DAMAGE, BASE_ATTACK_FREQUENCY, Races.PROTOSS, BASE_SHIELD_COUNT);
        setTarget(GameControl.getInstance().getCrystal());
    }
}
