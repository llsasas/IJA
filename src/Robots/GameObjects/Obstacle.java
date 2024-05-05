package Robots.GameObjects;

import javafx.scene.shape.Rectangle;

public class Obstacle extends Rectangle {

    final double x;
    final double y;


    public Obstacle(double x, double y) {
        super(x-15,y-15,30,30);
        this.x = x;
        this.y = y;
    }

    public double CenterX()
    {
        return x;
    }

    public double CenterY()
    {
        return y;
    }
}
