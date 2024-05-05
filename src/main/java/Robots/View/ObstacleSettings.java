package Robots.View;
/**
 * @file ObstacleSettings.java
 * @brief Class, used to store obstacle info
 * @author Samuel Čus, xcussa00
 */
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import Robots.GameObjects.Obstacle;
public class ObstacleSettings extends GridPane {

    private TextField xField;
    private TextField yField;


    public ObstacleSettings() {
        Label titleLabel = new Label("Obstacle");
        Label xLabel = new Label("X:");
        xField = new TextField();
        Label yLabel = new Label("Y:");
        yField = new TextField();

        add(titleLabel, 0, 0);
        add(xLabel, 0, 1);
        add(xField, 1, 1);
        add(yLabel, 0, 2);
        add(yField, 1, 2);
    }

    public Obstacle getEntity() {
        double x = Double.parseDouble(xField.getText());
        double y = Double.parseDouble(yField.getText());

        return new Obstacle(x,y);
    }
}