package Robots.GameObjects;

public class Maze {
    public final int HEIGHT;
    public final int WIDTH;

    public AutonomousRobot[] robots;
    public Obstacle[] obstacles;

    public ControlledRobot crobot;

    public Maze(int width,int height) {
        HEIGHT = height;
        WIDTH = width;
    }


}
