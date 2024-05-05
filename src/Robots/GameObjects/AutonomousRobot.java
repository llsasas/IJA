package Robots.GameObjects;

import javafx.scene.paint.Color;

import java.lang.Math;

public class AutonomousRobot extends Robot {

    public AutonomousRobot(double x, double y, int angle, int rangle, Maze maze, double distance) {
        super(x, y, angle, rangle, maze, distance);
        this.setFill(Color.YELLOW);
    }

    public boolean canMove(double x, double y)
    {
        if(((x - 15 - distance) < 0 || (x + 15+distance) > maze.HEIGHT) || ((y - 15 - distance) < 0 || (y + 15 + distance) > maze.WIDTH))
        {
            return false;
        }
        for (int i = 0; i < maze.robots.length; i++) {
            AutonomousRobot robot = maze.robots[i];
            if(this!= robot) {
                double obx = robot.getX();
                double oby = robot.getY();
                double dist = Math.sqrt(Math.pow(x - obx, 2) + Math.pow(y - oby, 2));
                if (dist - 30 < distance) {
                    return false;
                }
            }

        }
        for (int i = 0; i < maze.obstacles.length; i++) {
            Obstacle obstacle = maze.obstacles[i];
            double obx = obstacle.CenterX();
            double oby = obstacle.CenterY();
            double dist= Math.sqrt(Math.pow(x - obx, 2) + Math.pow(y - oby, 2));
            if (dist-(15+21.22) < distance ) {
                return false;
            }
        }

        if(maze.crobot != null) {
            ControlledRobot crobot = maze.crobot;
            double obx = crobot.getX();
            double oby = crobot.getY();
            double dist = Math.sqrt(Math.pow(x - obx, 2) + Math.pow(y - oby, 2));
            if (dist - 30 < distance) {
                return false;
            }
        }
        return true;
    }

    public void update() {
        double newx = x + Math.cos(Math.toRadians(angle)) * 1;
        double newy = y + Math.sin(Math.toRadians(angle)) * 1;
        if(canMove(newx,newy))
        {
            x = newx;
            y = newy;
        }
        else {
            rotateAc();
        }
        if (onUpdateListener != null) {
            onUpdateListener.onUpdate();
        }
    }


}
