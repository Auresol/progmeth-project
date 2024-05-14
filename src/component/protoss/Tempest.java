package component.protoss;

import component.Races;
import control.GameControl;
import util.Vector2D;

public class Tempest extends BaseProtossEnemy{
    private static final double BASE_SPEED = 3;
    private static final double BASE_MAX_HEALTH = 70;
    private static final double BASE_DAMAGE = 6;
    private static final double BASE_MIN_ATTACK_RANGE = 400;
    private static final double BASE_MAX_ATTACK_RANGE = 560;
    private static final double BASE_ATTACK_FREQUENCY = 1;
    private static final double IMAGE_SCALE = 1.8;
    private static final double BASE_SHIELD_COUNT = 4;

    public Tempest(Vector2D position) {
        super("Tempest","Tempest.gif", position, BASE_MAX_HEALTH,BASE_SPEED, IMAGE_SCALE, BASE_MIN_ATTACK_RANGE, BASE_MAX_ATTACK_RANGE, BASE_DAMAGE, BASE_ATTACK_FREQUENCY, Races.PROTOSS, BASE_SHIELD_COUNT);
        setTarget(GameControl.getInstance().getCrystal());
    }
}
