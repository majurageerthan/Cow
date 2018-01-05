package com.shankeerthan;

public class Point {
    private int x;
    private int y;
    private int regionNumber;

    Point(int x, int y, int regionNumber) {
        this.x = x;
        this.y = y;
        this.regionNumber = regionNumber;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRegionNumber() {
        return regionNumber;
    }
}
