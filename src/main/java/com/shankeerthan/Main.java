package com.shankeerthan;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.MalformedURLException;


import static javafx.scene.text.TextAlignment.*;


public class Main extends Application {
    public static int[] counts;
    public static Image[] images;     //++++++++++++++++++++++++++++++++++++changed
    protected static int[] imagesCheck;  //++++++++++++++++++++++++++++++++++++++changed
    private Canvas imageDisplay;
    private Stage stage;
    //    private double scaleTemMax = SetTem.scaleTemMax;
//    private double scaleTemMin= SetTem.scaleTemMin;
//    private double interestRangeMax = SetTem.interestRangeMax;
//    private double interestRangeMin= SetTem.interestRangeMin;
    private int unit = SetTem.unit;
    private int colorPallete;
    private BorderPane root;
    private ProgressBar progressBar;//++++++++++++++++++++++++++++changed
    private ImageView imageView;  //++++++++++++++++++++++++++++++++changed
    private File[] files;
    private Image singleImage;
    private File singleImageFile;
    private String singleImageName;


    public Main() {
//        scaleTemMax = 35;
//        scaleTemMin = 25;
//        interestRangeMax = 32;
//        interestRangeMin = 28;
//        unit = Values.CELSIUS;
        colorPallete = Values.IRON;
        root = new BorderPane();
        imageDisplay = new Canvas(); //HAVE TO handle initial size of canvas later point
        imageDisplay.getStyleClass().add("imagecanvas");
    }


    public static void callImag() {
        Main main = new Main();
        main.imageHandle();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Get reference of primaryStage to use in code further
        stage = primaryStage;
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.getIcons().add(new Image("file:NewIcons/main64.png"));
        primaryStage.setTitle("Smart Finder");


        //Border Pane is root for this software
        //BorderPane root = new BorderPane();
        root.getStyleClass().add("scene");
        //   Theme.themeMenu(root);
        root.getStylesheets().add("file:StyleSheet/dark.css");
        //root.getStylesheets().add("file:StyleSheet/whiteTheme.css");

        addNodesToRoot(root);
        Scene imageScene = new Scene(root, Values.DEFAULT_SCENE_WIDTH, Values.DEFAULT_SCENE_HEIGHT);

        //HAVE TO set tile with image name and app name set logo of the software
        styleStage(primaryStage);
        primaryStage.setScene(imageScene);

        RightClickMenu.RightClick(imageScene, primaryStage);
        primaryStage.show();
        StartingTips.run();

    }

    private void styleStage(Stage stage) {
        stage.initStyle(StageStyle.DECORATED);
    }

    private void designTopBar(HBox container, VBox vBox, BorderPane pane) {
        //Region
        Region spacing = new Region();
        HBox.setHgrow(spacing, Priority.ALWAYS);

        //Menu button
        Button smallMenuButton = new Button();
        handleSmallMenuButton(smallMenuButton, container, pane);
        Image menuIcon = new Image("file:" + "Icons/menu.png");
        //   ImageView menuIconView = new ImageView(menuIcon);
        smallMenuButton.setGraphic(new ImageView(menuIcon));

        //Canvas to show name of the image
        Canvas nameCanvas = new Canvas();
        nameCanvas.setWidth(Values.DEFAULT_NAME_CANVAS_WIDTH);
        nameCanvas.setHeight(Values.DEFAULT_TOP_BAR_NODES_HEIGHT);


        //Zoom Selecton Button
        Button zoomSelectionButton = new Button();
        handleZoomSelectionButton(zoomSelectionButton, container);
        Image zoomSelectionIcon = new Image("file:" + "Icons/menu.png");
        zoomSelectionButton.setGraphic(new ImageView(zoomSelectionIcon));

        //Color pallete Drop down
        ComboBox colorPallete = new ComboBox();

        handleComboBox(colorPallete);
        colorPallete.getStyleClass().add("combobox");
        colorPallete.getItems().addAll("Iron", "Lava", "Arctic", "Gray", "Rainbow", "Rainbow HC");
        colorPallete.getSelectionModel().selectFirst();


        //zoom  button
        Button zoomButton = new Button();
        Image zoomIcon = new Image("file:" + "NewIcons/zoom.png");
        zoomButton.setGraphic(new ImageView(zoomIcon));
        handleZoomButton(zoomButton);


        //Rotate left button
//        Button rotateLeftButton = new Button();
//        Image rotateLeftIcon = new Image("file:" + "Icons/rotate_left.png");
//        rotateLeftButton.setGraphic(new ImageView(rotateLeftIcon));

        //Rotate right button
//        Button rotateRightButton = new Button();
//        Image rotateRightIcon = new Image("file:" + "Icons/rotate_right.png");
//        rotateRightButton.setGraphic(new ImageView(rotateRightIcon));

        //Crop button
//        Button cropButton = new Button();
//        Image cropImageIcon = new Image("file:" + "Icons/crop.png");
//        cropButton.setGraphic(new ImageView(cropImageIcon));

        //ShowHide side bar  Button
        Button showSidebarButton = new Button();
        handleShowBarButton(showSidebarButton, vBox);
        Image showSidebarIcon = new Image("file:" + "Icons/show_sidebar.png");
        showSidebarButton.setGraphic(new ImageView(showSidebarIcon));


        //Hide side bar button
        Button hideSidebarButton = new Button();
        handleHideBarButton(hideSidebarButton, vBox);
        Image hideSidebarImageIcon = new Image("file:" + "Icons/hide_sidebar.png");
        hideSidebarButton.setGraphic(new ImageView(hideSidebarImageIcon));

        //About us button
        Button aboutUsButton = new Button();
        Image aboutUsIcon = new Image("file:" + "Icons/about_us.png");
        aboutUsButton.setGraphic(new ImageView(aboutUsIcon));

        container.setSpacing(Values.TOP_BOX_SPACING);
        container.getChildren().add(smallMenuButton);
        container.getChildren().add(nameCanvas);
        container.getChildren().add(spacing);

        container.getChildren().add(zoomSelectionButton);
        container.getChildren().add(colorPallete);
        container.getChildren().add(zoomButton);
//        container.getChildren().add(rotateRightButton);
//        container.getChildren().add(rotateLeftButton);
//        container.getChildren().add(cropButton);
        container.getChildren().add(showSidebarButton);
        container.getChildren().add(hideSidebarButton);
        container.getChildren().add(aboutUsButton);

    }


    private void designBottomBox(HBox container) {
        //It has a slider to show position of current image in particular directory
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);

        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);


        progressBar = new ProgressBar();
        progressBar.getStyleClass().add("progressbar");
        HBox.setHgrow(progressBar, Priority.ALWAYS);
        progressBar.setVisible(false);
        container.setPadding(new Insets(Values.POSITION_SLIDER_OFFSET));
        container.getChildren().addAll(region1, progressBar, region2);


    }

    private void designRightBox(VBox container) {

        //Temperature cursor
//        Button temperatureCursorButton = new Button();
//        temperatureCursorButton.setTooltip(new Tooltip("Temperature Cursor"));
//        Image temperatureCursorIcon = new Image("file:" + "Icons/cursor.png");
//        temperatureCursorButton.setGraphic(new ImageView(temperatureCursorIcon));

        Label temperatureUnitLabel = new Label("C");//------------------------Changed
        temperatureUnitLabel.setFont(new Font("Arial Black", 28));//+++++++++++++++++++++++++++

        ZoomingEffect.setLabelEffect(temperatureUnitLabel, container);
        temperatureUnitLabel.getStyleClass().add("label1");
        temperatureUnitLabel.setTooltip(new Tooltip("Unit of Temperature"));

        //Label to show current low point of range
        Label lowPointLabel = new Label(Values.LOW_TEM_REGION);  //++++++++++++++++++++++++++++++++++++changed

        lowPointLabel.getStyleClass().add("label1");
        lowPointLabel.setFont(new Font("Arial Black", 28));//+++++++++++++++++++++++++++
        lowPointLabel.setTooltip(new Tooltip("Low Point of Temperature Range"));
        ZoomingEffect.setLabelEffect(lowPointLabel, container);
        //label to show current temperature high point of range
        Label highPointLabel = new Label(Values.HIGH_TEM_REGION);
        highPointLabel.setFont(new Font("Arial Black", 28));//+++++++++++++++++++++++++++//++++++++++++++++++++++changed
        highPointLabel.getStyleClass().add("label1");       //++++++++++++++++++++++++++++++changed
        highPointLabel.setTooltip(new Tooltip("High Point of Temperature Range"));
        ZoomingEffect.setLabelEffect(highPointLabel, container);


        //Temperature Range Setting
        Button temperatureRangeButton = new Button();
        handleTemperatureRange(temperatureRangeButton, highPointLabel, lowPointLabel);
        temperatureRangeButton.setTooltip(new Tooltip("Set Low and High Temperature"));
//        Image temperatureRangeIcon = new Image("file:" + "Icons/tem_range.png");
//       temperatureRangeButton.setGraphic(new ImageView(temperatureRangeIcon));
        SetTem.takevar(stage, highPointLabel, lowPointLabel);


        //Compare with visual image
        Button comapareButton = new Button();
        comapareButton.setTooltip(new Tooltip("Compare with visual Image"));
//        Image compareIcon = new Image("file:" + "Icons/compare.png");
//        comapareButton.setGraphic(new ImageView(compareIcon));

        //Print Image
//        Button printImageButton = new Button();
//        printImageButton.setTooltip(new Tooltip("Print Image"));
        // Image printImageIcon = new Image("file:" + "Icons/print.png");
        //printImageButton.setGraphic(new ImageView(printImageIcon));

        //Save Image
        Button saveImageButton = new Button();
        saveImageButton.setTooltip(new Tooltip("Save Thermal Image"));
        saveImageButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                saveImage(singleImageFile, singleImageName);
            }
        });
//        Image saveImageIcon = new Image("file:" + "Icons/sava.png");
//        saveImageButton.setGraphic(new ImageView(saveImageIcon));

        //Open Image
        Button openImageButton = new Button();
        openImageButton.setTooltip(new Tooltip("Open Image"));
        openImageButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                openImage();
            }
        });
//        Image openImageIcon = new Image("file:" + "Icons/open_image.png");
//        openImageButton.setGraphic(new ImageView(openImageIcon));

        //Open Folder

        Button openFolderbutton = new Button();
        handleImageFolder(openFolderbutton);
        openFolderbutton.setTooltip(new Tooltip("Open Folder of Images"));
//        Image openFolderIcon = new Image("file:" + "Icons/open_image_folder.png");
//        openFolderbutton.setGraphic(new ImageView(openFolderIcon));

        //Copy Image
        //       Button copyImageButton = new Button();
//        handleCopyImage(copyImageButton);
        //       copyImageButton.setTooltip(new Tooltip("Copy Image"));
//        Image copyImageIcon = new Image("file:" + "Icons/copy.png");
//        copyImageButton.setGraphic(new ImageView(copyImageIcon));

        //Properties
        Button propertiesButton = new Button();
        // propertiesButton.setMaxSize(32,32);
        handlePropertiesButton(propertiesButton);
        propertiesButton.setTooltip(new Tooltip("Image Properties"));
//        Image propertiesIcon = new Image("file:" + "NewIcons/info.png");
//        propertiesButton.setGraphic(new ImageView(propertiesIcon));

        // add Zoom effect for the VBox
        ZoomingEffect.addZoomEffect(temperatureRangeButton, comapareButton, saveImageButton,
                openImageButton, openFolderbutton, propertiesButton, container);

        container.setSpacing(Values.SIDE_BOX_SPACING);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(Values.SIDE_BOX_PADDING_TOP_BOTTOM, 0, 0, 0));
        // container.getChildren().add(temperatureCursorButton);
        container.getChildren().add(temperatureRangeButton);
        container.getChildren().add(temperatureUnitLabel);
        container.getChildren().add(lowPointLabel);
        container.getChildren().add(highPointLabel);
        container.getChildren().add(comapareButton);
        // container.getChildren().add(printImageButton);
        container.getChildren().add(saveImageButton);
        container.getChildren().add(openImageButton);
        container.getChildren().add(openFolderbutton);
        //  container.getChildren().add(copyImageButton);
        container.getChildren().add(propertiesButton);


    }

    private void addNodesToRoot(BorderPane root) {
        /*
        Center: Canvas contains imaged Thremal image and two left and right arrow to jump
                previous and next image
        Bottom :Simple HBOX to show images in folder
        Left  :Nothing
        Right : Like Tool bar contains all functionalities
         */

        //CEnter
        root.setCenter(imageDisplay);

        //Bottom
        HBox imagePositionBox = new HBox();
        imagePositionBox.setMinHeight(Values.IMAGE_POSITION_BOX_HEIGHT);
        designBottomBox(imagePositionBox);
        root.setBottom(imagePositionBox);
        //Right
        VBox rightBox = new VBox();
        rightBox.setMinWidth(Values.RIGHT_BAR_WIDTH);
        designRightBox(rightBox);
        root.setRight(rightBox);

        //Top
        HBox topBox = new HBox();
        topBox.setMinHeight(Values.TOP_BAR_HEIGHT);
        designTopBar(topBox, rightBox, root);
        RightClickMenu.paneSave(root);
        root.setTop(topBox);


    }

    private void handleZoomSelectionButton(Button button, Node root) {
        //If this button is clicked shows vertical slider to user
        //set zoom
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ContextMenu sliderDisplay = new ContextMenu();
                Slider zoomSelection = new Slider();
                zoomSelection.setOrientation(Orientation.VERTICAL);
                CustomMenuItem slider = new CustomMenuItem(zoomSelection);
                sliderDisplay.getItems().add(slider);
                sliderDisplay.show(root, Side.TOP, Values.SLIDER_DX, Values.SLIDER_DY);
            }
        });
    }

    private void handleZoomButton(Button button) {
        button.setOnMouseClicked(event -> Zoom.startZoom());
    }


    public void handleTemperatureRange(Button button, Label high, Label low) {
        /*
        This method handle when button is clicked it get low point,high point of temperature scale range
        and Range of Interest  And  give of choice to user ues what ever the unit from user
        It has default values
        Temperature Scale Range : 25 - 35 degree celcious
        Interest range          :28   -32 degree celcious
         */

        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                SetTem.openTempWin();

            }
        });

//        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                final Stage dialog = new Stage();
//                dialog.setTitle("Adjust Temperature Scale Setting");
//                dialog.initModality(Modality.APPLICATION_MODAL);
//                dialog.initOwner(stage);
//
//
//                GridPane grid = new GridPane();
//
//                Label selectUnit = new Label("Temperature Unit");
//
//                ComboBox<String> unitSelection = new ComboBox<String>();
//                unitSelection.getItems().addAll("Celcious", "Frahneit", "Kelvin");
//                unitSelection.getSelectionModel().selectFirst();
//
//
//                Label temperatureScale = new Label("Adjust Temperature Scale Range");
//
//                Label highPoint = new Label("High Temperature of Range");
//                Label lowPoint = new Label("Low Temperature of Range");
//
//                TextField highPointText = new TextField(Double.toString(scaleTemMax));
//                TextField lowPointText = new TextField(Double.toString(scaleTemMin));
//
//                Label regionOfInterest = new Label("Region of Interset Temperature Scale");
//
//                Label highPointOfRegion = new Label("High Temperature");
//                Label lowPointOfRegion = new Label("Low Temperature ");
//
//                TextField highPointRegionText = new TextField(Double.toString(interestRangeMax));
//                TextField lowPonitRegionText = new TextField(Double.toString(interestRangeMin));
//
//                Button setRanges = new Button("Apply");
//
//                setRanges.setOnMouseClicked(new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent event) {
//                        try {
//                            scaleTemMax = Double.parseDouble(highPointText.getText());
//                            scaleTemMin = Double.parseDouble(lowPointText.getText());
//                            interestRangeMax = Double.parseDouble(highPointRegionText.getText());
//                            interestRangeMin = Double.parseDouble(lowPonitRegionText.getText());
//                            unit = unitSelection.getSelectionModel().getSelectedIndex();
//                            high.setText(highPointRegionText.getText());
//                            low.setText(lowPonitRegionText.getText());
//                            dialog.close();
//                        } catch (NumberFormatException e) {
//                            System.out.println(e);
//                        }
//                    }
//                });
//                Button reset = new Button("Reset");
//                reset.setOnMouseClicked(new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent event) {
//                        highPointRegionText.setText(Values.HIGH_TEM_REGION);
//                        lowPonitRegionText.setText(Values.LOW_TEM_REGION);
//                        highPointText.setText(Values.HIGH_TEM_SCALE_RANGE);
//                        lowPointText.setText(Values.LOW_TEM_SCALE_RANGE);
//                    }
//                });
//
//                grid.add(selectUnit, 0, 0);
//                grid.add(unitSelection, 1, 0);
//                grid.add(temperatureScale, 0, 1);
//                grid.add(highPoint, 0, 2);
//                grid.add(highPointText, 1, 2);
//                grid.add(lowPoint, 0, 3);
//                grid.add(lowPointText, 1, 3);
//                grid.add(regionOfInterest, 0, 4);
//                grid.add(highPointOfRegion, 0, 5);
//                grid.add(highPointRegionText, 1, 5);
//                grid.add(lowPointOfRegion, 0, 6);
//                grid.add(lowPonitRegionText, 1, 6);
//                grid.add(setRanges, 0, 7);
//                grid.add(reset, 1, 7);
//
//                grid.setVgap(Values.GRID_VGAP);
//                Scene dialogScene = new Scene(grid, 400, 400);
//                dialog.setScene(dialogScene);
//                dialog.show();
//
//
//            }
//        });

    }

    private void handleComboBox(ComboBox<String> comboBox) {
        //Default combobox selection is Fusion
        comboBox.getSelectionModel().selectFirst();

        comboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                {
                    colorPallete = comboBox.getSelectionModel().getSelectedIndex();
                    System.out.println(colorPallete);
                }
            }
        });

    }


    private void handleHideBarButton(Button button, VBox vBox) {
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                TranslateTransition transition = new TranslateTransition();
                transition.setDuration(Duration.seconds(2));
                transition.setNode(vBox);
                transition.setToX(50);
                transition.play();

            }


        });

    }


    private void handleShowBarButton(Button button, VBox vBox) {
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                TranslateTransition transition = new TranslateTransition();
                transition.setDuration(Duration.seconds(1));
                transition.setNode(vBox);
                transition.setToX(0);
                transition.play();

            }

        });
    }

    private void handlePropertiesButton(Button button) {
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //System.out.println("Show");
                SupportCodes.printProp();
            }
        });
    }

    private void handleSmallMenuButton(Button button, Node root, BorderPane pane) {
        /*
        If this button is clicked show a Context
        menu with some options
         */
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ContextMenu contextMenu = new ContextMenu();
                MenuItem loadMenuItem = new MenuItem("Load......");//Load image from pictures directory
                MenuItem saveMenuItem = new MenuItem("Save......");//Save showing image in current directoty
                MenuItem openMenuItem = new MenuItem("Open......");//Open an image from user Selection
                MenuItem openTheme = new MenuItem("Themes");//Set Different Themes

                contextMenu.getItems().addAll(loadMenuItem, saveMenuItem, openMenuItem, openTheme);
                contextMenu.show(root, Side.TOP, Values.SMALL_MENU_DX, Values.SMALL_MENU_DY);

                openTheme.setOnAction(event1 -> {
                    Theme.themeMenu(pane);

                });

                loadMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                    }
                });
                saveMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("save");
                        saveImage(singleImageFile, singleImageName);
                    }
                });
                openMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            Metadata metadata = ImageMetadataReader.readMetadata(openImage());
                            SupportCodes.saveProp(metadata);

                        } catch (MalformedURLException e) {
                            System.out.println(e);
                        } catch (Exception e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Warning Dialog");
                            alert.setHeaderText("Look, There was an Error");
                            alert.setContentText("Please Try Again!");
                            alert.showAndWait();

                        }

                    }


                });


            }
        });
    }

    private void handleImageFolder(Button button) {
        // imageHandle(button);
        button.setOnMouseClicked(event -> imageHandle());
    }

    public void imageHandle() {
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





    private HBox setImageView() {


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

    private void writeReport(File dir) {
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

    private File openImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open  Thermal Image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("png", "*.png"), new FileChooser.ExtensionFilter("jpg", "*.jpg"), new FileChooser.ExtensionFilter("jpeg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(stage);

        try {
            TemperatureScaler temperatureScaler = new TemperatureScaler(SetTem.scaleTemMax, SetTem.scaleTemMin, SetTem.interestRangeMax, SetTem.interestRangeMin, colorPallete);
            temperatureScaler.processColorScale();

            Image image = new Image(file.toURI().toURL().toExternalForm());
            root.setCenter(imageDisplay);

            imageDisplay.setWidth(image.getHeight());
            imageDisplay.setHeight(image.getHeight());
            imageDisplay.getGraphicsContext2D().drawImage(image, 0, 0);


            new Thread(new Runnable() {
                @Override
                public void run() {

                    ColorSeparator colorSeparator = new ColorSeparator();
                    int count = colorSeparator.regionOfInterestDetector(image, temperatureScaler.getMidColor(), temperatureScaler.getRadius());
                    singleImage = colorSeparator.edgeMarker(image, Color.RED, (int) image.getWidth(), (int) image.getHeight());

                    imageDisplay.getGraphicsContext2D().drawImage(singleImage, 0, 0);
                    imageDisplay.setHeight(singleImage.getHeight());
                    imageDisplay.setWidth(singleImage.getWidth());
                    singleImageFile = file;
                    singleImageName = file.getName();

                }
            }).start();


        } catch (NullPointerException e) {
            System.out.println("Null File");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return file;
    }

    private void saveImage(File image, String name) {

        //String name = files[filesCount].getName().split("\\.")[0];


        try {
            String nam = name.split("\\.")[0];
            File outputFile = new File(image.getParentFile().getAbsolutePath(), nam + "_detected.png");   //Still Problem
            outputFile.createNewFile();
            BufferedImage bImage = SwingFXUtils.fromFXImage(singleImage, null);

            ImageIO.write(bImage, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Image is not Ready");
        }
        //System.out.println(outputFile.exists());
        //System.out.println(outputFile.mkdir());

    }

    private HBox reportBox(File file) {
        HBox box = new HBox();
        //Region
        return box;
    }
}
