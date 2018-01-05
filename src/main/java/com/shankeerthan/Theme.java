package com.shankeerthan;

import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Theme {

    public static void themeMenu(BorderPane pane) {
        List<String> choices = new ArrayList<>();
        choices.add("Dark");
        choices.add("Light");
        choices.add("Silver");
        choices.add("Peradeniya");


        ChoiceDialog<String> dialog = new ChoiceDialog<>("Dark Theme", choices);
        dialog.setTitle("Choice Dialog");
        dialog.setHeaderText("Theme Selection");
        dialog.setContentText("Choose your Theme:");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
//        if (result.isPresent()){
//            setTheme(pane,0);
//        }

// The Java 8 way to get the response value (with lambda expression).
        result.ifPresent((String letter) -> {
            if (letter.equals("Dark")) {
                pane.getStylesheets().removeAll("file:StyleSheet/silverTheme.css",
                        "file:StyleSheet/light.css",
                        "file:StyleSheet/peradenya.css");
                pane.getStylesheets().add("file:StyleSheet/dark.css");

            } else if (letter.equals("Silver")) {
                pane.getStylesheets().removeAll("file:StyleSheet/dark.css",
                        "file:StyleSheet/light.css",
                        "file:StyleSheet/peradenya.css");

                pane.getStylesheets().add("file:StyleSheet/silverTheme.css");

            } else if (letter.equals("Light")) {
                pane.getStylesheets().removeAll("file:StyleSheet/dark.css",
                        "file:StyleSheet/silverTheme.css",
                        "file:StyleSheet/peradenya.css");
                pane.getStylesheets().add("file:StyleSheet/light.css");

            } else if (letter.equals("Peradeniya")) {
                pane.getStylesheets().removeAll("file:StyleSheet/dark.css",
                        "file:StyleSheet/light.css",
                        "file:StyleSheet/silverTheme.css");
                pane.getStylesheets().add("file:StyleSheet/peradeniya.css");

            }
        });
    }

}
