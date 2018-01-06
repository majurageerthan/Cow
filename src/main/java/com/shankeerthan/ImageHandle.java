package com.shankeerthan;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.MalformedURLException;


public class ImageHandle {
    public static Stage stage;
    public static File[] files;
    public static int[] counts;
    public static Image[] images;     //++++++++++++++++++++++++++++++++++++changed
    public static BorderPane root;
    public static Canvas imageDisplay;
    public static int colorPallete;
    protected static int[] imagesCheck;
    private static ImageView imageView;

    public static void imageHandle() {
         /*
        If Button is clicked it open DirectoryChooser
         */
//        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
        //++++++++++++++++++++++++++++++++++++++++++changed


        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open Image Folder");
        File dir = directoryChooser.showDialog(stage);


        try {
            File file;
            files = dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    String fileName = pathname.getName().toLowerCase();

                    return (fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith("jpeg")) && pathname.isFile();
                }
            });

            if (files.length == 0) {
                Label label = new Label("No Images are available in Selected Directory");
                root.setCenter(label);
            } else {
                //First set center Canvas
                root.setCenter(imageDisplay);
                //progressBar.setVisible(true);
                //progressBar.setProgress(0);

                counts = new int[files.length];
                images = new Image[files.length];
                imagesCheck = new int[files.length];

                file = new File(dir, "Detected" + Long.toString(System.currentTimeMillis()));


                file.mkdirs();
                //root.setCenter(new Label("Detecting Wounds"));


                //data  =new PicData[files.length];           //Handle .jpg .txt files,
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ProgressBar progressBar = new ProgressBar();
                        progressBar.setMinWidth(root.getWidth());
                        // progressBar.setProgress(0);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                root.setBottom(progressBar);
                            }
                        });


                        final Thread thread1 = new ImagingThread(files, 0, files.length / 4, SetTem.scaleTemMax, SetTem.scaleTemMin, SetTem.interestRangeMax, SetTem.interestRangeMin, colorPallete, file, imageDisplay);
                        final Thread thread2 = new ImagingThread(files, files.length / 4, files.length / 2, SetTem.scaleTemMax, SetTem.scaleTemMin, SetTem.interestRangeMax, SetTem.interestRangeMin, colorPallete, file, imageDisplay);
                        final Thread thread3 = new ImagingThread(files, files.length / 2, 3 * files.length / 4, SetTem.scaleTemMax, SetTem.scaleTemMin, SetTem.interestRangeMax, SetTem.interestRangeMin, colorPallete, file, imageDisplay);
                        final Thread thread4 = new ImagingThread(files, files.length * 3 / 4, files.length, SetTem.scaleTemMax, SetTem.scaleTemMin, SetTem.interestRangeMax, SetTem.interestRangeMin, colorPallete, file, imageDisplay);
                        thread1.start();
                        thread2.start();
                        thread3.start();
                        thread4.start();

                        //progressBar.setProgress(.5);
                        //Check wheather all threads finished their jobs and update progress Bar

                        int count = 0;
                        while (true) {
                            count = 0;

                            //final int  final_count =count;

                            //System.out.println(imagesCheck[0]);
                            for (int i = 0; i < imagesCheck.length; i++) {

                                count = count + imagesCheck[i];
                            }

                            if (count == imagesCheck.length) {
                                break;
                            }

                        }

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                root.setCenter(setImageView());
                                //
                            }
                        });

                        writeReport(dir);

                        progressBar.setVisible(false);
                    }
                }).start();


            }


        } catch (NullPointerException e) {
            //System.out.println("gdfgdfg");
        }


    }


    public static void saveDetailImg(Stage stageS, BorderPane rootS, Canvas imageDisplayS) {
        stage = stageS;
        root = rootS;
        imageDisplay = imageDisplayS;

    }


    private static HBox setImageView() {


        //Hbox
        HBox container = new HBox();

        imageView = new ImageView(images[0]);
        Zoom.saveImage(images[0]);
        VBox box1 = new VBox();
        Region region3 = new Region();
        Region region4 = new Region();
        VBox.setVgrow(region3, Priority.ALWAYS);
        VBox.setVgrow(region4, Priority.ALWAYS);
        box1.getChildren().addAll(region3, imageView, region4);
        Image leftBut = new Image("file:" + "NeWIcons/left.png");

        Button left = new Button();
        left.setGraphic(new ImageView(leftBut));

        left.getStyleClass().add("button");


        VBox box2 = new VBox();
        Region region5 = new Region();
        Region region6 = new Region();
        VBox.setVgrow(region5, Priority.ALWAYS);
        VBox.setVgrow(region6, Priority.ALWAYS);
        box2.getChildren().addAll(region5, left, region6);


        Button right = new Button();
        right.getStyleClass().add("button");

        Image rightBut = new Image("file:" + "NewIcons/right.png");
        right.setGraphic(new ImageView(rightBut));

        right.getStyleClass().add("button");
        VBox box3 = new VBox();
        Region region7 = new Region();
        Region region8 = new Region();
        VBox.setVgrow(region7, Priority.ALWAYS);
        VBox.setVgrow(region8, Priority.ALWAYS);
        box3.getChildren().addAll(region7, right, region8);


        Region region1 = new Region();
        Region region2 = new Region();

        HBox.setHgrow(region1, Priority.ALWAYS);
        HBox.setHgrow(region2, Priority.ALWAYS);
        left.setAlignment(Pos.CENTER_LEFT);
        right.setAlignment(Pos.CENTER_RIGHT);
        container.getChildren().addAll(box2, region1, box1, region2, box3);

        EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
            int count = 0;

            @Override
            public void handle(MouseEvent event) {

                if (event.getSource().equals(left)) {
                    if (count == 0) {
                        count = images.length - 1;
                    }
                    count--;
                    imageView.setImage(images[count]);
                    Zoom.saveImage(images[count]);

                    //System.out.println("left");
                } else if (event.getSource().equals(right)) {
                    if (count == images.length - 1) {
                        count = 0;
                    }
                    count++;
                    imageView.setImage(images[count]);
                    Zoom.saveImage(images[count]);

                    //System.out.println("right");
                }
            }

        };
        left.setOnMouseClicked(handler);
        right.setOnMouseClicked(handler);


        return container;
    }

    private static void writeReport(File dir) {
        try {
            System.out.println(dir.getCanonicalPath());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String path = dir.getCanonicalPath() + "\\Report" + Long.toString(System.currentTimeMillis()) + ".pdf";

            System.out.println(path);
            File file = new File(path);
            //File file =new File(path);
            //System.out.println(file.mkdir());
            // System.out.println(file.getAbsolutePath());

            ReportWriter re = new ReportWriter(path, 400, 700);
            re.writeTitle("Thermal Imaging Report");
            re.makeMetaDataTable(new double[]{SetTem.scaleTemMax, SetTem.scaleTemMin, SetTem.interestRangeMax, SetTem.interestRangeMin}, "Iron", "Celcious");
            re.makeDataTable(files, counts);
            re.closeDocument();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Button button = new Button("gh");
                    button.getStyleClass().add("button1");
                    button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            try {
                                Desktop.getDesktop().open(file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    root.setBottom(button);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
