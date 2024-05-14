package component.spell;

import component.Base;
import component.BaseUnit;
import component.Races;
import control.GameControl;
import javafx.scene.image.ImageView;
import util.Vector2D;

import java.util.ArrayList;
import java.util.HashSet;

public class Bullet extends BaseSpell implements Upgradable{
    private static final double BASE_DAMAGE = 30;
    private static final double BASE_SPEED = 500;
    private static final double DAMAGE_RANGE = 30;
    private static final double IMAGE_SCALE = 0.05;
    private static final String IMAGE_URL = "Bullet.png";
    private HashSet<BaseUnit> alreadyDamage = new HashSet<>();

    public Bullet(Vector2D position, Races races) {
        //Vector2D playerPosition = GameControl.getInstance().getPlayer().getPosition();
        super("Bullet", IMAGE_URL, GameControl.getInstance().getPlayer().getPosition(), BASE_SPEED,IMAGE_SCALE,races);
        this.setDirection(position.subtract(GameControl.getInstance().getPlayer().getPosition()).getNormalize());
        cast();
        applyEffect();
    }

    public void upgrade(){
        this.setSpeed(1000);
    }
    public void cast() {

//        ImageView lightingOrb = getImageView();

//        Timeline timeline = new Timeline();
//        timeline.getKeyFrames().addAll(
//                new KeyFrame(Duration.ZERO, e -> lightingOrb.setVisible(true)),
//                new KeyFrame(Duration.millis(BASE_CAST_TIME*0.75*1000), e -> lightingOrb.setOpacity(0.5)),
//                new KeyFrame(Duration.millis(BASE_CAST_TIME*1000), e -> lightingOrb.setVisible(false)) // Hide fireball
//        );
//
//        timeline.setOnFinished(e -> {
//            ArrayList<Base> entities = GameControl.getInstance().getEntities().get(this.getRaces());
//            for(Base base : entities){
//                if(base instanceof BaseTerranEnemy){
//                    castUnit.setHealth(castUnit.getHealth() - BASE_DAMAGE);
//                }
//            }
//        });

//        timeline.play();
    }
    public void applyEffect(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        if(getPosition().subtract(GameControl.getInstance().getCrystal().getPosition()).getSize() > 1500){
                            selfDelete();
                        }
                        ArrayList<Base> entities = GameControl.getInstance().getEntities().get(getRaces());
                        for (Base entity : entities) {
                            if (entity instanceof BaseUnit) {
                                BaseUnit castUnit = ((BaseUnit) entity);
                                if (getPosition().subtract(entity.getPosition()).getSize() <= DAMAGE_RANGE && !alreadyDamage.contains(castUnit)) { //distanceTo not yet implement
                                    alreadyDamage.add(castUnit);
                                    castUnit.setHealth(castUnit.getHealth() - BASE_DAMAGE);
                                }
                            }
                        }
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        thread.start();
    }
}
