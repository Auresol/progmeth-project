package component.spell;

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
import java.util.Objects;


public class Fireball extends BaseSpell implements Upgradable{
    private static final double BASE_DAMAGE = 10;
    private static final double BASE_CAST_TIME = 2;
    public Fireball(Vector2D position, Races races) {
        super("FireBall",position,races);
        cast();
    }
    public Fireball(Vector2D position, Empower empower,Races races) {
        super("FireBall",position,races);
        upgrade(empower);
        cast();

    }
    private void upgrade(Empower empower){

    }

    private void cast() {
        ImageView fireball = new ImageView(new Image("fireball.png"));
        fireball.setVisible(false);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, e -> fireball.setVisible(true)),
                new KeyFrame(Duration.millis(BASE_CAST_TIME*0.75*1000), e -> fireball.setOpacity(0.5)),
                new KeyFrame(Duration.millis(BASE_CAST_TIME*1000), e -> fireball.setVisible(false)) // Hide fireball
        );

        timeline.setOnFinished(e -> {
            ArrayList<Object> units = MainControl.getInstance().getUnits().get(this.getRaces());
            for(Object unit : units){
                if(unit instanceof BaseTerranEnemy castUnit){
                    castUnit.setHealth(castUnit.getHealth() - BASE_DAMAGE);
                }
            }
        });

        timeline.play();
    }



}
