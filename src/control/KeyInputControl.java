package control;

import com.sun.tools.javac.Main;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import setting.Config;
import util.Vector2D;

public class KeyInputControl implements EventHandler<KeyEvent> {
    private static KeyInputControl instance;
    private static double sceneWidth;
    private static double sceneHeight;
    private boolean[] arrowKey = new boolean[]{false,false,false,false};

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

        boolean setTarget = event.getEventType().equals(KeyEvent.KEY_PRESSED);
        //System.out.println("Key : " + event.getEventType() + " , " + event.getCode());

        switch (event.getCode()){
            case UP -> arrowKey[0] = setTarget;
            case DOWN -> arrowKey[1] = setTarget;
            case LEFT -> arrowKey[2] = setTarget;
            case RIGHT -> arrowKey[3] = setTarget;
        }

        updateWalkDirection();
        //System.out.println("Key : " + event.getText());
    }

    private void updateWalkDirection(){
        //System.out.println(arrowKey[0] + " " + arrowKey[1] + " " + arrowKey[2] + " " + arrowKey[3]);
        Vector2D result = Vector2D.ZERO;

        if(arrowKey[0]){
            result = result.add(Vector2D.UP);
        }
        if(arrowKey[1]){
            result = result.add(Vector2D.DOWN);
        }
        if(arrowKey[2]){
            result = result.add(Vector2D.LEFT);
        }
        if(arrowKey[3]){
            result = result.add(Vector2D.RIGHT);
        }

        GameControl.getInstance().getPlayer().setDirection(result);
    }
}
