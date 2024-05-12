package component.spell;

import component.Base;
import component.BaseUnit;
import component.Races;
import component.terran.BaseTerranEnemy;
import control.GameControl;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import setting.Config;
import util.Vector2D;

import java.util.ArrayList;


public class Tornado extends BaseSpell implements Upgradable {
    private static final double BASE_DAMAGE = 0.5;
    private static final double BASE_CAST_TIME = 2;
    private static final double BASE_RADIUS = 500;
    private static final double BASE_PULL_FORCE = 100;
    private static final double IMAGE_SCALE = 0.05;
    private static String imageUrl = "tornado.png";
    public Tornado(Vector2D position, Races races) {
        super("Tornado",imageUrl,position,0,IMAGE_SCALE,races);
        updateSprite();
        applyEffect();
    }
    public Tornado(Vector2D position, Empower empower,Races races) {
        super("Tornado",imageUrl,position,0,IMAGE_SCALE,races);
        upgrade(empower);

    }
    public void upgrade(Empower empower){
        System.out.println("Upgrade");
    }

    public void cast() {
        //System.out.println("Start cast");
//        ImageView fireball = getImageView();
//
//        Timeline timeline = new Timeline();
//        timeline.getKeyFrames().addAll(
//                new KeyFrame(Duration.ZERO, e -> fireball.setOpacity(0.1)),
//                new KeyFrame(Duration.millis(BASE_CAST_TIME*0.2*1000), e -> fireball.setOpacity(0.3)),
//                new KeyFrame(Duration.millis(BASE_CAST_TIME*0.4*1000), e -> fireball.setOpacity(0.5)),
//                new KeyFrame(Duration.millis(BASE_CAST_TIME*0.6*1000), e -> fireball.setOpacity(0.75)),
//                new KeyFrame(Duration.millis(BASE_CAST_TIME*0.75*1000), e -> fireball.setOpacity(1)),
//                new KeyFrame(Duration.millis(BASE_CAST_TIME*1000), e -> fireball.setVisible(false)) // Hide fireball
//        );
//
//        timeline.setOnFinished(e -> {
//            ArrayList<Base> entities = GameControl.getInstance().getEntities().get(this.getRaces());
//            for(Base base : entities){
//                if(base instanceof BaseUnit) {
//                    if (base.getPosition().subtract(this.getPosition()).getSize() <= BASE_RADIUS) {
//                        BaseUnit castUnit = ((BaseUnit) base);
//                        castUnit.setHealth(castUnit.getHealth() - BASE_DAMAGE);
//                    }
//                }
//            }
//        });
//
//        timeline.play();
    }

    public void applyEffect(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {

                        ArrayList<Base> entities = GameControl.getInstance().getEntities().get(getRaces());
                        for (Base entity : entities) {
                            if (entity instanceof BaseTerranEnemy) {
                                BaseTerranEnemy castUnit = ((BaseTerranEnemy) entity);
                                if (getPosition().subtract(entity.getPosition()).getSize() <= BASE_RADIUS) { //distanceTo not yet implement
                                    Vector2D pullDirection = getPosition().subtract(castUnit.getPosition()).getNormalize();
                                    castUnit.setPosition(castUnit.getPosition().add(pullDirection.multiply(BASE_PULL_FORCE*Config.timeStep)));
                                }
                            }
                        }

                        Thread.sleep((long) (Config.timeStep * 1000));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        thread.start();

    }



}
