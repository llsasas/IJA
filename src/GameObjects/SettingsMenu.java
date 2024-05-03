package GameObjects;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class SettingsMenu extends GridPane {

    private TextField countField;

    private TextField countField2;

    private AutonomousSettings[] autonomous;
    private ControlledSettings controlled;
    private OnApplySettingsListener onApplySettingsListener;

    private Maze maze;
    int count = 0;
    public SettingsMenu(Maze maze) {
        this.maze = maze;
        showMainMenu();



    }

    private void showMainMenu()
    {
        Button play = new Button("Play");
        Button replay = new Button("Replay");
        add(play, 0, 0);
        add(replay, 0, 1);
        play.setOnAction(event -> {
            getChildren().clear();
            showPlay();
            }
        );

    }

    private void showPlay()
    {
        Button loadmap = new Button("Load map");
        Button createmap = new Button("Create map");
        add(loadmap, 0, 0);
        add(createmap, 0, 1);
        createmap.setOnAction(event -> {
            getChildren().clear();
            showMapCreate();
        }
        );
    }
    private void showMapCreate()
    {
        Label countLabel = new Label("Number of autonomous robots:");
        countField = new TextField("1");
        Button applyButton = new Button("Set");
        Label countLabel2 = new Label("Controlled robot:");
        countField2 = new TextField("1");
        Button savemap = new Button("Save map");
        Button confirmButton = new Button("Confirm");
        add(countLabel, 0, 0);
        add(countField, 1, 0);
        add(countLabel2, 0, 1);
        add(countField2, 1, 1);
        add(applyButton, 0, 2);
        applyButton.setOnAction(event -> {
            int cnt = Integer.parseInt(countField.getText());
            int obst = Integer.parseInt(countField2.getText());
            getChildren().clear();
            autonomous = new AutonomousSettings[cnt];
            for (int i = 0; i < cnt; i++) {
                count++;
                autonomous[i] = new AutonomousSettings(maze);
                add(autonomous[i], i, 0 );
            }
            if(obst == 1)
            {
                count++;
                controlled = new ControlledSettings(maze);
                add(controlled, 0, count*5);
            }
            add(confirmButton, 0, count*5+1);
        });
        confirmButton.setOnAction(event -> {
            if (onApplySettingsListener != null) {
                maze.robots = new AutonomousRobot[autonomous.length];
                for (int i = 0; i < autonomous.length; i++) {
                    maze.robots[i] = autonomous[i].getEntity();
                }
                if(controlled != null)
                {
                    maze.crobot = controlled.getEntity();
                }
                onApplySettingsListener.onApplySettings();
            }
        });
    }
    public void setOnApplySettings(OnApplySettingsListener listener) {
        this.onApplySettingsListener = listener;
    }

    public interface OnApplySettingsListener {
        void onApplySettings();
    }
}
