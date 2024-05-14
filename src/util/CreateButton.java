package util;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import setting.Config;

public class CreateButton {
    public static ImageView createButtonFromImage(String imagePath1, String imagePath2, double sizeCompareToScreenWidth){
        Image image1 = new Image(imagePath1);
        ImageView imageView1 = new ImageView(image1);
        imageView1.setPreserveRatio(true);
        imageView1.setFitWidth(Config.width * sizeCompareToScreenWidth);
        //imageView1.setFitHeight(image1.getHeight());
        // Position image (adjust as needed)

        // Handle image click
        imageView1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Change image on click
                Image playImage2 = new Image(imagePath2);
                imageView1.setImage(playImage2);
                Goto.getInstance().gotoMain();
            }
        });

        return imageView1;
    }

}
