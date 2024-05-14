package control;

import com.sun.tools.javac.Main;
import component.Base;
import component.spell.BaseSpell;
import component.spell.Fireball;
import component.spell.Spell;
import graphic.GameRender;
import graphic.MainRender;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import setting.Config;
import util.Vector2D;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class KeyInputControl implements EventHandler<KeyEvent> {
    private static KeyInputControl instance;
    private boolean[] arrowKey = new boolean[]{false,false,false,false};
    private boolean[] cameraRotateState = new boolean[]{false, false};
    private static boolean[] spellKeyboardLayoutState = new boolean[]{false,false,false,false};
    private static KeyCode currentKeyToggle;

    public KeyInputControl() {}
    public static KeyInputControl getInstance(){
        if(instance == null){
            instance = new KeyInputControl();
        }
        return instance;
    }


    @Override
    public void handle(KeyEvent event) {

        boolean setTarget = event.getEventType().equals(KeyEvent.KEY_PRESSED);
        //System.out.println("Key : " + event.getEventType() + " , " + event.getCode());

        switch (event.getCode()){
            case W -> arrowKey[0] = setTarget;
            case S -> arrowKey[1] = setTarget;
            case A -> arrowKey[2] = setTarget;
            case D -> arrowKey[3] = setTarget;
            case Q -> rotateCameraHandler(event);
            case E -> rotateCameraHandler(event);

            case DIGIT1 -> spellKeyboardLayLoutHandler(event);
            case DIGIT2 -> spellKeyboardLayLoutHandler(event);
            case DIGIT3 -> spellKeyboardLayLoutHandler(event);
            case DIGIT4 -> spellKeyboardLayLoutHandler(event);
        }

        //System.out.println("Current key : " + currentKeyToggle);

        updateWalkDirection();
        //System.out.println("Key : " + event.getText());
    }

    private void rotateCameraHandler(KeyEvent event){
        boolean isKeyPress = event.getEventType().equals(KeyEvent.KEY_PRESSED);
        //System.out.println(event.getEventType().toString());
        int arrayPos = event.getCode().equals(KeyCode.Q)? 0:1;

        if(isKeyPress && !cameraRotateState[arrayPos]){
            if(arrayPos == 0){
                GameRender.setCameraTargetAngle(GameRender.getCameraTargetAngle() + 45);
                GameRender.goToNextRaces();
            }
            if(arrayPos == 1){
                GameRender.setCameraTargetAngle(GameRender.getCameraTargetAngle() - 45);
                GameRender.goToPreviousRaces();
            }
        }

        cameraRotateState[arrayPos] = isKeyPress;
    }

    private void spellKeyboardLayLoutHandler(KeyEvent event){
        boolean isKeyPress = event.getEventType().equals(KeyEvent.KEY_PRESSED);
        int arrayPos = 0;
        switch (event.getCode()){
            case DIGIT1 -> arrayPos = 0;
            case DIGIT2 -> arrayPos = 1;
            case DIGIT3 -> arrayPos = 2;
            case DIGIT4 -> arrayPos = 3;
        }

        if(isKeyPress && !spellKeyboardLayoutState[arrayPos]){
            if(currentKeyToggle == event.getCode()){
                currentKeyToggle = KeyCode.UNDEFINED;
            }else{
                currentKeyToggle = event.getCode();
            }
        }

        spellKeyboardLayoutState[arrayPos] = isKeyPress;
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

    public static KeyCode getCurrentKeyToggle() {
        return currentKeyToggle;
    }

    public void setCurrentKeyToggle(KeyCode currentKeyToggle) {
        KeyInputControl.currentKeyToggle = currentKeyToggle;
    }
}
