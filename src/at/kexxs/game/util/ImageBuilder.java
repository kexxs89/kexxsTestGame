package at.kexxs.game.util;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageBuilder {

  public static BufferedImage colorImage(BufferedImage image, Color color) {
    final int width = image.getWidth();
    final int height = image.getHeight();
    final WritableRaster raster = image.getRaster();

    for (int xx = 0; xx < width; xx++) {
      for (int yy = 0; yy < height; yy++) {
        final int[] pixels = raster.getPixel(xx, yy, (int[]) null);
        pixels[0] = color.getRed();
        pixels[1] = color.getGreen();
        pixels[2] = color.getBlue();
        raster.setPixel(xx, yy, pixels);
      }
    }
    return image;
  }

  public static BufferedImage getBufferdImageFromPath(String path) {

    BufferedImage bufferdImage = null;
    try {
      bufferdImage = ImageIO.read(new File(path));
    } catch (final IOException e) {
      e.printStackTrace();
    }

    return bufferdImage;
  }

  public static Image scaleBufferdImage(BufferedImage bufferdImage, int width, int height) {
    return bufferdImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
  }

}
