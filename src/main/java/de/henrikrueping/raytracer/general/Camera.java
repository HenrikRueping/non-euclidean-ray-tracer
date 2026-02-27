package de.henrikrueping.raytracer.general;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Camera {

  private int width;
  private int height;
  private double maxDist;


  public Camera(int width, int height, double maxDist) {
    super();
    this.width = width;
    this.height = height;
    this.maxDist = maxDist;
  }

  public Camera(int width, int height) {
    this(width, height, 7);
  }

  public Camera setMaxDist(double maxDist) {
    this.maxDist = maxDist;
    return this;
  }



  private <TV extends TangentVector, TF extends TangentFrame<TV>> void renderToGraphics(
      Graphics graphics, Scene<TV, TF> scene) {
    long startMillis = System.currentTimeMillis();
    double factor = Math.sqrt(width / height);
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {

        TV ray = scene.getFrame().obtainRayFor((2.0 * x / width - 1.0) * factor,
            (2.0 * y / height - 1.0) / factor);

        graphics.setColor(scene.getColor(ray, maxDist));
        graphics.fillRect(x, y, 1, 1);
      }
      long cur = System.currentTimeMillis();
      System.out.println(String.format("%1$,.1f perCent ETA: %2$,.2f", 100.0 * x / width,
          (cur - startMillis) * (width - x - 1) / (x + 1) / 1000.0));
    }
  }

  public <TV extends TangentVector, TF extends TangentFrame<TV>> void render(Scene<TV, TF> scene) {
    BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D graphics = bi.createGraphics();
    renderToGraphics(graphics, scene);
    try {
      ImageIO.write(bi, "PNG", new File("yourImageName.PNG"));
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
