package component.spell;

import component.Base;
import component.BaseUnit;
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

    private static final String imageUrl = "LightningOrb.png";

    public LightningOrb(Vector2D position, Races races) {
        super("LightningOrb", imageUrl, position, races);
        cast();
    }

    public LightningOrb(Vector2D position, Empower empower, Races races) {
        super("LightningOrb", imageUrl, position, races);
        upgrade(empower);
        cast();
    }

    private void upgrade(Empower empower){

    }
    public void cast() {
        ImageView LightningOrb = new ImageView(new Image("LightningOrb.png"));
        LightningOrb.setVisible(false);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, e -> LightningOrb.setVisible(true)),
                new KeyFrame(Duration.millis(BASE_CAST_TIME*0.75*1000), e -> {
                    LightningOrb.setOpacity(0.5);
                    zapEnemiesInRange();
                }),
                new KeyFrame(Duration.millis(BASE_CAST_TIME*1000), e -> LightningOrb.setVisible(false)) // Hide fireball
        );

        timeline.setOnFinished(e -> {
            zapEnemiesInRange();
        });

        timeline.play();
    }
    private void zapEnemiesInRange() {
        ArrayList<Base> entities = MainControl.getInstance().getEntities().get(this.getRaces());
        for (Base base : entities) {
            if (base instanceof BaseUnit castUnit) {
                if (this.getPosition().subtract(castUnit.getPosition()).getSize() <= ZAP_RANGE) { //distanceTo not yet implement
                    castUnit.setHealth(castUnit.getHealth() - BASE_DAMAGE);
                }
            }
        }
    }


}
