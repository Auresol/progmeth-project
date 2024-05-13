package control;

import component.spell.Fireball;
import component.spell.Spell;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import util.Vector2D;

import java.security.Key;
import java.util.HashMap;
import java.util.Vector;

public class MouseInputControl implements EventHandler<MouseEvent> {
    //private static Vector2D currentMousePosition = Vector2D.ZERO;
    private static HashMap<KeyCode, Spell> spellMapping = new HashMap<>();
    private static MouseInputControl instance;
    public MouseInputControl(){
        spellMapping.put(KeyCode.DIGIT1, Spell.FIREBALL);
        spellMapping.put(KeyCode.DIGIT2, Spell.LIGHTING_ORB);
        spellMapping.put(KeyCode.DIGIT3, Spell.TORNADO);
    };

    public static MouseInputControl getInstance(){
        if(instance == null){
            instance = new MouseInputControl();
        }
        return instance;
    }

    @Override
    public void handle(MouseEvent event) {

        //System.out.println(clickX + ", " + clickY);
        double x = event.getSceneX();
        double y = event.getSceneY();

        Spell selectedSpell = spellMapping.get(KeyInputControl.getCurrentKeyToggle());

        if(selectedSpell != null){
            GameControl.getInstance().useSpell(new Vector2D(x,y), selectedSpell);
        }

    }

}
