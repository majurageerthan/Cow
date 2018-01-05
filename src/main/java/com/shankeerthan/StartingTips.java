package com.shankeerthan;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StartingTips {

    public static void run() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Tips");
        alert.setHeaderText("Please fill the correct Temperature Settings");
        // alert.setContentText("I have a great message for you!");
        // Get the Stage.
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        // Add a custom icon.
        stage.getIcons().add(new Image("file:NewIcons/main64.png")); // To add an icon
        //   stage.setResizable(false);
        // alert.initStyle(StageStyle.UTILITY);

        Image img1 = new Image("file:Tips/Start.png");
        ImageView imageView = new ImageView(img1);
        imageView.setFitHeight(500);
        imageView.setFitWidth(500);
        GridPane.setVgrow(imageView, Priority.ALWAYS);
        GridPane.setHgrow(imageView, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(imageView, 0, 1);

// Set expandable Exception into the dialog pane.
        alert.getDialogPane().setContent(expContent);

        alert.showAndWait();

    }
}
