package component;

import javafx.scene.image.ImageView;
import util.Vector2D;

public class Crystal implements Render{
    private ImageView imageView;
    private Vector2D position;
    private double health = 100;
    private double maxHealth = 100;

    public Crystal(Vector2D position) {

        this.position = position;
        try {
            imageView = new ImageView(ClassLoader.getSystemResources("crystal.png").toString());
        }catch (Exception e){};

    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void updateSprite(){
        imageView.setLayoutX(position.getX());
        imageView.setLayoutY(position.getY());
    }
    public ImageView getImageView() {
        return imageView;
    }

}
