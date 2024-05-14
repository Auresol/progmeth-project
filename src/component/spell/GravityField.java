package component.spell;

import component.Base;
import component.BaseUnit;
import component.Player;
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
import java.util.HashMap;
import java.util.HashSet;


public class GravityField extends BaseSpell {
    private static final double BASE_DAMAGE = 0.5;
    private static final double BASE_CAST_TIME = 0;
    private static final double BASE_RADIUS = 1000000;
    private static final double BASE_SLOW_RATIO = 0.1;
    private static final double IMAGE_SCALE = 0.05;
    private static final double BASE_SPELL_TIME = 5;
    private HashMap<BaseUnit, Double> baseUnits = new HashMap<BaseUnit, Double>();
    private static String IMAGE_URL = "GravityField.png";
    public GravityField(Vector2D position, Races races) {
        super("GravityField",IMAGE_URL,position,0,IMAGE_SCALE,races);
        cast();
        applyEffect();
        selfDestroy();
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
                while(!isDestroyed()) {
                    try {
                        ArrayList<Base> entities = GameControl.getInstance().getEntities().get(getRaces());
                        for (int i = 0;i < entities.size();i++) {
                            Base entity = entities.get(i);
                            if (entity instanceof BaseUnit) {
                                BaseUnit castUnit = ((BaseUnit) entity);
                                if(baseUnits.get(castUnit) == null){
                                    baseUnits.put(castUnit, castUnit.getSpeed());
                                    castUnit.setSpeed(castUnit.getSpeed()*BASE_SLOW_RATIO);
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

    private void selfDestroy(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep((long) (BASE_SPELL_TIME * 1000));
                        for(BaseUnit unit : baseUnits.keySet()){
                            if(unit != null){
                                unit.setSpeed(baseUnits.get(unit));
                            }
                        }
                        setDestroyed(true);
                        selfDelete();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thread.start();
    }



}
