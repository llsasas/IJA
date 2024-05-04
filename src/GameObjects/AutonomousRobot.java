package GameObjects;

import javafx.scene.shape.Circle;
import java.lang.Math;

public class AutonomousRobot extends Robot {

    public AutonomousRobot(double x, double y, int angle, int rangle, Maze maze, double distance) {
        super(x, y, angle, rangle, maze, distance);
    }

    public boolean canMove(double x, double y)
    {
        if((x-5 < 0 || x+5 > maze.HEIGHT) || (y-5 < 0 || y+5 > maze.WIDTH))
        {
            return false;
        }
        for (int i = 0; i < maze.robots.length; i++) {
            AutonomousRobot robot = maze.robots[i];
            if(this!= robot) {
                double obx = robot.getX();
                double oby = robot.getY();
                double dist = Math.sqrt(Math.pow(x - obx, 2) + Math.pow(y - oby, 2));
                if (dist - 10 < distance) {
                    return false;
                }
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
        if(maze.crobot != null) {
            ControlledRobot crobot = maze.crobot;
            double obx = crobot.getX();
            double oby = crobot.getY();
            double dist = Math.sqrt(Math.pow(x - obx, 2) + Math.pow(y - oby, 2));
            if (dist + 5 < distance) {
                return false;
            }
        }
        return true;
    }

    public void update() {
        double newx = x + Math.cos(angle) * 1;
        double newy = y + Math.sin(angle) * 1;
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
