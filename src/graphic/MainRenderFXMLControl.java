package graphic;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import setting.Config;


public class MainRenderFXMLControl {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button startButton;
    @FXML
    private Button howToPlayButton;

    public void initialize() {
        anchorPane.setPrefWidth(Config.width);
        anchorPane.setPrefHeight(Config.height);
    }

    public void startButtonHandler(){
        System.out.println("START");
    }

    public void howToPlayButtonHandler(){
        System.out.println("HOW TO PLAY");
    }

}
