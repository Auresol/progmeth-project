package component;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import util.Vector2D;

public class BaseUnit extends Base{
    private double maxHealth;
    private double health;
    private Rectangle healthBar = new Rectangle(50, 10, Color.RED);
    private double maxAttackRange;
    private double minAttackRange;
    private double damage;
    private double attackFrequency;
    private boolean attackFlag = false;
    private BaseUnit target = null;
    private Thread attackThread;

    public BaseUnit(String name, String imageUrl, Vector2D position, double maxHealth, double speed, double imageScale, double minAttackRange, double maxAttackRange, double damage, double attackFrequency, Races races) {
        super(name, imageUrl,position, speed, imageScale, races);
        setMaxHealth(maxHealth);
        setHealth(maxHealth);
        setMinAttackRange(minAttackRange);
        setMaxAttackRange(maxAttackRange);
        setDamage(damage);
        setAttackFrequency(attackFrequency);

        healthBar.setLayoutX(-25);
        healthBar.setLayoutY(-this.getImage().getHeight()*getImageScale()/2 - 10);
        getRenderGroup().getChildren().add(healthBar);

        constructAttackThread();
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
        this.health = Math.min(maxHealth,health);
    }

    public boolean isDestroyed(){
        return health <= 0 || super.isDestroyed();
    }

    public double getMaxAttackRange() {
        return maxAttackRange;
    }

    public void setMaxAttackRange(double maxAttackRange) {
        this.maxAttackRange = maxAttackRange;
    }

    public double getMinAttackRange() {
        return minAttackRange;
    }

    public void setMinAttackRange(double minAttackRange) {
        this.minAttackRange = minAttackRange;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getAttackFrequency() {
        return attackFrequency;
    }

    public void setAttackFrequency(double attackFrequency) {
        this.attackFrequency = attackFrequency;
    }

    public BaseUnit getTarget() {
        return target;
    }

    public void setTarget(BaseUnit target) {
        this.target = target;
    }

    public void updateSprite(boolean renderAsMainRace){

        healthBar.setWidth(50*(health/maxHealth));
        super.updateSprite(renderAsMainRace);

    }

    public void step(double time){

        double distanceFromTarget = Double.MAX_VALUE;

        if(target != null){
            setDirection(target.getPosition().subtract(getPosition()).getNormalize());
            distanceFromTarget = target.getPosition().subtract(getPosition()).getSize();
        }


        if(distanceFromTarget > maxAttackRange){
            attackFlag = false;
            super.step(time);
            return;
        }

        if(distanceFromTarget < minAttackRange){
            setDirection(getDirection().reverse());
            super.step(time);
        }

        if(!attackFlag) {
            constructAttackThread();
            attackFlag = true;
            attackThread.start();
        }
    }

    private void constructAttackThread(){
        attackThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (attackFlag) {
                    try {
                        Thread.sleep((long) (attackFrequency * 1000));
                        if(!attackFlag){
                            break;
                        }
                        target.setHealth(target.getHealth() - damage);

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }


}
