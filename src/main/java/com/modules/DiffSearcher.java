package com.modules;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


@Component
public class DiffSearcher {
    private static final Logger LOG = Logger.getLogger(DiffSearcher.class);

    private static final int COLOR_DIFF_PERCENT = 10;
    private static final int RGB_MAX_VALUE = 255;
    private static final String TEST_IMAGE1_PATH = "image1.png";
    private static final String TEST_IMAGE2_PATH = "image2.png";

    private List<ImageDiffGroup> groups = new ArrayList<>();

    public DiffSearcher() {
        groups.add(new ImageDiffGroup());
    }

    public void loadImages(String firstImgPath, String secondImgPath) {
        BufferedImage firstImage;
        BufferedImage secondImage;

        try {
            File firstImgFile = new File(firstImgPath);
            File secondImgFile = new File(secondImgPath);

            if (!firstImgFile.exists() || !secondImgFile.exists()) {
                LOG.error("Files are not exists, " + firstImgPath + ", " + secondImgPath);
                return;
            }

            firstImage = ImageIO.read(firstImgFile);
            secondImage = ImageIO.read(secondImgFile);

            if (firstImgFile.length() > 0 && secondImgFile.length() > 0) {
                findDifferences(firstImage, secondImage);
            } else {
                LOG.error("Files doesn't exists: " + firstImgPath + ", " + secondImgPath);
            }
        } catch (IOException e) {
            LOG.error("Error while loading images", e);
        }
    }

    private void findDifferences(BufferedImage firstImage, BufferedImage secondImage) {
        int width1 = firstImage.getWidth(null);
        int width2 = secondImage.getWidth(null);
        int height1 = firstImage.getHeight(null);
        int height2 = secondImage.getHeight(null);

        checkImageDimensions(width1, width2, height1, height2);

        for (int y = 0; y < height1; y++) {
            for (int x = 0; x < width1; x++) {
                ImagePixel firstImagePixel = new ImagePixel(x, y, firstImage.getRGB(x, y));
                ImagePixel secondImagePixel = new ImagePixel(x, y, secondImage.getRGB(x, y));

                if (colorDiffInPercent(firstImagePixel, secondImagePixel) >= COLOR_DIFF_PERCENT) {
                    addPixelToGroup(secondImagePixel);
                }
            }
        }

        Drawer drawer = new Drawer();
        drawer.fillGroupsOnImage(groups, secondImage);
    }

    private void checkImageDimensions(int width1, int width2, int height1, int height2) {
        if ((width1 != width2) || (height1 != height2)) {
            LOG.error("Error: Images dimensions mismatch: " +
                    "width1 = " + width1 +
                    ", width2 = " + width2 +
                    ", height1 = " + height1 +
                    ", height2 = " + height2);
            System.exit(1);
        }
    }

    // RGB distance in percent algorithm
    private double colorDiffInPercent(ImagePixel firstImgPixel, ImagePixel secondImgPixel) {
        RGB firstColor = firstImgPixel.getColor();
        RGB secondColor = secondImgPixel.getColor();

        int diffRed = Math.abs(firstColor.getRed() - secondColor.getRed());
        int diffGreen = Math.abs(firstColor.getGreen() - secondColor.getGreen());
        int diffBlue = Math.abs(firstColor.getBlue() - secondColor.getBlue());

        double pctDiffRed = (double) diffRed / RGB_MAX_VALUE;
        double pctDiffGreen = (double) diffGreen / RGB_MAX_VALUE;
        double pctDiffBlue = (double) diffBlue / RGB_MAX_VALUE;

        return (pctDiffRed + pctDiffGreen + pctDiffBlue) / 3 * 100;
    }

    // Load test image if not defined
    public void loadImages() throws URISyntaxException {
        LOG.debug("Load test images from resource folder");
        loadImages(
                ClassLoader.getSystemResource(TEST_IMAGE1_PATH).getPath(),
                ClassLoader.getSystemResource(TEST_IMAGE2_PATH).getPath());
    }

    private void addPixelToGroup(ImagePixel pixel) {
        boolean pixelAdded = false;
        for (ImageDiffGroup group : groups) {
            if (group.isPixelNearGroup(pixel)) {
                group.addPixelToGroup(pixel);
                pixelAdded = true;
                break;
            }
        }

        if (!pixelAdded) {
            ImageDiffGroup newGroup = new ImageDiffGroup();
            newGroup.addPixelToGroup(pixel);
            groups.add(newGroup);
        }
    }
}
