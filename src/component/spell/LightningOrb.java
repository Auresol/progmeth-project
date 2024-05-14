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
import util.Vector2D;

import javax.swing.plaf.TableHeaderUI;
import java.util.ArrayList;
import java.util.Vector;

public class LightningOrb extends BaseSpell implements Upgradable{
    private static final double BASE_DAMAGE = 1;
    private static final double BASE_CAST_TIME = 2;
    private static final double BASE_SPEED = 50;
    private static final double ZAP_RANGE = 200;
    private static final double IMAGE_SCALE = 0.3;
    private static double rangeBuff = 1;
    private static final String IMAGE_URL = "lighting_orb.png";

    public LightningOrb(Vector2D position, Races races) {
        //Vector2D playerPosition = GameControl.getInstance().getPlayer().getPosition();
        super("LightningOrb", IMAGE_URL, GameControl.getInstance().getPlayer().getPosition(), BASE_SPEED,IMAGE_SCALE,races);
        this.setDirection(position.subtract(GameControl.getInstance().getPlayer().getPosition()).getNormalize());
        cast();
        applyEffect();
    }

    public void upgrade(){
        rangeBuff = 2.5;
    }
    public void cast() {
        ImageView lightingOrb = getImageView();

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
                        ArrayList<Base> entities = GameControl.getInstance().getEntities().get(getRaces());
                        for (Base entity : entities) {
                            if (entity instanceof BaseUnit) {
                                BaseUnit castUnit = ((BaseUnit) entity);
                                if (getPosition().subtract(entity.getPosition()).getSize() <= ZAP_RANGE * rangeBuff) { //distanceTo not yet implement
                                    castUnit.setHealth(castUnit.getHealth() - BASE_DAMAGE);
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
