package com.shankeerthan;

import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.util.ArrayList;

public class SupportCodes {
    private static ArrayList<String> imgProperties = new ArrayList<>();

    //Save the image properties to in a arrayList
    public static void saveProp(Metadata metadata) {
        String temp = null;
        for (Directory directory : metadata.getDirectories()) {

            //
            // Each Directory stores values in Tag objects
            //

            for (Tag tag : directory.getTags()) {
                temp = tag.toString();

                //Remove unwanted details in [] (square brackets)
                temp = temp.replaceAll("\\[.*\\]", "");
                imgProperties.add(temp);


            }


        }
    }

    //print arrayList in a new window
    public static void printProp() {

        if (imgProperties.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Look, Image could not be Opened");
            alert.setContentText("Please Try Again!");
            alert.showAndWait();
        } else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Image Properties");
            alert.setHeaderText(null);
            alert.setContentText(imgProperties.get(16) + "\n" + imgProperties.get(17) + "\n" + imgProperties.get(0) + "\n" + imgProperties.get(1) +
                    "\n" + "\n" + "Please Press below button for more details");


            Label label = new Label("Properties of Inserted Image :");


            TextArea textArea = new TextArea();
            textArea.setEditable(false);
            textArea.setWrapText(true);

            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);

            for (int i = 0; i < imgProperties.size(); i++) {
                textArea.appendText(imgProperties.get(i) + "\n");


            }


            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);

            // Set expandable Exception into the dialog pane.
            alert.getDialogPane().setExpandableContent(expContent);
            alert.showAndWait();

            // Set expandable Exception into the dialog pane.
            alert.getDialogPane().setExpandableContent(expContent);

        }


    }
}
