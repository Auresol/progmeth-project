package component.protoss;

import component.Base;
import component.Races;
import component.terran.BaseTerranEnemy;
import component.terran.Thor;
import control.GameControl;
import util.Vector2D;

import java.util.ArrayList;

public class Disruptor extends BaseProtossEnemy{
    private static final double BASE_SPEED = 7.5;
    private static final double BASE_MAX_HEALTH = 20;
    private static final double BASE_DAMAGE = 2;
    private static final double BASE_MIN_ATTACK_RANGE = 150;
    private static final double BASE_MAX_ATTACK_RANGE = 210;
    private static final double BASE_ATTACK_FREQUENCY = 1;
    private static final double IMAGE_SCALE = 0.6;
    private static final double BASE_SHIELD_COUNT = 2;
    private static final double BASE_AURA_RANGE = 200;
    private static final double BASE_SLOW = 0.80;

    public Disruptor(Vector2D position) {
        super("Disruptor","Disruptor.gif", position, BASE_MAX_HEALTH,BASE_SPEED, IMAGE_SCALE, BASE_MIN_ATTACK_RANGE, BASE_MAX_ATTACK_RANGE, BASE_DAMAGE, BASE_ATTACK_FREQUENCY, Races.TERRAN, BASE_SHIELD_COUNT);
        setTarget(GameControl.getInstance().getCrystal());
        applyEffect();
    }

    public void applyEffect(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        if (getPosition().subtract(GameControl.getInstance().getPlayer().getPosition()).getSize() <= BASE_AURA_RANGE){
                            GameControl.getInstance().getPlayer().setSpeed(GameControl.getInstance().getPlayer().getSpeed() * BASE_SLOW);
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
}
