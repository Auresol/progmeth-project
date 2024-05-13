package component.zerg;

import component.BaseUnit;
import component.Races;
import util.Vector2D;

public class BaseZergEnemy extends BaseUnit {
    public BaseZergEnemy(String name, String imageUrl, Vector2D position, double maxHealth, double speed, double imageScale, double minAttackRange, double maxAttackRange, double damage, double attackFrequency, Races races) {
        super(name, imageUrl, position, maxHealth, speed, imageScale, minAttackRange, maxAttackRange, damage, attackFrequency, races);
        setDamage(damage);
    }
}
