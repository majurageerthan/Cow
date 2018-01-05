package com.shankeerthan;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SetTem {
    protected static double scaleTemMax = 35;
    protected static double scaleTemMin = 25;
    protected static double interestRangeMax = 32;
    protected static double interestRangeMin = 28;
    protected static int unit = Values.CELSIUS;


    public static void openTempWin(Button button, Stage stage, Label high, Label low) {
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                final Stage dialog = new Stage();
                dialog.setTitle("Adjust Temperature Scale Setting");
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(stage);


                GridPane grid = new GridPane();

                Label selectUnit = new Label("Temperature Unit");

                ComboBox<String> unitSelection = new ComboBox<String>();
                unitSelection.getItems().addAll("Celcious", "Frahneit", "Kelvin");
                unitSelection.getSelectionModel().selectFirst();


                Label temperatureScale = new Label("Adjust Temperature Scale Range");

                Label highPoint = new Label("High Temperature of Range");
                Label lowPoint = new Label("Low Temperature of Range");

                TextField highPointText = new TextField(Double.toString(scaleTemMax));
                TextField lowPointText = new TextField(Double.toString(scaleTemMin));

                Label regionOfInterest = new Label("Region of Interset Temperature Scale");

                Label highPointOfRegion = new Label("High Temperature");
                Label lowPointOfRegion = new Label("Low Temperature ");

                TextField highPointRegionText = new TextField(Double.toString(interestRangeMax));
                TextField lowPonitRegionText = new TextField(Double.toString(interestRangeMin));

                Button setRanges = new Button("Apply");

                setRanges.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            scaleTemMax = Double.parseDouble(highPointText.getText());
                            scaleTemMin = Double.parseDouble(lowPointText.getText());
                            interestRangeMax = Double.parseDouble(highPointRegionText.getText());
                            interestRangeMin = Double.parseDouble(lowPonitRegionText.getText());
                            unit = unitSelection.getSelectionModel().getSelectedIndex();
                            high.setText(highPointRegionText.getText());
                            low.setText(lowPonitRegionText.getText());
                            dialog.close();
                        } catch (NumberFormatException e) {
                            System.out.println(e);
                        }
                    }
                });
                Button reset = new Button("Reset");
                reset.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        highPointRegionText.setText(Values.HIGH_TEM_REGION);
                        lowPonitRegionText.setText(Values.LOW_TEM_REGION);
                        highPointText.setText(Values.HIGH_TEM_SCALE_RANGE);
                        lowPointText.setText(Values.LOW_TEM_SCALE_RANGE);
                    }
                });

                grid.add(selectUnit, 0, 0);
                grid.add(unitSelection, 1, 0);
                grid.add(temperatureScale, 0, 1);
                grid.add(highPoint, 0, 2);
                grid.add(highPointText, 1, 2);
                grid.add(lowPoint, 0, 3);
                grid.add(lowPointText, 1, 3);
                grid.add(regionOfInterest, 0, 4);
                grid.add(highPointOfRegion, 0, 5);
                grid.add(highPointRegionText, 1, 5);
                grid.add(lowPointOfRegion, 0, 6);
                grid.add(lowPonitRegionText, 1, 6);
                grid.add(setRanges, 0, 7);
                grid.add(reset, 1, 7);

                grid.setVgap(Values.GRID_VGAP);
                Scene dialogScene = new Scene(grid, 400, 400);
                dialog.setScene(dialogScene);
                dialog.show();


            }
        });
    }

}
