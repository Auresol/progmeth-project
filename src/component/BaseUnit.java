package component;

import util.Vector2D;

import java.util.HexFormat;

public class BaseUnit extends Base{
    private double maxHealth;
    private double health;
    private double speed;
    private Vector2D direction;

    public BaseUnit(String name, String imageUrl, Vector2D position, double maxHealth, double speed, Vector2D direction) {
        super(name, imageUrl,position);
        setMaxHealth(maxHealth);
        setHealth(maxHealth);
        setSpeed(speed);
        setDirection(direction);
    }

    public void step(double time){
        setPosition(getPosition().add(getDirection().multiply(speed * time)));
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Vector2D getDirection() {
        return direction;
    }

    public void setDirection(Vector2D direction) {
        this.direction = direction;
    }
}
