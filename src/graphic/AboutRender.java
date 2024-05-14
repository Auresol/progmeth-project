package graphic;

import control.GameControl;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import setting.Config;
import util.CreateButton;
import util.Goto;
import util.Vector2D;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Stack;

public class AboutRender extends StackPane {
    private static AboutRender instance;
    public AboutRender(){

        Image backgroundImage = new Image("ui/background4.gif");
        ImageView backgroundView = new ImageView(backgroundImage);

        backgroundView.setFitWidth(Config.width);
        backgroundView.setFitHeight(Config.height);

        this.getChildren().add(backgroundView);

        Text textLabel = new Text("Phudis  Tansakul  6632168921\nSamyan  Warlord  6631352821\n");
        Font customFont = Font.loadFont(getClass().getClassLoader().getResource("ARCADECLASSIC.TTF").toExternalForm(), 100);
        textLabel.setFill(Color.BLACK);
        textLabel.setFont(customFont);
        textLabel.setTextAlignment(TextAlignment.CENTER);

        ImageView backButton = CreateButton.createButtonFromImage("ui/back01.png","ui/back02.png",0.3);
        backButton.setFitWidth(Config.width/4);
        backButton.setLayoutX(Config.width/2 - backButton.getFitWidth()/2);
        backButton.setLayoutY(2*Config.height/3 - 70);

        this.getChildren().addAll(textLabel, backButton);
        setAlignment(textLabel, Pos.CENTER);
        setAlignment(backButton, Pos.BOTTOM_CENTER);
        backButton.setTranslateY(-Config.height/10);

    };

    public static AboutRender getInstance() {
        if(instance == null){
            instance = new AboutRender();
        }
        return instance;
    }


}
