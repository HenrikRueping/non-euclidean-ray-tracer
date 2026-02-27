package de.henrikrueping.raytracer.spherical;

import java.awt.Color;
import de.henrikrueping.raytracer.general.Scene;

public class SphericalScene extends Scene<SphericalTangentVector, SphericalTangentFrame> {

  public SphericalScene(SphericalTangentFrame frame) {
    super(frame);
  }

  public SphericalScene() {
    this(new SphericalTangentFrame());
  }

  @Override
  public void teleportTo(SphericalTangentFrame frame) {
    setFrame(new SphericalTangentFrame(frame));
  }

  @Override
  public void addSphere(double radius, Color color) {
    addEntity(new SphericalSphere(getFrame(), radius, color));

  }

  @Override
  public void addPlane(Color color) {
    addEntity(new SphericalPlane(getFrame(), false, color));

  }

  @Override
  public void addReflectingPlane(Color color) {
    addEntity(new SphericalPlane(getFrame(), true, color));

  }

  @Override
  public void addCylinder(double length, double radius, Color color) {
    addEntity(new SphericalCylinder(getFrame(), length, radius, color));
  }

}
