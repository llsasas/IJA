package GameObjects;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class App extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    Maze maze;

    boolean play;
    GameController controller;
    Logger log;
    static Game game;
    ReplayController rController;
    @Override
    public void start(Stage primaryStage) {

        SettingsMenu settingsMenu = new SettingsMenu();

        primaryStage.setScene(new Scene(settingsMenu, WIDTH, HEIGHT));
        primaryStage.show();
        settingsMenu.requestFocus();
        settingsMenu.setOnApplySettings(() -> {
            maze = settingsMenu.getMaze();
            log = settingsMenu.getLogger();
            game = new Game(log, maze);
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
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        Rectangle background = new Rectangle(0, 0, HEIGHT, WIDTH);
        background.setFill(Color.BLUEVIOLET);
        root.getChildren().add(background);
        log.log("N:"+ maze.robots.length+"\t");
        for (AutonomousRobot entity : maze.robots) {
            root.getChildren().add(entity);
            double x = entity.getX();
            double y = entity.getY();
            int angle = entity.angle;
            int rangle = entity.rangle;
            double distance = entity.distance;
            log.log("Ai:"+ x +","+y+","+angle + ","+rangle+","+distance);
        }
        if(maze.crobot != null)
        {
            ControlledRobot crobot = maze.crobot;
            root.getChildren().add(maze.crobot);
            controller.addCrobot();
            scene.setOnKeyPressed(event -> controller.MoveControlledRobot(event));
            log.log("Ci:"+ crobot.x +","+crobot.y+","+crobot.angle + ","+crobot.rangle + ","+ crobot.distance);
        }
        log.log("\n");

        return scene;
    }

    private Scene createReplay()
    {
        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        rController= new ReplayController();
        HBox hbox =  rController.getControls();
        root.getChildren().add(hbox);
        Rectangle background = new Rectangle(0, 0, HEIGHT, WIDTH);
        background.setFill(Color.BLUEVIOLET);
        root.getChildren().add(background);
        for (AutonomousRobot entity : maze.robots) {
            root.getChildren().add(entity);
        }
        if(maze.crobot != null)
        {
            root.getChildren().add(maze.crobot);
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
