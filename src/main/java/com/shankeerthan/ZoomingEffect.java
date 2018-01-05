package com.shankeerthan;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class ZoomingEffect {


    public static void addZoomEffect(
            Button temperatureRangeButton,
            Button comapareButton,

            Button saveImageButton,
            Button openImageButton,
            Button openFolderbutton,

            Button propertiesButton, VBox vBox) {

        //   setImageView(temperatureCursorButton, "NewIcons/cursor.png", vBox);
        setImageView(temperatureRangeButton, "NewIcons/tem_range.png", vBox);
        setImageView(comapareButton, "NewIcons/compare.png", vBox);
        //  setImageView(printImageButton, "NewIcons/print.png", vBox);
        setImageView(saveImageButton, "NewIcons/sava.png", vBox);
        setImageView(openImageButton, "NewIcons/open_image.png", vBox);
        // setImageView(copyImageButton, "NewIcons/copy.png", vBox);
        setImageView(openFolderbutton, "NewIcons/open_image_folder1.png", vBox);
        setImageView(propertiesButton, "NewIcons/info.png", vBox);


        //      handleZoomEffect(propertiesButton);
//        handleZoomEffect(temperatureRangeButton);
//        handleZoomEffect(comapareButton);
//        handleZoomEffect(printImageButton);
//        handleZoomEffect(saveImageButton);
//        handleZoomEffect(openImageButton);
//        handleZoomEffect(openFolderbutton);
//        handleZoomEffect(copyImageButton);


    }

    private static void handleZoomEffect(Button button, VBox vBox) {


        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                imageView.setFitWidth(40);
//                imageView.setFitHeight(40);
                TranslateTransition transition = new TranslateTransition();
                transition.setDuration(Duration.millis(50));
                transition.setNode(vBox);
                transition.setToX(-15);
                transition.play();


                ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), button);
                scaleTransition.setToX(2.5);
                scaleTransition.setToY(2.5);
                scaleTransition.setAutoReverse(true);

                scaleTransition.play();
                //  System.out.println("entered");

            }
        });
        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                imageView.setFitWidth(32);
//                imageView.setFitHeight(32);
                TranslateTransition transition = new TranslateTransition();
                transition.setDuration(Duration.millis(50));
                transition.setNode(vBox);
                transition.setToX(0);
                transition.play();


                ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), button);
                scaleTransition.setToX(1f);
                scaleTransition.setToY(1f);

                scaleTransition.setAutoReverse(true);

                scaleTransition.play();
                // System.out.println("exit");

            }
        });

        button.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
                scaleTransition.setToX(1);
                scaleTransition.setToY(1);
                scaleTransition.setAutoReverse(true);
                scaleTransition.play();

//                ScaleTransition scaleTransition2 = new ScaleTransition(Duration.millis(50), button);
//                scaleTransition2.setToX(2.5);
//                scaleTransition2.setToY(2.5);
//                scaleTransition2.setAutoReverse(true);
//                scaleTransition2.play();

            }
        });


    }

    private static void setImageView(Button button, String url, VBox vBox) {
        Image img1 = new Image("file:" + url);
        ImageView imageView = new ImageView(img1);
        // Image image = new Image(url);
        //imageView = new ImageView(image);
        imageView.setFitHeight(32);
        imageView.setFitWidth(32);
        button.setGraphic(imageView);
        handleZoomEffect(button, vBox);


    }

    public static void setLabelEffect(Label label, VBox vBox) {
        label.setOnMouseEntered(event -> {
            TranslateTransition transition = new TranslateTransition();
            transition.setDuration(Duration.millis(50));
            transition.setNode(vBox);
            transition.setToX(-15);
            transition.play();

            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), label);
            scaleTransition.setToX(2);
            scaleTransition.setToY(2);
            scaleTransition.setAutoReverse(true);

            scaleTransition.play();
        });

        label.setOnMouseExited(event -> {
            TranslateTransition transition = new TranslateTransition();
            transition.setDuration(Duration.millis(50));
            transition.setNode(vBox);
            transition.setToX(0);
            transition.play();


            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), label);
            scaleTransition.setToX(1f);
            scaleTransition.setToY(1f);

            scaleTransition.setAutoReverse(true);

            scaleTransition.play();
        });

    }


}
