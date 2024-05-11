package component;

import javafx.scene.image.ImageView;
import util.Vector2D;

public class Crystal extends BaseUnit{

    private static final String name = "Crystal";
    private static final String imageUrl = "crystal.png";
    private static final Vector2D position = Vector2D.ZERO;
    private static final double maxHealth = 100;

    public Crystal() {
        super(name, imageUrl, position, maxHealth, 0, Vector2D.ZERO);
    }

}
