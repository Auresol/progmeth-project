package component.spell;

import component.Base;
import component.BaseUnit;
import component.Races;
import control.GameControl;
import graphic.GameRender;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import util.Vector2D;

import java.util.ArrayList;


public class Fireball extends BaseSpell implements Upgradable {
    private static final double BASE_DAMAGE = 3;
    private static final double BASE_CAST_TIME = 2;
    private static final double BASE_RADIUS = 500;
    private static final double IMAGE_SCALE = 0.5;
    private static double buffDamage = 1;
    private static String IMAGE_URL = "fireball.png";
    public Fireball(Vector2D position, Races races) {
        super("FireBall",IMAGE_URL,position,0,IMAGE_SCALE,races);
        cast();
    }

    public void upgrade(){
        buffDamage = 5;
    }

    public void cast() {
        //System.out.println("Start cast");
        ImageView fireball = getImageView();

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, e -> fireball.setOpacity(0.1)),
                new KeyFrame(Duration.millis(BASE_CAST_TIME*0.2*1000), e -> fireball.setOpacity(0.3)),
                new KeyFrame(Duration.millis(BASE_CAST_TIME*0.4*1000), e -> fireball.setOpacity(0.5)),
                new KeyFrame(Duration.millis(BASE_CAST_TIME*0.6*1000), e -> fireball.setOpacity(0.75)),
                new KeyFrame(Duration.millis(BASE_CAST_TIME*0.75*1000), e -> fireball.setOpacity(1)),
                new KeyFrame(Duration.millis(BASE_CAST_TIME*1000), e -> fireball.setVisible(false)) // Hide fireball
        );

        timeline.setOnFinished(e -> applyEffect());

        timeline.play();
    }

    public void applyEffect(){
        ArrayList<Base> entities = GameControl.getInstance().getEntities().get(this.getRaces());
        for(Base base : entities){
            if(base instanceof BaseUnit) {
                if (base.getPosition().subtract(this.getPosition()).getSize() <= BASE_RADIUS) {
                    BaseUnit castUnit = ((BaseUnit) base);
                    castUnit.setHealth(castUnit.getHealth() - BASE_DAMAGE * buffDamage);
                }
            }
        }

        GameRender.getInstance().shakeCamera(30, 30,40, 1.5);
        selfDelete();
    }



}
