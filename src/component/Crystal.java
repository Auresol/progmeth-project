package component;

import util.Vector2D;

public class Crystal extends BaseUnit{

    private static final String name = "Crystal";
    private static final String imageUrl = "crystal.png";
    private static final Vector2D position = Vector2D.MID_SCREEN;
    private static final double maxHealth = 100;
    private static final  double IMAGE_SCALE = 0.3;

    public Crystal() {
        super(name, imageUrl, position, maxHealth, 0,IMAGE_SCALE,-1,-1, 0, 1,Races.ALL);
    }

}
