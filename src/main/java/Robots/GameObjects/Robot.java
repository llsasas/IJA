/**
 * @file Robot.java
 * @brief Abstract class, used by both robot types
 * @author Samuel Čus, xcussa00
 */
package Robots.GameObjects;

import javafx.scene.shape.Circle;

public abstract class Robot extends Circle {

    public double x;
    public double y;
    public int angle;
    public int rangle;
    public double distance;
    protected Maze maze;
    protected OnUpdateListener onUpdateListener;

    public Robot(double x, double y, int angle, int rangle, Maze maze, double distance) {
        super(x, y, 15);
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

    public void rotateAc() {
        angle = (angle - rangle + 360) % 360;
    }

    public void rotateC() {
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

    public interface OnUpdateListener {
        void onUpdate();
    }
}
