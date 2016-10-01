package com.comparators;

import com.modules.ImagePixel;

import java.util.Comparator;


public class SortByX implements Comparator<ImagePixel> {
    @Override
    public int compare(ImagePixel o1, ImagePixel o2) {
        return o1.getX() - o2.getX();
    }
}