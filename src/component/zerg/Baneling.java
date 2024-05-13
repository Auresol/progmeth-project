package component.zerg;

import component.Base;
import component.BaseUnit;
import component.Races;
import component.terran.BaseTerranEnemy;
import control.GameControl;
import util.Vector2D;

import java.util.ArrayList;

public class Baneling extends BaseZergEnemy {
    private static final double BASE_SPEED = 19.5;
    private static final double BASE_MAX_HEALTH = 10;
    private static final double BASE_DAMAGE = 0.5;
    private static final double BASE_MIN_ATTACK_RANGE = 20;
    private static final double BASE_MAX_ATTACK_RANGE = 20;
    private static final double BASE_ATTACK_FREQUENCY = 0;
    private static final double IMAGE_SCALE = 2.1;
    public Baneling(Vector2D position) {
        super("Baneling", "Baneling.gif", position, BASE_MAX_HEALTH, BASE_SPEED, IMAGE_SCALE, BASE_MIN_ATTACK_RANGE, BASE_MAX_ATTACK_RANGE, BASE_DAMAGE, BASE_ATTACK_FREQUENCY, Races.TERRAN);
        setTarget(GameControl.getInstance().getCrystal());
        applyEffect();
    }

//    public void Detonate(){
//        if (this.getHealth() < BASE_MAX_HEALTH/10 || this.isDestroyed() || getPosition().subtract(getTarget().getPosition()).getSize() <= 50){
//            ArrayList<Base> entities = GameControl.getInstance().getEntities().get(getRaces());
//            for (Base entity : entities) {
//                if (entity instanceof BaseUnit castUnit) {
//                    if (getPosition().subtract(entity.getPosition()).getSize() <= BASE_MAX_ATTACK_RANGE) {
//                        castUnit.setHealth(castUnit.getHealth() - BASE_DAMAGE);
//                    }
//                }
//            }
//            this.selfDelete();
//        }
//    }
    public void applyEffect(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        if (getHealth() < BASE_MAX_HEALTH/10 || isDestroyed() || getPosition().subtract(getTarget().getPosition()).getSize() <= 100){
                            ArrayList<Base> entities = GameControl.getInstance().getEntities().get(getRaces());
                            for (Base entity : entities) {
                                if (entity instanceof BaseUnit castUnit) {
                                    if (getPosition().subtract(entity.getPosition()).getSize() <= BASE_MAX_ATTACK_RANGE) {
                                        castUnit.setHealth(castUnit.getHealth() - BASE_DAMAGE);
                                    }
                                }
                            }
                            setHealth(0);
                            selfDelete();
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
