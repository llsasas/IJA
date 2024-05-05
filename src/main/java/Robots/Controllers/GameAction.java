/**
 * @file GameAction.java
 * @brief Class representing one game action
 * @author Samuel Čus, xcussa 00
 */
package Robots.Controllers;

import Robots.GameObjects.Robot;


public class GameAction {
    private final Robot object;

    private final double x;
    private final double y;

    private final int angle;

    public GameAction(Robot object, double x, double y, int angle) {
        this.object = object;
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public double getX()
    {
        return  x;
    }

    public double getY() {
        return y;
    }

    public int getAngle()
    {
        return angle;
    }

    public Robot getObject()
    {
        return object;
    }
}
