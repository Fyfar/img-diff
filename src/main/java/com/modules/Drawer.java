package com.modules;

import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class Drawer {
    private static final Logger LOG = Logger.getLogger(Drawer.class);

    private static final String OUTPUT_IMAGE_EXTENSION = "png";
    public static final String OUTPUT_IMAGE_NAME = "output." + OUTPUT_IMAGE_EXTENSION;


    public void fillGroupsOnImage(List<ImageDiffGroup> groups, BufferedImage image) {
        BufferedImage copiedImage = copyImage(image);

        for (ImageDiffGroup group : groups) {
            Graphics2D graphics2D = copiedImage.createGraphics();
            float thickness = 1f;
            graphics2D.setStroke(new BasicStroke(thickness));
            graphics2D.setColor(Color.RED);
            graphics2D.drawRect(group.getX(), group.getY() - 2, group.getWidth(), group.getHeight() + 4);
        }

        saveImage(copiedImage);
    }

    private BufferedImage copyImage(BufferedImage image) {
        BufferedImage b = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics g = b.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return b;
    }

    private void saveImage(BufferedImage image) {
        try {
            ImageIO.write(image, OUTPUT_IMAGE_EXTENSION, new File(OUTPUT_IMAGE_NAME));
        } catch (IOException e) {
            LOG.error("Error while saving image, ", e);
        }
    }
}
