package GameObjects;

import javafx.scene.shape.Rectangle;

public class Obstacle extends Rectangle {

    final double x;
    final double y;


    public Obstacle(double x, double y, double edgel) {
        super(x,y,edgel,edgel);
        this.x = x;
        this.y = y;
    }
}
