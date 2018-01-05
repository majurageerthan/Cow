package com.shankeerthan;


import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class RightClickMenu {
    static BorderPane paneS;
    public static Scene sceneS;

    public static void RightClick(Scene scene, Stage stage) {
        sceneS = scene;
        keyEvents();

        // Create ContextMenu
        ContextMenu contextMenu = new ContextMenu();

        MenuItem temp = new MenuItem("Set Temperature");


        MenuItem imgFolder = new MenuItem("Open image folder");
        imgFolder.setOnAction(event -> {
            //Main.callImag();
        });

        MenuItem zoom = new MenuItem("Zoom and pan");
        zoom.setOnAction(event -> Zoom.startZoom());

        MenuItem theme = new MenuItem("Themes");
        theme.setOnAction(event -> Theme.themeMenu(paneS));

        MenuItem aboutUs = new MenuItem("About us");

        SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();

        createMenu(temp, "Ctrl+t");
        createMenu(imgFolder, "Ctrl+i");
        createMenu(zoom, "z");
        createMenu(theme, "t");
        createMenu(aboutUs, "Ctrl+u");

        temp.setOnAction(event -> System.out.println("ttt"));

        // Set accelerator to menuItem.
        //menuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));


        //    SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();


        // Add MenuItem to ContextMenu
        contextMenu.getItems().addAll(temp, imgFolder, zoom, separatorMenuItem, theme, aboutUs);

        // When user right-click on Circle
        scene.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

            @Override
            public void handle(ContextMenuEvent event) {

                contextMenu.show(stage, event.getScreenX(), event.getScreenY());

            }
        });


    }


    public static void createMenu(MenuItem menuItem, String shortCut) {


        // Set accelerator to menuItem.
        menuItem.setAccelerator(KeyCombination.keyCombination(shortCut));

    }


    public static void paneSave(BorderPane pane) {
        paneS = pane;
    }

    public static void keyEvents() {
        sceneS.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.T) {
                Theme.themeMenu(paneS);
            }
        });

    }




}

