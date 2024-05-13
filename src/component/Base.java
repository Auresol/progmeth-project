package component;

import control.GameControl;
import control.MainControl;
import graphic.GameRender;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import setting.Config;
import util.Vector2D;

public class Base {
    private String name;
    private Image image;
    private ImageView imageView;
    private Vector2D position;
    private double speed;
    private Vector2D direction = Vector2D.ZERO;
    private Vector2D cameraMovementVector = Vector2D.ZERO;
    private Group renderGroup = new Group();
//    private Image invisibleImage;
//    private ImageView invisibleImageView;
//    private Group invisibleRenderGroup = new Group();
    private Circle redDot = new Circle(5, Color.RED);
    private double imageScale = 0.3;
    private Races races;
    private boolean isDestroyed = false;

    public Base(String name, String imageUrl, Vector2D position, double speed, double imageScale, Races races) {
        setName(name);
        setImageScale(imageScale);
        setImage(imageUrl);
        setPosition(position);
        setSpeed(speed);
        setRaces(races);

        renderGroup.getChildren().add(this.getImageView());

        //renderGroup.getChildren().add(redDot);
    }

    public void updateSprite(){

//        if(this instanceof Player){
//            System.out.println(renderAsMainRace);
//        }

        boolean renderAsMainRace = (getRaces() == GameRender.getCurrentRace() || GameRender.getCurrentRace() == Races.ALL || getRaces() == Races.ALL);

        Platform.runLater(() -> {
            renderGroup.setLayoutX(position.getX());
            renderGroup.setLayoutY(position.getY());

            if(renderGroup.isVisible() != renderAsMainRace){
                renderGroup.setVisible(renderAsMainRace);
            }
        });

//        if (!this.renderAsMainRace && renderAsMainRace) {
//            Platform.runLater(() -> {
//                renderGroup.setVisible(true);
//            });
//        } else if (this.renderAsMainRace && !renderAsMainRace) {
//            Platform.runLater(() -> {
//                renderGroup.setVisible(false);
//            });
//        }

//        if(renderGroup.isVisible() != renderAsMainRace){
//            Platform.runLater(() -> {
//                renderGroup.setVisible(renderAsMainRace);
//            });
//        }

        //System.out.println("UPDATE SPRITE");
        //imageView.setLayoutY(position.getY());
    }

    public void step(double time){
        setPosition(getPosition()
                .add(new Vector2D(getDirection().getX() * speed * time + cameraMovementVector.getX() * time
                        , getDirection().getY() * speed * time + cameraMovementVector.getY() * time))
        );
    }

    public void selfDelete(){
        GameControl.getInstance().removeEntity(this);
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
        imageView.setScaleX(imageScale);
        imageView.setScaleY(imageScale);

        // Calculate offsets for centering the scaled image
        double xOffset = -image.getWidth()/2;
        double yOffset = -image.getHeight()/2;

        // Translate the image to center it
        imageView.setLayoutX(xOffset);
        imageView.setLayoutY(yOffset);
        //imageView.setLayoutX(-image.getWidth()/2);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Vector2D getDirection() {
        return direction;
    }

    public void setDirection(Vector2D direction) {
        this.direction = direction;
    }

    public Vector2D getCameraMovementVector() {
        return cameraMovementVector;
    }

    public void setCameraMovementVector(Vector2D cameraMovementVector) {
        this.cameraMovementVector = cameraMovementVector;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Group getRenderGroup() {
        return renderGroup;
    }


    public void setRenderGroup(Group renderGroup) {
        this.renderGroup = renderGroup;
    }

    public double getImageScale() {
        return imageScale;
    }

    public void setImageScale(double scalingFactor) {
        this.imageScale = scalingFactor;
    }

    public Races getRaces() {
        return races;
    }

    public void setRaces(Races races) {
        this.races = races;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }

}
