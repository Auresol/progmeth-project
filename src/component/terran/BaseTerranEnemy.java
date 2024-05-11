package component.terran;

import component.BaseUnit;
import util.Vector2D;

public class BaseTerranEnemy extends BaseUnit{
    private double damage;
    public BaseTerranEnemy(String name, String imageUrl, Vector2D position, double maxHealth, double speed, Vector2D direction, double damage) {
        super(name, imageUrl, position, maxHealth, speed, direction);
        setDamage(damage);
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }
}
