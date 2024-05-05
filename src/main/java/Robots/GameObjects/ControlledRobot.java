/**
 * @file ControlledRobot.java
 * @brief Class representing controled robot
 * @author Samuel Čus, xcussa00
 */
package Robots.GameObjects;

import javafx.scene.paint.Color;
public class ControlledRobot extends Robot {

    private boolean move = false;

    public ControlledRobot(double x, double y, int angle, int rangle, Maze maze, double distance) {
        super(x, y, angle, rangle, maze, distance);
        this.setFill(Color.RED);
    }


    public void update() {
        if(move) {
            double newx = x + Math.cos(Math.toRadians(angle)) * 1;
            double newy = y + Math.sin(Math.toRadians(angle)) * 1;
            if (canMove(newx, newy)) {
                x = newx;
                y = newy;
            }
        }
        if (onUpdateListener != null) {
            onUpdateListener.onUpdate();
        }
    }

    public boolean canMove(double x, double y) {
        if (((x - 15 - distance) < 0 || (x + 15+distance) > maze.WIDTH) || ((y - 15 - distance) < 0 || (y + 15 + distance) > maze.HEIGHT)) {
            return false;
        }
        for (int i = 0; i < maze.robots.length; i++) {
            AutonomousRobot robot = maze.robots[i];
            double obx = robot.getX();
            double oby = robot.getY();
            double dist = Math.sqrt(Math.pow(x - obx, 2) + Math.pow(y - oby, 2));
            if (dist - 30 < distance) {
                return false;
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
        return true;
    }
    public void moveForward() {
        move = true;
    }

    public void moveStop() {
        move = false;
    }

}
