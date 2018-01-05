package com.shankeerthan;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class ImagingThread extends Thread {
    private static TemperatureScaler temperatureScaler;
    private File[] files;
    private int low, high;
    private double scaleMax, scaleMin;
    private double rangeMax, rangeMin;
    private int pallete;
    private File dir;
    private Canvas canvas;
    private Image finalImage;

    public ImagingThread(File[] files, int low, int high, double scaleMax, double scaleMin, double rangeMax, double rangeMin, int colorPallete, File dir, Canvas canvas) {
        this.scaleMax = scaleMax;
        this.scaleMin = scaleMin;
        this.rangeMax = rangeMax;
        this.rangeMin = rangeMin;
        this.files = files;
        this.low = low;
        this.high = high;
        pallete = colorPallete;
        this.dir = dir;
        this.canvas = canvas;


        temperatureScaler = new TemperatureScaler(scaleMax, scaleMin, rangeMax, rangeMin, pallete);
        temperatureScaler.processColorScale();
    }


    @Override
    public void run() {
        super.run();
        //System.out.println("running");
        //iterate over fileleft.pngs

        for (int filesCount = low; filesCount < high; filesCount++) {

            try {

                Image image;
                synchronized (this) {

                    image = new Image(files[filesCount].toURI().toURL().toExternalForm());
                }

                ColorSeparator colorSeparator = new ColorSeparator();

                int count = colorSeparator.regionOfInterestDetector(image, temperatureScaler.getMidColor(), temperatureScaler.getRadius());
                Main.counts[filesCount] = count;
                finalImage = colorSeparator.edgeMarker(image, Color.RED, (int) image.getWidth(), (int) image.getHeight());
                synchronized (this) {
                    canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    canvas.setWidth(finalImage.getWidth());
                    canvas.setHeight(finalImage.getHeight());
                    canvas.getGraphicsContext2D().drawImage(finalImage, 0, 0);
                }
                String name = files[filesCount].getName().split("\\.")[0];
                File outputFile = new File(dir.getAbsolutePath(), name + "_detected.png");   //Still Problem

                outputFile.createNewFile();
                //System.out.println(outputFile.exists());
                //System.out.println(outputFile.mkdir());
                BufferedImage bImage = SwingFXUtils.fromFXImage(finalImage, null);

                ImageIO.write(bImage, "png", outputFile);

                Main.imagesCheck[filesCount] = 1;
                Main.images[filesCount] = finalImage;


            } catch (MalformedURLException e) {
                e.printStackTrace();
                System.out.println(e);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
