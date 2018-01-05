package com.shankeerthan;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ All code are changed
/*
This class contains methods to
convert Temparature Scale to Color Scale
 */
public class TemperatureScaler {
    private double scaleMax, scaleMin;
    private double rangeMax, rangeMin;
    private int colorPallete;
    private Image palleteImage;
    private Color midColor;
    private double radius;

    public TemperatureScaler(double scaleMax, double scaleMin, double rangeMax, double rangeMin, int colorPallete) {
        this.scaleMax = scaleMax;
        this.scaleMin = scaleMin;
        this.rangeMax = rangeMax;
        this.rangeMin = rangeMin;
        this.colorPallete = colorPallete;
        openImage();
    }

    /*
    This method assigning correct Palllete Image to palleteImage Reference
    according to ColorPallete value
     */
    private void openImage() {
        /*
        This mehod open color pallete image according to given
        integer colorPalllete
         */
        switch (colorPallete) {
            case Values.IRON:
                palleteImage = new Image("file:" + "ColorPalletes/iron.PNG");
                break;
            case Values.LAVA:
                palleteImage = new Image("file:" + "ColorPalletes/lava.PNG");
                break;
            case Values.ARCTIC:
                palleteImage = new Image("file:" + "ColorPalletes/arctic.PNG");
                break;
            case Values.GRAY:
                palleteImage = new Image("file:" + "ColorPalletes/gray.PNG");
                break;
            case Values.RAINBOW:
                palleteImage = new Image("file:" + "ColorPalletes/rainbow.PNG");
                break;
            case Values.RAINBOW_HC:
                palleteImage = new Image("file:" + "ColorPalletes/rainbow_hc.PNG");
                break;
        }

    }


    public void processColorScale() {
        double width = palleteImage.getWidth();
        double height = palleteImage.getHeight();

        double rangeMaxY = height * (scaleMax - rangeMax) / (scaleMax - scaleMin);
        double rangeMinY = height * (scaleMax - rangeMin) / (scaleMax - scaleMin);

        Color rangeColor = palleteImage.getPixelReader().getColor((int) width / 2, (int) rangeMaxY);
        midColor = palleteImage.getPixelReader().getColor((int) width / 2, (int) ((rangeMaxY + rangeMinY) / 2));

        radius = ColorSeparator.getDistance(rangeColor.getRed(), rangeColor.getGreen(), rangeColor.getBlue(), midColor.getRed(), midColor.getGreen(), midColor.getBlue());
    }

    public Color getMidColor() {
        return midColor;
    }

    public double getRadius() {
        return radius;
    }
}