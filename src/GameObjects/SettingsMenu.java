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
    private Logger log;

    private boolean replay = false;
    int count = 0;
    public SettingsMenu() {
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
        replay.setOnAction(event -> {
                    this.replay = true;
                    maze = MapHandler.loadMap("map.txt");
                    log = new Logger("log.txt", maze);
            onApplySettingsListener.onApplySettings();
                }
        );

    }

    private void showPlay()
    {
        log = new Logger("log.txt");
        Button loadmap = new Button("Load map");
        Button createmap = new Button("Create map");
        add(loadmap, 0, 0);
        add(createmap, 0, 1);
        createmap.setOnAction(event -> {
            getChildren().clear();
            showMapCreate();
        }
        );

        loadmap.setOnAction(event -> {
            maze = MapHandler.loadMap("map.txt");
            onApplySettingsListener.onApplySettings();
                }
        );
    }
    private void showMapCreate()
    {
        maze = new Maze(600,800);
        Label countLabel = new Label("Number of autonomous robots:");
        countField = new TextField("1");
        Button applyButton = new Button("Set");
        Label countLabel2 = new Label("Controlled robot:");
        countField2 = new TextField("1");
        Button savemap = new Button("Save map");
        Button confirmButton = new Button("Confirm");
        Button start = new Button("Start");
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
                getChildren().clear();
                add(savemap, 0, 0);
            }
        });
        savemap.setOnAction(event -> {
                    MapHandler.saveMap("map.txt", maze);
                    getChildren().clear();
                    add(start, 0, 0);
                }
        );

        start.setOnAction(event -> {
                    onApplySettingsListener.onApplySettings();
                }
        );

    }
    public void setOnApplySettings(OnApplySettingsListener listener) {
        this.onApplySettingsListener = listener;
    }

    public Maze getMaze()
    {
        return this.maze;
    }

    public boolean replay()
    {
        return this.replay;
    }
    public Logger getLogger()
    {
        return this.log;
    }
    public interface OnApplySettingsListener {
        void onApplySettings();
    }

}
