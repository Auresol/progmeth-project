package component.zerg;

import component.Base;
import component.Races;
import component.terran.BaseTerranEnemy;
import component.terran.Thor;
import control.GameControl;
import util.Vector2D;

import java.util.ArrayList;

public class Infestor extends BaseZergEnemy{

    private static final double BASE_MAX_HEALTH = 2.5;
    private static final double BASE_DAMAGE = 1.5;
    private static final double BASE_MIN_ATTACK_RANGE = 20;
    private static final double BASE_MAX_ATTACK_RANGE = 20;
    private static final double BASE_ATTACK_FREQUENCY = 1;
    private static final double IMAGE_SCALE = 0.21;

    public Infestor(Vector2D position) {
        super("Infestor","Infestor.png", position, BASE_MAX_HEALTH,0, IMAGE_SCALE, BASE_MIN_ATTACK_RANGE, BASE_MAX_ATTACK_RANGE, BASE_DAMAGE, BASE_ATTACK_FREQUENCY, Races.TERRAN);
        setTarget(GameControl.getInstance().getPlayer());
        applyEffect();
    }

    public void applyEffect(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        ArrayList<Base> entities = GameControl.getInstance().getEntities().get(getRaces());
                        for (int i = 0;i < entities.size();i++) {
                            Base entity = entities.get(i);
                            if (entity instanceof BaseTerranEnemy) {
                                BaseTerranEnemy castUnit = ((BaseTerranEnemy) entity);
                                if (getPosition().subtract(entity.getPosition()).getSize() <= BASE_HEAL_RANGE)
                                {
                                    if (castUnit instanceof Thor){
                                        castUnit.setHealth(castUnit.getHealth() + 2 * BASE_HEAL);
                                    }else {
                                        castUnit.setHealth(castUnit.getHealth() + BASE_HEAL);
                                    }
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
