package GameObjects;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class SettingsMenu extends GridPane {

    private TextField countField;
    private Button applyButton;

    private TextField countField2;
    private Button applyButton2;
    private EntitySettings[] entitySettings;

    private OnApplySettingsListener onApplySettingsListener;
    int count = 0;
    public SettingsMenu() {
        Label countLabel = new Label("Number of controlled robots:");
        countField = new TextField("1");
        applyButton = new Button("Set");
        Label countLabel2 = new Label("Obstacles:");
        countField2 = new TextField("1");
        applyButton2 = new Button("Set");

        applyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int cnt = Integer.parseInt(countField.getText());
                int obst = Integer.parseInt(countField2.getText());
                getChildren().clear();
                entitySettings = new EntitySettings[cnt+obst];
                for (int i = 0; i < cnt; i++) {
                    count++;
                    entitySettings[i] = new EntitySettings();
                    add(entitySettings[i], 0, i );
                }
                for (int i = cnt; i < cnt+obst; i++) {
                    count++;
                    entitySettings[i] = new EntitySettings();
                    add(entitySettings[i], 0, cnt*5+i);
                }
            }
        });


        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (onApplySettingsListener != null) {
                    Entity[] entities = new Entity[entitySettings.length];
                    for (int i = 0; i < entitySettings.length; i++) {
                        entities[i] = entitySettings[i].getEntity();
                    }
                    onApplySettingsListener.onApplySettings(entities);
                }
            }
        });

        add(countLabel, 0, 0);
        add(countField, 1, 0);
        add(countLabel2, 0, 1);
        add(countField2, 1, 1);
        add(applyButton, 0, 2);
        add(confirmButton, 0, 11);
    }


    public void setOnApplySettings(OnApplySettingsListener listener) {
        this.onApplySettingsListener = listener;
    }

    public interface OnApplySettingsListener {
        void onApplySettings(Entity[] entities);
    }
}
