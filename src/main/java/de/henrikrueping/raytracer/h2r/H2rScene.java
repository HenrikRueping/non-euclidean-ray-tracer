package de.henrikrueping.raytracer.h2r;

import java.awt.Color;
import de.henrikrueping.raytracer.general.Scene;

public class H2rScene extends Scene<H2rTangentVector, H2rTangentFrame> {

  public H2rScene(H2rTangentFrame frame) {
    super(frame);
  }

  public H2rScene() {
    this(new H2rTangentFrame());
  }

  @Override
  public void addSphere(double radius, Color color) {
    addEntity(new H2rSphere(getFrame(), radius, color));
  }

  @Override
  public void addPlane(Color color) {
    addEntity(new H2rPlane(getFrame(), color));
  }

  @Override
  public void addReflectingPlane(Color color) {
    addEntity(new H2rPlane(getFrame(), color, true));
  }

  @Override
  public void addCylinder(double length, double radius, Color color) {
    addEntity(new H2rCylinder(getFrame(), length, radius, color));
  }

  @Override
  public void teleportTo(H2rTangentFrame frame) {
    setFrame(new H2rTangentFrame(frame));

  }



}
