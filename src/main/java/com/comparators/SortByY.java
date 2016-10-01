package com.comparators;

import com.modules.ImagePixel;

import java.util.Comparator;


public class SortByY implements Comparator<ImagePixel> {
    @Override
    public int compare(ImagePixel o1, ImagePixel o2) {
        return o1.getY() - o2.getY();
    }
}
