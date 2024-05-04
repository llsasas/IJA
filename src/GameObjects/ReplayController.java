package GameObjects;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class ReplayController {

    private Logger gameLogger = App.getGame().getLogger();

    /**
     * Returns the controls for replaying the game
     * @return box with controls
     */
    public HBox getControls() {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10.0));
        hBox.setSpacing(20.0);
        hBox.setAlignment(Pos.CENTER);

        hBox.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        Button buttonBackward = new Button(Constant.UI.BUTTON_BACKWARD);
        Button buttonForward = new Button(Constant.UI.BUTTON_FORWARD);
        Button pause = new Button(Constant.UI.BUTTON_PAUSE);

        buttonBackward.setOnAction(event -> {
                gameLogger.setBackward();

        });
        buttonForward.setOnAction(event -> {
                gameLogger.setForward();
        });

        pause.setOnAction(event -> {
            gameLogger.setPause();
        });


        hBox.getChildren().addAll(buttonBackward,pause,buttonForward);
        //hBox.setPrefHeight((double) App.getGame().getMaze().HEIGHT /10);
        hBox.setLayoutY(App.getGame().getMaze().HEIGHT);
        return hBox;
    }

    public void startAnimation() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameLogger.updateObjects();
            }
        };


        timer.start();
    }


}