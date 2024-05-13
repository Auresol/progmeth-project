package component.zerg;

import component.Base;
import component.Races;
import component.terran.BaseTerranEnemy;
import component.terran.Thor;
import control.GameControl;
import util.Vector2D;

import java.util.ArrayList;

public class Infestor extends BaseZergEnemy{
    private static final double BASE_SPEED = 0;
    private static final double BASE_MAX_HEALTH = 50;
    private static final double BASE_DAMAGE = 0;
    private static final double BASE_MIN_ATTACK_RANGE = 1000000000;
    private static final double BASE_MAX_ATTACK_RANGE = 1000000000;
    private static final double BASE_ATTACK_FREQUENCY = 1;
    private static final double IMAGE_SCALE = 1;
    private static final double ATTACK_BUFF = 1.05;
    private static final double SPEED_BUFF = 1.05;
    private static final double SPEED_DEBUFF = 9.5;


    public Infestor(Vector2D position) {
        super("Infestor","Infestor.gif", position, BASE_MAX_HEALTH,BASE_SPEED, IMAGE_SCALE, BASE_MIN_ATTACK_RANGE, BASE_MAX_ATTACK_RANGE, BASE_DAMAGE, BASE_ATTACK_FREQUENCY, Races.TERRAN);
        setTarget(GameControl.getInstance().getPlayer());
        applyEffect();
    }
    public void applyEffect(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        GameControl.getInstance().getPlayer().setSpeed(GameControl.getInstance().getPlayer().getSpeed() * SPEED_DEBUFF);
                        ArrayList<Base> entities = GameControl.getInstance().getEntities().get(getRaces());
                        for (int i = 0;i < entities.size();i++) {
                            Base entity = entities.get(i);
                            if (entity instanceof Zergling) {
                                Zergling castUnit = ((Zergling) entity);
                                if (getPosition().subtract(entity.getPosition()).getSize() <= BASE_MAX_ATTACK_RANGE) {
                                    castUnit.setSpeed(castUnit.getSpeed() * SPEED_BUFF);
                                    castUnit.setDamage(castUnit.getDamage() * ATTACK_BUFF);
                                }
                            }
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
