package com.modules;


public class RGB {
    private int red;
    private int green;
    private int blue;

    public RGB(int color) {
        parseColorToRGB(color);
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    @Override
    public String toString() {
        return "RGB{" +
                "red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                '}';
    }

    private void parseColorToRGB(int color) {
        red = (color >> 16) & 0xff;
        green = (color >>  8) & 0xff;
        blue = (color) & 0xff;
    }
}
