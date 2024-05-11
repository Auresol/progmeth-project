package component;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import setting.Config;
import util.Vector2D;

public class Base {
    private String name;
    private Image image;
    private ImageView imageView;
    private Vector2D position;

    public Base(String name, String imageUrl, Vector2D position) {
        setName(name);
        setImage(imageUrl);
        setPosition(position);
    }

    public void updateSprite(){
        //System.out.println("UPDATE SPRITE");
        imageView.setX(position.getX() - image.getWidth()/2);
        imageView.setY(position.getY() - image.getHeight()/2);
        //imageView.setLayoutY(position.getY());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        //System.out.println(position);
        this.position = position;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(String imageUrl) {
        try {
            this.image = new Image(ClassLoader.getSystemResource(imageUrl).toString());
        }catch (Exception e){
            System.out.println("No " + imageUrl + " presented -> " + e);
        };

        this.imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(Config.height/10);
    }

    public ImageView getImageView() {
        return imageView;
    }
}
