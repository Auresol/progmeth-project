package component.zerg;

import component.Races;
import control.GameControl;
import util.Vector2D;

public class Infestor extends BaseZergEnemy{

    private static final double BASE_MAX_HEALTH = 2.5;
    private static final double BASE_DAMAGE = 1.5;
    private static final double BASE_MIN_ATTACK_RANGE = 20;
    private static final double BASE_MAX_ATTACK_RANGE = 20;
    private static final double BASE_ATTACK_FREQUENCY = 1;
    private static final double IMAGE_SCALE = 0.21;

    public Infestor(Vector2D position) {
        super("Infestor","Infestor.png", position, BASE_MAX_HEALTH,0, IMAGE_SCALE, BASE_MIN_ATTACK_RANGE, BASE_MAX_ATTACK_RANGE, BASE_DAMAGE, BASE_ATTACK_FREQUENCY, Races.TERRAN);
        setTarget(GameControl.getInstance().getPlayer());
    }
}
