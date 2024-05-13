package component;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import util.Vector2D;

import java.io.IOException;

public class Player extends BaseUnit{
    public static final String imageUrl = "player.png";
    public static final double BASE_MAX_HEALTH = 100;
    public static final double BASE_SPEED = 100;
    public static final double BASE_ATTACK_FREQUENCY = 1;
    private static final double IMAGE_SCALE = 0.3;
    private static final double BASE_DAMAGE = 2;

    public Player(Vector2D position){
        super("Player", imageUrl, position, BASE_MAX_HEALTH, BASE_SPEED, IMAGE_SCALE, -1,-1,BASE_DAMAGE, 0,Races.ALL);
        updateSprite();
    }


}
