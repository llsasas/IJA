package GameObjects;

import javafx.scene.Group;

public class Maze {
    final int HEIGHT;
    final int WIDTH;

    public AutonomousRobot[] robots;
    public Obstacle[] obstacles;

    public ControlledRobot crobot;

    public Maze(int height, int width) {
        HEIGHT = height;
        WIDTH = width;
    }


}
