package component;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import util.Vector2D;

import java.io.IOException;

public class Player extends BaseUnit{
    public static final String imageUrl = "player.png";
    public static final double BASE_MAX_HEALTH = 100;
    public static final double BASE_SPEED = 10;

    public Player(Vector2D position){
        super("Player", imageUrl, position, BASE_MAX_HEALTH, BASE_SPEED, Vector2D.ZERO);

    }

    public void moveUp(){
        setPosition(getPosition().add(0, -getSpeed()));
        updateSprite();
    }
    public void moveDown(){
        setPosition(getPosition().add(0, getSpeed()));
        updateSprite();
    }
    public void moveLeft(){
        setPosition(getPosition().add(-getSpeed(), 0));
        updateSprite();
    }
    public void moveRight(){
        setPosition(getPosition().add(getSpeed(),0));
        updateSprite();
    }

}
