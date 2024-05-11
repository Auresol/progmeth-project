package component.spell;

import component.Base;
import component.BaseUnit;
import component.Races;
import component.terran.BaseTerranEnemy;
import control.MainControl;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import util.Vector2D;

import javafx.scene.image.Image;

import java.util.ArrayList;


public class Fireball extends BaseSpell implements Upgradable {
    private static final double BASE_DAMAGE = 10;
    private static final double BASE_CAST_TIME = 2;
    private static String imageUrl = "fireball.png";
    public Fireball(Vector2D position, Races races) {
        super("FireBall",imageUrl,position,races);
        updateSprite();
    }
    public Fireball(Vector2D position, Empower empower,Races races) {
        super("FireBall",imageUrl,position,races);
        upgrade(empower);

    }
    private void upgrade(Empower empower){

    }

    public void cast() {
        //System.out.println("Start cast");
        ImageView fireball = getImageView();

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, e -> fireball.setVisible(true)),
                new KeyFrame(Duration.millis(BASE_CAST_TIME*0.75*1000), e -> fireball.setOpacity(0.5)),
                new KeyFrame(Duration.millis(BASE_CAST_TIME*1000), e -> fireball.setVisible(false)) // Hide fireball
        );

        timeline.setOnFinished(e -> {
            ArrayList<Base> entities = MainControl.getInstance().getEntities().get(this.getRaces());
            for(Base base : entities){
                if(base instanceof BaseUnit castUnit){
                    castUnit.setHealth(castUnit.getHealth() - BASE_DAMAGE);
                }
            }
        });

        timeline.play();
    }



}
