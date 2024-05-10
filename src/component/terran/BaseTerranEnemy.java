package component.terran;

import component.Render;
import javafx.scene.image.ImageView;
import util.Vector2D;

public class BaseTerranEnemy implements Render {
    private ImageView imageView;
    private String name;
    private String imagePath;
    private Vector2D position;
    private double health;
    private Vector2D direction;
    private double speed;

    public BaseTerranEnemy(String name, String imagePath, Vector2D position, Vector2D direction, double speed, double health) {
        this.name = name;
        try {
            imageView = new ImageView(ClassLoader.getSystemResources(imagePath).toString());
        }catch (Exception e){};

        setPosition(position);
        setDirection(direction);
    }
    public void step(){
        setPosition(getPosition().add(direction));
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

    public Vector2D getDirection() {
        return direction;
    }

    public void setDirection(Vector2D direction) {
        this.direction = direction.getVectorWithSize(speed);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public boolean isDeath(){
        return this.health <= 0;
    }

    public void updateSprite(){
        imageView.setLayoutX(position.getX());
        imageView.setLayoutY(position.getY());
    }
    public ImageView getImageView() {
        return imageView;
    }

}
