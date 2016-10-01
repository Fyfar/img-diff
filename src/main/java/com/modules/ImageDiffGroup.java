package com.modules;

import com.comparators.SortByX;
import com.comparators.SortByY;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;


@Component
public class ImageDiffGroup {
    private static final int DISTANCE_BETWEEN_PIXELS = 100;
    private List<ImagePixel> pixels = new ArrayList<>();

    private int x;
    private int y;
    private int width;
    private int height;

    public void addPixelToGroup(ImagePixel pixel) {
        pixels.add(pixel);
        setGroupSize();
    }

    private ImagePixel getLastPixelInGroup() {
        return pixels.get(pixels.size() - 1);
    }

    public boolean isPixelNearGroup(ImagePixel pixel) {
        if (pixels.isEmpty()) {
            return true;
        }

        int x1 = pixel.getX();
        int x2 = getLastPixelInGroup().getX();
        int y1 = pixel.getY();
        int y2 = getLastPixelInGroup().getY();
        return sqrt(pow((double)(x2 - x1), 2) + pow((double)(y2 - y1), 2)) <= DISTANCE_BETWEEN_PIXELS;
    }

    private void setGroupSize() {
        pixels.sort(new SortByX());

        this.x = pixels.get(0).getX();
        this.width = getLastPixelInGroup().getX() - pixels.get(0).getX();

        pixels.sort(new SortByY());

        this.y = pixels.get(0).getY();
        this.height = getLastPixelInGroup().getY() - pixels.get(0).getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageDiffGroup that = (ImageDiffGroup) o;

        if (x != that.x) return false;
        if (y != that.y) return false;
        if (width != that.width) return false;
        if (height != that.height) return false;
        return pixels != null ? pixels.equals(that.pixels) : that.pixels == null;

    }

    @Override
    public int hashCode() {
        int result = pixels != null ? pixels.hashCode() : 0;
        result = 31 * result + x;
        result = 31 * result + y;
        result = 31 * result + width;
        result = 31 * result + height;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ImageDiffGroup{");
        sb.append("pixels=").append(pixels);
        sb.append('}');
        return sb.toString();
    }
}
