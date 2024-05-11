package component.spell;

import component.Races;
import util.Vector2D;

public class LightningOrb extends BaseSpell implements Upgradable{
    private static final double BASE_DAMAGE = 10;
    private static final double BASE_CAST_TIME = 2;

    public LightningOrb(Vector2D position, Races races) {
        super("LightningOrb", position, races);
        cast();
    }

    public LightningOrb(Vector2D position, Empower empower, Races races) {
        super("LightningOrb", position, races);
        cast();
    }




}
