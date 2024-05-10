package component;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import util.Vector2D;

import java.io.IOException;

public class Player implements Render{
    private ImageView imageView;
    private Vector2D position;
    private double speed;

    public Player(Vector2D position, double speed){
        try {
            imageView = new ImageView(ClassLoader.getSystemResource("player.png").toString());
        }catch (Exception e){
            System.out.println("No player.png presented : " + e);
        };

        imageView.setOnKeyPressed(this::handleKeyPressed);

        this.position = position;
        this.speed = speed;
        System.out.println("Intallize player : " + position.toString() + " , " + speed);
    }

    public void moveUp(){
        setPosition(position.add(0, -speed));
        updateSprite();
    }
    public void moveDown(){
        setPosition(position.add(0, speed));
        updateSprite();
    }
    public void moveLeft(){
        setPosition(position.add(-speed, 0));
        updateSprite();
    }
    public void moveRight(){
        setPosition(position.add(speed,0));
        updateSprite();
    }
    public void updateSprite(){
        //System.out.println("Update player : " + position.toString());
        imageView.setLayoutX(position.getX());
        imageView.setLayoutY(position.getY());
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public ImageView getImageView() {
        return imageView;
    }

    private void handleKeyPressed(KeyEvent event) {

        System.out.println("KEY event : " + event.getCode());
        switch (event.getCode()) {
            case W:
                this.moveUp();
                break;
            case S:
                this.moveDown();
                break;
            case A:
                this.moveLeft();
                break;
            case D:
                this.moveRight();
                break;
            default:
                // Ignore other keys
                break;
        }
    }
}
