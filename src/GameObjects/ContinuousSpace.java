package GameObjects;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ContinuousSpace extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) {
        SettingsMenu settingsMenu = new SettingsMenu();

        primaryStage.setScene(new Scene(settingsMenu, 720, 480));
        primaryStage.show();
        settingsMenu.requestFocus();
        settingsMenu.setOnApplySettings((entities) -> {
            primaryStage.setScene(createScene(entities));
            primaryStage.show();
            startAnimation(entities);
        });
    }

    private Scene createScene(Entity[] entities) {
        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        for (Entity entity : entities) {
            root.getChildren().add(entity.getCircle());
        }

        return scene;
    }

    private void startAnimation(Entity[] entities) {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (Entity entity : entities) {
                    entity.update();
                    entity.getCircle().setCenterX(entity.getX());
                    entity.getCircle().setCenterY(entity.getY());
                }
            }
        };
        timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
