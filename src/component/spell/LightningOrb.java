package component.spell;

import component.Base;
import component.Races;
import component.terran.BaseTerranEnemy;
import control.MainControl;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import util.Vector2D;

import java.util.ArrayList;

public class LightningOrb extends BaseSpell implements Upgradable{
    private static final double BASE_DAMAGE = 10;
    private static final double BASE_CAST_TIME = 2;
    private static final double ZAP_RANGE = 50;
    private static final String imageUrl = "lighting_orb.png";

    public LightningOrb(Vector2D position, Races races) {
        super("LightningOrb", imageUrl, position, races);
    }

    public LightningOrb(Vector2D position, Empower empower, Races races) {
        super("LightningOrb", imageUrl, position, races);
        upgrade(empower);
    }

    private void upgrade(Empower empower){

    }
    public void cast() {
        ImageView lightingOrb = getImageView();

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, e -> lightingOrb.setVisible(true)),
                new KeyFrame(Duration.millis(BASE_CAST_TIME*0.75*1000), e -> lightingOrb.setOpacity(0.5)),
                new KeyFrame(Duration.millis(BASE_CAST_TIME*1000), e -> lightingOrb.setVisible(false)) // Hide fireball
        );

        timeline.setOnFinished(e -> {
            ArrayList<Base> entities = MainControl.getInstance().getEntities().get(this.getRaces());
            for(Base base : entities){
                if(base instanceof BaseTerranEnemy castUnit){
                    castUnit.setHealth(castUnit.getHealth() - BASE_DAMAGE);
                }
            }
        });

        timeline.play();
    }
    private void zapEnemiesInRange() {
        ArrayList<Base> entities = MainControl.getInstance().getEntities().get(this.getRaces());
        for (Base unit : entities) {
            if (unit instanceof BaseTerranEnemy castUnit) {
                if (this.getPosition().subtract(castUnit.getPosition()).getSize() <= ZAP_RANGE) { //distanceTo not yet implement
                    castUnit.setHealth(castUnit.getHealth() - BASE_DAMAGE);
                }
            }
        }
    }


}
