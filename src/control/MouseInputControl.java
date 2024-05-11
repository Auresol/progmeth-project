package control;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import util.Vector2D;

public class MouseInputControl implements EventHandler<MouseEvent> {
    private static MouseInputControl instance;
    public MouseInputControl(){};

    public static MouseInputControl getInstance(){
        if(instance == null){
            instance = new MouseInputControl();
        }
        return instance;
    }

    @Override
    public void handle(MouseEvent event) {
        double clickX = event.getSceneX();
        double clickY = event.getSceneY();

        //System.out.println(clickX + ", " + clickY);

        MainControl.getInstance().useSpell(new Vector2D(clickX,clickY));
    }
}
