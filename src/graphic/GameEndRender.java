package graphic;

import control.GameControl;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import setting.Config;
import util.CreateButton;
import util.Goto;

public class GameEndRender extends StackPane {

    private static int score = 0;
    public static GameEndRender instance;

    public GameEndRender() {
        //this.setBackground(new BackgroundImage(new Image("ui/background4.gif"), ));

        Image backgroundImage = new Image("ui/gameEnd.png");
        ImageView backgroundView = new ImageView(backgroundImage);

        backgroundView.setFitWidth(Config.width);
        backgroundView.setFitHeight(Config.height);

        ImageView gameOverText = new ImageView(new Image("ui/gameOver.png"));
        gameOverText.setPreserveRatio(true);
        gameOverText.setFitWidth(Config.width/2);

        Text textLabel = new Text("Score  " + GameControl.getWave());
        Font customFont = Font.loadFont(getClass().getClassLoader().getResource("ARCADECLASSIC.TTF").toExternalForm(), 150);
        textLabel.setFill(Color.WHITE);
        textLabel.setFont(customFont);
        textLabel.setTextAlignment(TextAlignment.CENTER);

        ImageView backButton = CreateButton.createButtonFromImage("ui/back01.png","ui/back02.png",0.3);
        backButton.setFitWidth(Config.width/4);
        backButton.setLayoutX(Config.width/2 - backButton.getFitWidth()/2);
        backButton.setLayoutY(2*Config.height/3 - 70);

        this.getChildren().addAll(backgroundView, gameOverText, textLabel, backButton);
        setAlignment(gameOverText, Pos.TOP_CENTER);
        gameOverText.setTranslateY(Config.height/7);
        setAlignment(textLabel, Pos.CENTER);
        textLabel.setTranslateY(-Config.height/20);
        setAlignment(backButton, Pos.BOTTOM_CENTER);
        backButton.setTranslateY(-Config.height/7);

    }

    public static GameEndRender getInstance(){
        if(instance == null){
            instance = new GameEndRender();
        }
        return instance;
    }

//    private void textInstallize(){
//        ImageView gameOverText = new ImageView(new Image("ui/gameOver.png"));
//        gameOverText.setPreserveRatio(true);
//        gameOverText.setFitWidth(Config.width/3);
//        this.getChildren().add(gameOverText);
//    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        GameEndRender.score = score;
    }

}
