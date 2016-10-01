package com.modules;

import java.util.Objects;


public class ImagePixel {
    private int x;
    private int y;
    private RGB color;

    public ImagePixel(int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.color = new RGB(color);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public RGB getColor() {
        return color;
    }

    public void setColor(RGB color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImagePixel that = (ImagePixel) o;
        return x == that.x &&
                y == that.y &&
                color == that.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, color);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ImagePixel{");
        sb.append("x=").append(x);
        sb.append(", y=").append(y);
        sb.append(", color=").append(color);
        sb.append('}');
        return sb.toString();
    }
}
