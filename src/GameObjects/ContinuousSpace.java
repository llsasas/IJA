package GameObjects;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ContinuousSpace extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    Maze maze;
    GameController controller;
    @Override
    public void start(Stage primaryStage) {
        maze = new Maze(600, 800);
        controller = new GameController(maze);
        SettingsMenu settingsMenu = new SettingsMenu(maze);

        primaryStage.setScene(new Scene(settingsMenu, WIDTH, HEIGHT));
        primaryStage.show();
        settingsMenu.requestFocus();
        settingsMenu.setOnApplySettings(() -> {
            primaryStage.setScene(createScene());
            primaryStage.show();
            controller.startAnimation();
        });
    }

    private Scene createScene() {
        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        Rectangle background = new Rectangle(0, 0, HEIGHT, WIDTH);
        background.setFill(Color.BLUEVIOLET);
        root.getChildren().add(background);
        for (AutonomousRobot entity : maze.robots) {
            root.getChildren().add(entity);
        }
        if(maze.crobot != null)
        {
            root.getChildren().add(maze.crobot);
            controller.addCrobot();
            scene.setOnKeyPressed(event -> controller.MoveControlledRobot(event));
        }

        return scene;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
