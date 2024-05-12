package component.spell;

import component.Base;
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

public class LightningOrb extends BaseSpell implements Upgradable{
    private static final double BASE_DAMAGE = 1;
    private static final double BASE_CAST_TIME = 2;
    private static final double ZAP_RANGE = 200;
    private static final double IMAGE_SCALE = 0.3;
    private static final String imageUrl = "lighting_orb.png";

    public LightningOrb(Vector2D position, Races races) {
        super("LightningOrb", imageUrl, position, 20,IMAGE_SCALE,races);
        updateSprite();

        applyEffect();
    }

    public LightningOrb(Vector2D position, Empower empower, Races races) {
        super("LightningOrb", imageUrl, position, 0, IMAGE_SCALE,races);
        upgrade(empower);
    }

    public void upgrade(Empower empower){
        System.out.println("Upgrade");
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
                            if (entity instanceof BaseTerranEnemy) {
                                BaseTerranEnemy castUnit = ((BaseTerranEnemy) entity);
                                if (getPosition().subtract(entity.getPosition()).getSize() <= ZAP_RANGE) { //distanceTo not yet implement
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
