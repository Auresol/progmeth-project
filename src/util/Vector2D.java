package util;

import setting.Config;

import java.util.Random;

public class Vector2D {
    private static final Random random = new Random();
    public static final Vector2D ZERO = new Vector2D(0,0);
    public static final Vector2D UP = new Vector2D(0,-1);
    public static final Vector2D DOWN = new Vector2D(0,1);
    public static final Vector2D RIGHT = new Vector2D(1, 0);
    public static final Vector2D LEFT = new Vector2D(-1,0);
    public static Vector2D MID_SCREEN = new Vector2D(Config.width/2, Config.height/2);
    private double x;
    private double y;

    public Vector2D(){
        this.x = Config.width * random.nextFloat();
        this.y = Config.height * random.nextFloat();
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Vector2D(double x, double y,double size) {
        this.x = x * size;
        this.y = y * size;
    }
    public Vector2D add(Vector2D in){
        return new Vector2D(this.x + in.getX(), this.y + in.getY());
    }
    public Vector2D add(double x,double y){
        return new Vector2D(this.x + x, this.y + y);
    }
    public Vector2D subtract(Vector2D in){
        return new Vector2D(this.x - in.getX(), this.y - in.getY());
    }
    public Vector2D subtract(double x, double y){
        return new Vector2D(this.x - x, this.y - y);
    }
    public Vector2D multiply(double mul){
        return new Vector2D(this.x * mul, this.y * mul);
    }
    public Vector2D reverse(){
        return new Vector2D(-this.x,-this.y);
    }
    public double getSize(){
        return Math.sqrt(x*x + y*y);
    }
    public Vector2D getNormalize(){
        double size = getSize();
        return new Vector2D(x/size,y/size);
    }

    public Vector2D getVectorWithSize(double in_size){
        double size = getSize();
        return new Vector2D(x/size * in_size,y/size * in_size);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    @Override
    public String toString(){
        return "x : " + this.x + ", y : "  + this.y;
    }
}
