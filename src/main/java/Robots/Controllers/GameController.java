/**
 * @file GameController.java
 * @brief Class representing game controller, game logic
 * @author Samuel Čus, xcussa 00
 */
package Robots.Controllers;

import Robots.GameObjects.AutonomousRobot;
import Robots.GameObjects.ControlledRobot;
import Robots.GameObjects.Maze;
import Robots.GameObjects.Obstacle;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;

public class GameController {
    Maze maze;
    ControlledRobot crobot;
    AnimationTimer timer;
    Logger logger;
    public GameController(Maze maze, Logger logger)
    {
        this.maze = maze;
        this.logger = logger;
    }

    public void addCrobot()
    {
        crobot = maze.crobot;
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
         timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateObjects();
            }
        };

        timer.start();
    }

    public void stopAnimation()
    {
        timer.stop();
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
        logPositions();
    }

    public void logPositions()
    {

        for(AutonomousRobot arobot : maze.robots)
        {
            double x = arobot.getX();
            double y = arobot.getY();
            int angle = arobot.angle;
            logger.log("A:"+ x +","+y+","+angle+"\n");
        }
        if(crobot != null)
        {
            logger.log("C:"+ crobot.x +","+crobot.y+"," + crobot.angle+"\n");
        }

        logger.log("\n");
    }

}
