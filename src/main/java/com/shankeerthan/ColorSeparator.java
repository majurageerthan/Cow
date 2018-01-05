package com.shankeerthan;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.ListIterator;


/*
This class contains static methods to proceesing color of each pixel in an image

 */
public class ColorSeparator {
    private int regionsCount;
    private ArrayList<Point> points;

    public ColorSeparator() {
        points = new ArrayList<Point>();
        regionsCount = 0;

    }

    public static boolean compareColors(Color color1, Color color2, double radius) {
        double color1_rValue = color1.getRed();
        double color1_bValue = color1.getBlue();
        double color1_gValue = color1.getGreen();
        double color2_rValue = color2.getRed();
        double color2_bValue = color2.getBlue();
        double color2_gValue = color2.getGreen();

        //Check wheather two points are inside sphere
        if (getDistance(color1_rValue, color1_gValue, color1_bValue, color2_rValue, color2_gValue, color2_bValue) <= radius) {
            return true;
        } else {
            return false;
        }

    }

    /*
    method return distance between two points in  3 dimensional

     */
    public static double getDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
        double radius = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2) + Math.pow((z1 - z2), 2));
        //System.out.println(radius);
        return radius;
    }

    protected int regionOfInterestDetector(Image image, Color color, double radius) {
        //System.out.println("Image"+image);
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                if (compareColors(image.getPixelReader().getColor(i, j), color, radius)) {
                    if (points.isEmpty()) {
                        regionsCount++;
                        points.add(new Point(i, j, regionsCount));
                    } else {
                        Point point;
                        int regionNumber;
                        ListIterator<Point> iterator = points.listIterator();
                        boolean isInExistingRegion = false;
                        while (iterator.hasNext()) {
                            point = iterator.next();
                            regionNumber = pointRegionFinder(point, i, j);
                            if (regionNumber != 0) {
                                isInExistingRegion = true;
                                points.add(new Point(i, j, regionNumber));
                                break;
                            }
                        }
                        if (!isInExistingRegion) {
                            //System.out.println(regionsCount);
                            regionsCount++;
                            points.add(new Point(i, j, regionsCount));
                        }
                    }
                }
            }
        }
        return regionsCount;
    }

    protected Image edgeMarker(Image image, Color color, int width, int height) {
        ListIterator<Point> listIterator = points.listIterator();
        Point point;

        WritableImage writableImage = new WritableImage(width - 30, height - 30);
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        Color color1;
        PixelReader pixelReader = image.getPixelReader();
        for (int i = 0; i < width - 30; i++) {
            for (int j = 0; j < height - 30; j++) {
                color1 = image.getPixelReader().getColor(i, j);
                pixelWriter.setColor(i, j, color1);
            }
        }//Image Writing is finisshed
        while (listIterator.hasNext()) {
            point = listIterator.next();
            try {
                pixelWriter.setColor(point.getX(), point.getY(), color);
            } catch (IndexOutOfBoundsException e) {

            }

        }
        return writableImage;
    }

    private int pointRegionFinder(Point point, int x, int y) {

        if ((point.getX() == x - 1 && point.getY() == y) || (point.getX() == x - 1 && point.getY() == y - 1) || (point.getX() == x && point.getY() == y - 1) || (point.getX() == x - 1 && point.getY() == y + 1)) {

            return point.getRegionNumber();
        } else {

            return 0;
        }
    }
}
