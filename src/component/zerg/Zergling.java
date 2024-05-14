package component.zerg;

import component.Races;
import component.terran.BaseTerranEnemy;
import control.GameControl;
import util.Vector2D;

public class Zergling extends BaseZergEnemy {
        private static final double BASE_SPEED = 12;
        private static final double BASE_MAX_HEALTH = 2.5;
        private static final double BASE_DAMAGE = 1.5;
        private static final double BASE_MIN_ATTACK_RANGE = 20;
        private static final double BASE_MAX_ATTACK_RANGE = 20;
        private static final double BASE_ATTACK_FREQUENCY = 1;
        private static final double IMAGE_SCALE = 2.1;

        public Zergling(Vector2D position) {
            super("Zergling","Zergling.gif", position, BASE_MAX_HEALTH,BASE_SPEED, IMAGE_SCALE, BASE_MIN_ATTACK_RANGE, BASE_MAX_ATTACK_RANGE, BASE_DAMAGE, BASE_ATTACK_FREQUENCY, Races.ZERG);
            setTarget(GameControl.getInstance().getPlayer());
        }
}
