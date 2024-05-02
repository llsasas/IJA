package GameObjects;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class EntitySettings extends GridPane {

    private TextField xField;
    private TextField yField;
    private TextField velocityXField;
    private TextField velocityYField;
    private TextField radiusField;

    public EntitySettings() {
        Label titleLabel = new Label("Controlled robots");
        Label xLabel = new Label("X:");
        xField = new TextField();
        Label yLabel = new Label("Y:");
        yField = new TextField();
        Label velocityXLabel = new Label("Velocity X:");
        velocityXField = new TextField();
        Label velocityYLabel = new Label("Velocity Y:");
        velocityYField = new TextField();
        Label radiusLabel = new Label("Radius:");
        radiusField = new TextField();

        add(titleLabel, 0, 0);
        add(xLabel, 0, 1);
        add(xField, 1, 1);
        add(yLabel, 0, 2);
        add(yField, 1, 2);
        add(velocityXLabel, 0, 3);
        add(velocityXField, 1, 3);
        add(velocityYLabel, 0, 4);
        add(velocityYField, 1, 4);
        add(radiusLabel, 0, 5);
        add(radiusField, 1, 5);
    }

    public Entity getEntity() {
        double x = Double.parseDouble(xField.getText());
        double y = Double.parseDouble(yField.getText());
        double velocityX = Double.parseDouble(velocityXField.getText());
        double velocityY = Double.parseDouble(velocityYField.getText());
        double radius = Double.parseDouble(radiusField.getText());
        return new Entity(x, y, radius, velocityX, velocityY);
    }
}