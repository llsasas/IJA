/**
 * @file App.java
 * @brief Class, main app
 * @author Samuel Čus, xcussa00
 */
package Robots;

import Robots.GameObjects.Obstacle;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import Robots.GameObjects.Maze;
import Robots.GameObjects.ControlledRobot;
import Robots.GameObjects.AutonomousRobot;
import Robots.Controllers.ReplayController;
import Robots.Controllers.GameController;
import Robots.Controllers.Logger;
import Robots.View.SettingsMenu;

public class App extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private Button returnButton;
    Maze maze;
    GameController controller;
    Logger log;
    static Game game;
    ReplayController rController;
    @Override
    public void start(Stage primaryStage) {
        SettingsMenu settingsMenu = new SettingsMenu();
        settingsMenu.setAlignment(Pos.CENTER);
        Scene sets = new Scene(settingsMenu, 200, 200);
        primaryStage.setScene(sets);
        primaryStage.setFullScreen(true);
        primaryStage.show();
        settingsMenu.requestFocus();
        settingsMenu.setOnApplySettings(() -> {
            maze = settingsMenu.getMaze();
            log = settingsMenu.getLogger();
            game = new Game(log, maze);
            returnButton= new Button("Return");
            returnButton.setOnAction(event -> {
                if(!settingsMenu.replay)
                {
                    controller.stopAnimation();
                }
                settingsMenu.replay = false;
                primaryStage.close();
                primaryStage.setScene(sets);
                primaryStage.setFullScreen(true);
                primaryStage.show();
            });
            if(settingsMenu.replay())
            {
                log.setMaze(maze);
                log.parseLog();
                primaryStage.setScene(createReplay());
                primaryStage.show();
                rController.startAnimation();
            }
            else {
                controller = new GameController(maze, log);
                primaryStage.setScene(createScene());
                primaryStage.show();
                controller.startAnimation();
            }
        });
    }

    private Scene createScene() {

        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT+30);
        Rectangle background = new Rectangle(0, 0, WIDTH, HEIGHT);
        background.setFill(Color.LIGHTSTEELBLUE);
        root.getChildren().add(background);
        root.getChildren().add(returnButton);
        returnButton.setLayoutY(HEIGHT);
        returnButton.setLayoutX(WIDTH/2);
        log.log("Nr:"+ maze.robots.length+"\n");
        for (AutonomousRobot entity : maze.robots) {
            root.getChildren().add(entity);
            double x = entity.getX();
            double y = entity.getY();
            int angle = entity.angle;
            int rangle = entity.rangle;
            double distance = entity.distance;
            log.log("Ai:"+ x +","+y+","+angle + ","+rangle+","+distance+"\n");
        }
        log.log("No:"+ maze.obstacles.length+"\n");
        for(Obstacle obstacle : maze.obstacles)
        {
            root.getChildren().add(obstacle);
            log.log("Ob:"+ obstacle.CenterX()+","+ obstacle.CenterY() +"\n");
        }
        if(maze.crobot != null)
        {
            ControlledRobot crobot = maze.crobot;
            root.getChildren().add(maze.crobot);
            controller.addCrobot();
            scene.setOnKeyPressed(event -> controller.MoveControlledRobot(event));
            log.log("Ci:"+ crobot.x +","+crobot.y+","+crobot.angle + ","+crobot.rangle + ","+ crobot.distance+"\n");
        }
        log.log("\n");

        return scene;
    }

    private Scene createReplay() {
        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT + 50);
        rController = new ReplayController();
        HBox hbox = rController.getControls(returnButton);
        hbox.setLayoutY(HEIGHT);
        hbox.setLayoutX(300);
        Rectangle background = new Rectangle(0, 0, WIDTH, HEIGHT);
        background.setFill(Color.LIGHTSTEELBLUE);
        root.getChildren().add(background);
        root.getChildren().add(hbox);
        if (maze.robots != null) {
            for (AutonomousRobot entity : maze.robots) {
                root.getChildren().add(entity);
            }

            if (maze.obstacles != null) {
                for (Obstacle obstacle : maze.obstacles) {
                    root.getChildren().add(obstacle);
                }
            }
            if (maze.crobot != null) {
                root.getChildren().add(maze.crobot);
            }
        }
        return scene;
    }
    public static Game getGame()
    {
        return game;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
