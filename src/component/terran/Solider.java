package component.terran;

import util.Vector2D;

public class Solider extends BaseTerranEnemy {

    private static final double BASE_SPEED = 1;
    private static final double BASE_MAX_HEALTH = 10;
    private static final double BASE_DAMAGE = 1;

    public Solider(Vector2D position, Vector2D direction) {
        super("Solider","solider.png", position, BASE_MAX_HEALTH,BASE_SPEED,direction, BASE_DAMAGE);
    }

}
