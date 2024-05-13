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
    private double life = 1;

    public Tempest(Vector2D position) {
        super("Tempest","Tempest.png", position, BASE_MAX_HEALTH,BASE_SPEED, IMAGE_SCALE, BASE_MIN_ATTACK_RANGE, BASE_MAX_ATTACK_RANGE, BASE_DAMAGE, BASE_ATTACK_FREQUENCY, Races.TERRAN, BASE_SHIELD_COUNT);
        setTarget(GameControl.getInstance().getCrystal());
        applyEffect();
    }

    public Tempest(Vector2D position, double life) {
        super("Tempest","Tempest.png", position, BASE_MAX_HEALTH,BASE_SPEED, IMAGE_SCALE, BASE_MIN_ATTACK_RANGE, BASE_MAX_ATTACK_RANGE, BASE_DAMAGE, BASE_ATTACK_FREQUENCY, Races.TERRAN, BASE_SHIELD_COUNT);
        setTarget(GameControl.getInstance().getCrystal());
        setLife(life);
        applyEffect();
    }

    public void applyEffect(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        if (getHealth() <= 0 && life == 1){
                            Tempest tempest = new Tempest(getPosition(), 0);
                        }
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        thread.start();
    }

    public double getLife() {
        return life;
    }

    public void setLife(double life) {
        this.life = life;
    }
}
