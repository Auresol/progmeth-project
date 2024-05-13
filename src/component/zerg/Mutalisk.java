package component.zerg;

import component.Base;
import component.Races;
import component.terran.BaseTerranEnemy;
import component.terran.Thor;
import control.GameControl;
import util.Vector2D;

import java.util.ArrayList;

public class Mutalisk extends BaseZergEnemy{

    private static final double BASE_SPEED = 22.5;
    private static final double BASE_MAX_HEALTH = 50;
    private static final double BASE_DAMAGE = 1.5;
    private static final double BASE_MIN_ATTACK_RANGE = 20;
    private static final double BASE_MAX_ATTACK_RANGE = 20;
    private static final double BASE_ATTACK_FREQUENCY = 1;
    private static final double IMAGE_SCALE = 2.1;

    public Mutalisk(Vector2D position) {
        super("Mutalisk","Mutalisk.gif", position, BASE_MAX_HEALTH,BASE_SPEED, IMAGE_SCALE, BASE_MIN_ATTACK_RANGE, BASE_MAX_ATTACK_RANGE, BASE_DAMAGE, BASE_ATTACK_FREQUENCY, Races.TERRAN);
        setTarget(GameControl.getInstance().getPlayer());
        applyEffect();
    }

    public void applyEffect(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Vector2D spawnPosition = new Vector2D(getPosition().getX(), getPosition().getY());
                        Zergling newZergling = new Zergling(spawnPosition);
                        GameControl.getInstance().addEntity(newZergling);
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
