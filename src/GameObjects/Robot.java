package GameObjects;

import javafx.scene.shape.Circle;
import java.lang.Math;

public abstract class Robot extends Circle {

    protected double x;
    protected double y;
    protected int angle;
    protected int rangle;
    protected double distance;
    protected Maze maze;
    protected OnUpdateListener onUpdateListener;

    public Robot(double x, double y, int angle, int rangle, Maze maze, double distance) {
        super(x, y, 10);
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.rangle = rangle;
        this.maze = maze;
        this.distance = distance;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public abstract void update();

    public abstract boolean canMove(double x, double y);

    protected void rotateC() {
        angle = (angle - rangle + 360) % 360;
    }

    protected void rotateAc() {
        angle = (angle + rangle) % 360;
    }

    public void setAngle(int angle)
    {
        this.angle = angle;
    }

    public void updateLog(double x, double y)
    {
        this.x = x;
        this.y = y;
        if (onUpdateListener != null) {
            onUpdateListener.onUpdate();
        }
    }
    public void setOnUpdate(OnUpdateListener listener) {
        this.onUpdateListener = listener;
    }

    public interface OnUpdateListener {
        void onUpdate();
    }
}
