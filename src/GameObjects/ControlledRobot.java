package GameObjects;

import javafx.scene.Node;
import javafx.scene.shape.Circle;
import java.lang.Math;

public class ControlledRobot extends Circle {

    private double x;
    private double y;
    public int angle;

    int rangle;

    double distance;
    public Maze maze;

    private boolean move =false;

    private OnUpdateListener onUpdateListener;

    public  ControlledRobot(double x, double y, int angle, int rangle, Maze maze, double distance) {
        super(x, y, 5);
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.rangle = rangle;
        this.maze = maze;
        this.distance = distance;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public void moveForward()
    {
        move = true;
    }

    public void moveStop()
    {
        move = false;
    }
    public void update() {
        if(move) {
            double newx = x + Math.cos(angle) * 5;
            double newy = y + Math.sin(angle) * 5;
            if (canMove(newx, newy)) {
                x = newx;
                y = newy;
            }
        }
        if (onUpdateListener != null) {
            onUpdateListener.onUpdate();
        }
    }

    private boolean canMove(double x, double y)
    {
        if((x-5 < 0 || x+5 > maze.HEIGHT) || (y-5 < 0 || y+5 > maze.WIDTH))
        {
            return false;
        }
        for (int i = 0; i < maze.robots.length; i++) {
            AutonomousRobot robot = maze.robots[i];
            double obx = robot.getX();
            double oby = robot.getY();
            double dist= Math.sqrt(Math.pow(x - obx, 2) + Math.pow(y - oby, 2));
            if (dist+5 < distance ) {
                return false;
            }

        }
        /*
        for (int i = 0; i < maze.obstacles.length; i++) {
            Obstacle obstacle = maze.obstacles[i];
            double obx = getX();
            double oby = getY();
            double dist= Math.sqrt(Math.pow(x - obx, 2) + Math.pow(y - oby, 2));
            if (dist+5 < distance ) {
                return false;
            }

        }
         */
        return true;
    }

    public void rotateC()
    {
        angle = (angle + rangle) % 360;
    }

    public void rotateAc()
    {
        angle = (angle - rangle + 360) % 360;
    }

    public void setOnUpdate(OnUpdateListener listener) {
        this.onUpdateListener = listener;
    }

    public interface OnUpdateListener {
        void onUpdate();
    }
}