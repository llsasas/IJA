/**
 * @file Game.java
 * @brief Class, used to store game info
 * @author Samuel Čus, xcussa00
 */
package Robots;

import Robots.Controllers.Logger;
import Robots.GameObjects.Maze;

public class Game {

    private final Logger logger;
    private final Maze maze;

    Game(Logger logger, Maze maze)
    {
        this.logger = logger;
        this.maze = maze;
    }

    public Maze getMaze()
    {
        return maze;
    }

    public Logger getLogger()
    {
        return logger;
    }

}
