package graphic;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import setting.Config;
import util.Vector2D;

import java.io.IOException;
import java.nio.file.Path;

public class MainRender extends Pane {
    private static MainRender instance;
    public MainRender(){

        Image backgroundImage = new Image("background4.gif");
        ImageView backgroundView = new ImageView(backgroundImage);

        backgroundView.setFitWidth(Config.width);
        backgroundView.setFitHeight(Config.height);

        this.getChildren().add(backgroundView);
        this.setOpacity(0.0); // Set initial opacity to 0 (invisible)

        ImageView gameLabel1 = createTextLabelFromImage("ui/gameLabel1.png", 0.6);
        gameLabel1.setLayoutY(Config.height/5);
        ImageView gameLabel2 = createTextLabelFromImage("ui/gameLabel2.png", 0.45);
        gameLabel2.setLayoutY(gameLabel1.getLayoutY() + gameLabel1.getFitHeight() + 150);

        // Initial Play Image
        ImageView playImageView1 = createButtonFromImageAtCenter("ui/play01.png", "ui/play02.png", 0.2);
        ImageView aboutImageView1 = createButtonFromImageAtCenter("ui/about01.png", "ui/about02.png", 0.1);
        aboutImageView1.setLayoutY(aboutImageView1.getLayoutY() + Config.height/6);

        this.getChildren().addAll(gameLabel1, gameLabel2, playImageView1, aboutImageView1);
    };

//    private static void loadAbout(){
//        root.getChildren().add()
//    }
//    private static void unloadAbout(){
//
//    }

    private static ImageView createTextLabelFromImage(String imagePath, double sizeCompareToScreenWidth){
        ImageView gameLabel = new ImageView(imagePath);
        gameLabel.setPreserveRatio(true);
        gameLabel.setFitWidth(Config.width * sizeCompareToScreenWidth);
        gameLabel.setLayoutX(Config.width/2 - gameLabel.getFitWidth()/2);

        return gameLabel;
    }

    private static ImageView createButtonFromImageAtCenter(String imagePath1, String imagePath2, double sizeCompareToScreenWidth){
        Image image1 = new Image(imagePath1);
        ImageView imageView1 = new ImageView(image1);
        imageView1.setPreserveRatio(true);
        imageView1.setFitWidth(Config.width * sizeCompareToScreenWidth);
        //imageView1.setFitHeight(image1.getHeight());
        // Position image (adjust as needed)
        imageView1.setLayoutX(Config.width/2 - imageView1.getFitWidth()/2);
        imageView1.setLayoutY(Config.height/2 + imageView1.getFitHeight()/2);

        // Handle image click
        imageView1.setOnMouseClicked(new EventHandler< javafx.scene.input.MouseEvent >() {
            @Override
            public void handle(MouseEvent event) {
                // Change image on click
                Image playImage2 = new Image(imagePath2);
                imageView1.setImage(playImage2);

                if(imagePath1.equals("ui/play01.png")){

                }else if(imagePath1.equals("ui/about01.png")){

                }
            }
        });

        return imageView1;
    }

    public static MainRender getInstance() {
        if(instance == null){
            instance = new MainRender();
        }
        return instance;
    }


}
