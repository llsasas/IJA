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

        ToggleButton buttonBackward = new ToggleButton(Constant.UI.BUTTON_BACKWARD);
        ToggleButton buttonForward = new ToggleButton(Constant.UI.BUTTON_FORWARD);



        // forward and reverse
        ToggleGroup toggleGroup = new ToggleGroup();
        buttonBackward.setToggleGroup(toggleGroup);
        buttonForward.setToggleGroup(toggleGroup);
        buttonBackward.setOnAction(event -> {
            if (buttonBackward.isSelected()) {
                buttonForward.setText(Constant.UI.BUTTON_FORWARD);
                buttonBackward.setText(Constant.UI.BUTTON_PAUSE);
                gameLogger.setBackward();
            } else {
                buttonBackward.setText(Constant.UI.BUTTON_BACKWARD);
                gameLogger.setPause();
            }
        });
        buttonForward.setOnAction(event -> {
            if (buttonForward.isSelected()) {
                buttonBackward.setText(Constant.UI.BUTTON_BACKWARD);
                buttonForward.setText(Constant.UI.BUTTON_PAUSE);
                gameLogger.setForward();
            } else {
                buttonForward.setText(Constant.UI.BUTTON_FORWARD);
                gameLogger.setPause();
            }
        });


        hBox.getChildren().addAll(buttonBackward,buttonForward);
        hBox.setPrefHeight(App.getGame().getMaze().HEIGHT);

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