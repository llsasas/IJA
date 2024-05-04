package GameObjects;

import javafx.scene.shape.Circle;

public class ControlledRobot extends Robot {

    private boolean move = false;

    public ControlledRobot(double x, double y, int angle, int rangle, Maze maze, double distance) {
        super(x, y, angle, rangle, maze, distance);
    }


    public void update() {
        if(move) {
            double newx = x + Math.cos(angle) * 1;
            double newy = y + Math.sin(angle) * 1;
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
        if ((x - 5 < 0 || x + 5 > maze.HEIGHT) || (y - 5 < 0 || y + 5 > maze.WIDTH)) {
            return false;
        }
        for (int i = 0; i < maze.robots.length; i++) {
            AutonomousRobot robot = maze.robots[i];
            double obx = robot.getX();
            double oby = robot.getY();
            double dist = Math.sqrt(Math.pow(x - obx, 2) + Math.pow(y - oby, 2));
            if (dist + 5 < distance) {
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
