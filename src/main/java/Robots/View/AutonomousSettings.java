/**
 * @file AutonomousSettings.java
 * @brief Class, used to store autonomous robot info
 * @author Samuel Čus, xcussa00
 */
package Robots.View;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import Robots.GameObjects.Maze;
import Robots.GameObjects.AutonomousRobot;
public class AutonomousSettings extends GridPane {

    private TextField xField;
    private TextField yField;
    private TextField angleField;
    private TextField rangleField;
    private TextField distanceField;

    private Maze maze;

    public AutonomousSettings(Maze maze) {
        this.maze = maze;
        Label titleLabel = new Label("Autonomous robot");
        Label xLabel = new Label("X:");
        xField = new TextField();
        Label yLabel = new Label("Y:");
        yField = new TextField();
        Label angleLabel = new Label("Init. angle:");
        angleField = new TextField();
        Label rangleLabel = new Label("Rot. angle:");
        rangleField = new TextField();
        Label distanceLabel = new Label("Det. distance");
        distanceField = new TextField();

        add(titleLabel, 0, 0);
        add(xLabel, 0, 1);
        add(xField, 1, 1);
        add(yLabel, 0, 2);
        add(yField, 1, 2);
        add(angleLabel, 0, 3);
        add(angleField, 1, 3);
        add(rangleLabel, 0, 4);
        add(rangleField, 1, 4);
        add(distanceLabel, 0, 5);
        add(distanceField, 1, 5);
    }

    public AutonomousRobot getEntity() {
        double x = Double.parseDouble(xField.getText());
        double y = Double.parseDouble(yField.getText());
        int angle = Integer.parseInt(angleField.getText());
        int rangle = Integer.parseInt(rangleField.getText());
        double distance = Double.parseDouble(distanceField.getText());
        return new AutonomousRobot(x,y,angle, rangle, maze, distance);
    }
}