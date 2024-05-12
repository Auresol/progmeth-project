package setting;

import javafx.stage.Screen;

public class Config {
    public static Screen screen = Screen.getPrimary();
    public static double width = screen.getBounds().getMaxX();
    public static double height = screen.getBounds().getMaxY();
    public static double timeStep = 0.02;
}
