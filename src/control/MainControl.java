package control;

public class MainControl {
    private static MainControl instance;

    public MainControl(){};
    public static MainControl getInstance(){
        if(instance == null){
            instance = new MainControl();
        }
        return instance;
    }
}
