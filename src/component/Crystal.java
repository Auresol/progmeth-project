package component;

import util.Vector2D;

public class Crystal extends BaseUnit{

    private static final String NAME = "Crystal";
    private static final String IMAGE_URL = "crystal.png";
    private static final Vector2D POSITION = Vector2D.MID_SCREEN;
    private static final double BASE_HEALTH = 1000;
    private static final double IMAGE_SCALE = 0.3;

    public Crystal() {
        super(NAME, IMAGE_URL, POSITION, BASE_HEALTH, 0,IMAGE_SCALE,-1,-1, 0, 1,Races.ALL);
    }

}
