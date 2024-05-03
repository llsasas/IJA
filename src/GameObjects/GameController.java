package GameObjects;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyEvent;

public class GameController {
    Maze maze;
    ControlledRobot crobot;
    GameController(Maze maze)
    {
        this.maze = maze;
    }

    public void addCrobot()
    {
        crobot = maze.crobot;
    }
    public void handleKeyPress(KeyEvent event) {
        MoveControlledRobot(event);
    }
    public void MoveControlledRobot(KeyEvent e)
    {
        switch (e.getCode())
        {
            case Q:
                crobot.rotateAc();
                break;
            case W:
                crobot.moveForward();
                break;
            case E:
                crobot.rotateC();
                break;
            case S:
                crobot.moveStop();
                break;
        }
    }
    public void startAnimation() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateObjects();
            }
        };

        timer.start();
    }

    public void updateObjects()
    {
        for (AutonomousRobot entity : maze.robots) {
            entity.update();
            entity.setCenterX(entity.getX());
            entity.setCenterY(entity.getY());
        }
        if(maze.crobot != null)
        {
            maze.crobot.update();
            maze.crobot.setCenterX(maze.crobot.getX());
            maze.crobot.setCenterY(maze.crobot.getY());
        }
    }
}
