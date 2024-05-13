package component.terran;

import component.BaseUnit;
import component.Races;
import util.Vector2D;

public class BaseTerranEnemy extends BaseUnit{
    public BaseTerranEnemy(String name, String imageUrl, Vector2D position, double maxHealth, double speed, double imageScale, double minAttackRange, double maxAttackRange, double damage, double attackChargeTime, Races races) {
        super(name, imageUrl, position, maxHealth, speed, imageScale, minAttackRange, maxAttackRange, damage, attackChargeTime, races);
        setDamage(damage);
    }
}
