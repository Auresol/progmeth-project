package component.protoss;

import component.BaseUnit;
import component.Races;
import util.Vector2D;

public class BaseProtossEnemy extends BaseUnit {
    private double shield;
    private double maxShield;
    public BaseProtossEnemy(String name, String imageUrl, Vector2D position, double maxHealth, double speed, double imageScale, double minAttackRange, double maxAttackRange, double damage, double attackChargeTime, Races races, double maxShield) {
        super(name, imageUrl, position, maxHealth, speed, imageScale, minAttackRange, maxAttackRange, damage, attackChargeTime, races);
        setDamage(damage);
        setShield(maxShield);
        applyEffect();
    }
    public void applyEffect(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        setShield(getShield() + 1);
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        thread.start();
    }

    public double getMaxShield() {
        return maxShield;
    }

    public void setMaxShield(double maxShield) {
        this.maxShield = maxShield;
    }

    public double getShield() {
        return shield;
    }

    public void setShield(double shield) {
        this.shield = Math.max(maxShield,shield);
    }

    @Override
    public void setHealth(double health) {
        if(getShield() > 0){
            setShield(getShield() - 1);
        }else {
            super.setHealth(health);
        }
    }
}
