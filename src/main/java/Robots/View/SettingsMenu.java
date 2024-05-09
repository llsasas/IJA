/**
 * @file SettingMenu.java
 * @brief Class, main menu GUI
 * @author Samuel Čus, xcussa00
 */
package Robots.View;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import Robots.GameObjects.Maze;
import Robots.Controllers.Logger;
import Robots.GameObjects.AutonomousRobot;
import Robots.GameObjects.Obstacle;
import Robots.Controllers.MapHandler;


import java.io.IOException;

public class SettingsMenu extends GridPane {

    private TextField countField;

    private TextField countField2;

    private TextField countField3;

    private AutonomousSettings[] autonomous;

    private ObstacleSettings[] obstacleSettings;
    private ControlledSettings controlled;
    private OnApplySettingsListener onApplySettingsListener;
    private Maze maze;
    private Logger log;

    public boolean replay = false;
    int count = 0;
    public SettingsMenu() {
        showMainMenu();
    }

    private void showMainMenu()
    {

        getChildren().clear();
        Button play = new Button("Play");
        play.setPrefSize(100,50);
        Button replay = new Button("Replay");
        replay.setPrefSize(100,50);
        Button leave = new Button("Exit");
        leave.setPrefSize(100,50);
        add(play, 0, 0);
        add(replay, 0, 1);
        add(leave, 0, 2);
        play.setOnAction(event -> {
            getChildren().clear();
                    try {
                        showPlay();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        replay.setOnAction(event -> {
                    this.replay = true;
                    maze = new Maze(600,800);
                    log = new Logger("data/log.txt", maze);
            onApplySettingsListener.onApplySettings();
                }

        );
        leave.setOnAction(event -> {
                System.exit(0);
                }
        );

    }

    private void showPlay() throws IOException {
        Button loadmap = new Button("Load map");
        Button createmap = new Button("Create map");
        Button back = new Button("Back");
        loadmap.setPrefSize(100,50);
        createmap.setPrefSize(100,50);
        back.setPrefSize(100,50);
        add(loadmap, 0, 0);
        add(createmap, 0, 1);
        add(back,0,2);

        createmap.setOnAction(event -> {
                    getChildren().clear();

            try {
                if(log == null) {
                    log = new Logger("data/log.txt");
                }
                else
                {
                    log.dfile();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            showMapCreate();
                }
        );
        back.setOnAction(event -> {
            showMainMenu();
        }
        );

        loadmap.setOnAction(event -> {
            maze = MapHandler.loadMap("data/map.txt");
            try {
                if(log == null) {
                    log = new Logger("data/log.txt");
                }
                else
                {
                    log.dfile();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            showMainMenu();
            onApplySettingsListener.onApplySettings();
              }
        );
    }
    private void showMapCreate()
    {
        maze = new Maze(800,600);
        Label countLabel = new Label("Number of autonomous robots:");
        countField = new TextField("1");
        Button back = new Button("Back");
        Button applyButton = new Button("Set");
        Label countLabel2 = new Label("Controlled robot:");
        countField2 = new TextField("1");
        Label countLabel3 = new Label("Obstacles:");
        countField3 = new TextField("1");
        Button savemap = new Button("Save map");
        Button confirmButton = new Button("Confirm");
        Button start = new Button("Start");
        add(countLabel, 0, 0);
        add(countField, 1, 0);
        add(countLabel2, 0, 1);
        add(countField2, 1, 1);
        add(countLabel3, 0, 2);
        add(countField3, 1, 2);
        add(applyButton, 0, 3);
        add(back,1,3);
        back.setOnAction(event -> {
                    getChildren().clear();
                    log = null;
                    try {
                        showPlay();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        applyButton.setOnAction(event -> {
            int cnt = Integer.parseInt(countField.getText());
            int obst = Integer.parseInt(countField2.getText());
            int obstaclesnum = Integer.parseInt(countField3.getText());
            getChildren().clear();
            autonomous = new AutonomousSettings[cnt];
            obstacleSettings = new ObstacleSettings[obstaclesnum];
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
            for (int i = 0; i < obstaclesnum; i++)
            {
                obstacleSettings[i] = new ObstacleSettings();
                add(obstacleSettings[i],i, count*5+1);
            }
            add(confirmButton, 0, count*5+obstaclesnum*2);

        });

        confirmButton.setOnAction(event -> {
            if (onApplySettingsListener != null) {
                maze.robots = new AutonomousRobot[autonomous.length];
                maze.obstacles = new Obstacle[obstacleSettings.length];
                for (int i = 0; i < autonomous.length; i++) {
                    maze.robots[i] = autonomous[i].getEntity();
                }
                if(controlled != null)
                {
                    maze.crobot = controlled.getEntity();
                }
                for (int i = 0; i < obstacleSettings.length; i++) {
                    maze.obstacles[i] = obstacleSettings[i].getEntity();
                }
                getChildren().clear();
                add(savemap, 0, 0);
                add(start, 0, 1);
            }
        });
        savemap.setOnAction(event -> {
                    MapHandler.saveMap("data/map.txt", maze);
                }
        );

        start.setOnAction(event -> {
                    getChildren().clear();
                    showMainMenu();
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
