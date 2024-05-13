package component.terran;

import component.Base;
import component.Races;
import control.GameControl;
import util.Vector2D;

import java.util.ArrayList;

public class Medic extends BaseTerranEnemy{
    private static final double BASE_SPEED = 15;
    private static final double BASE_MAX_HEALTH = 10;
    private static final double BASE_DAMAGE = 1;
    private static final double BASE_MIN_ATTACK_RANGE = 100;
    private static final double BASE_MAX_ATTACK_RANGE = 120;
    private static final double BASE_ATTACK_FREQUENCY = 1;
    private static final double BASE_HEAL = 2;
    private static final double BASE_HEAL_RANGE = 200;
    private static final double IMAGE_SCALE = 0.15;

    public Medic(Vector2D position) {
        super("Medic","medic.png", position, BASE_MAX_HEALTH,BASE_SPEED, IMAGE_SCALE, BASE_MIN_ATTACK_RANGE, BASE_MAX_ATTACK_RANGE, BASE_DAMAGE, BASE_ATTACK_FREQUENCY, Races.TERRAN);
        setTarget(GameControl.getInstance().getCrystal());
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
                                if (getPosition().subtract(entity.getPosition()).getSize() <= BASE_HEAL_RANGE) { //distanceTo not yet implement
                                    castUnit.setHealth(castUnit.getHealth() + BASE_HEAL);
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
