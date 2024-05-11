package control;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import setting.Config;

public class KeyInputControl implements EventHandler<KeyEvent> {
    private static KeyInputControl instance;
    private static double sceneWidth;
    private static double sceneHeight;

    public KeyInputControl(double sceneWidth, double sceneHeight) {
        KeyInputControl.sceneWidth = sceneWidth;
        KeyInputControl.sceneHeight = sceneHeight;

    }
    public static KeyInputControl getInstance(){
        if(instance == null){
            instance = new KeyInputControl(Config.width,Config.height);
        }
        return instance;
    }


    @Override
    public void handle(KeyEvent event) {
        //System.out.println("Key : " + event.getText());
        switch (event.getCode()) {
            case W:
                MainControl.getInstance().getPlayer().moveUp();
                break;
            case S:
                MainControl.getInstance().getPlayer().moveDown();
                break;
            case A:
                MainControl.getInstance().getPlayer().moveLeft();
                break;
            case D:
                MainControl.getInstance().getPlayer().moveRight();
                break;
            // Add handlers for other keys as needed
            default:
                // Ignore other keys
                break;
        }
    }
}
