package de.henrikrueping.raytracer.euclidean;

import java.awt.Color;
import de.henrikrueping.raytracer.general.Scene;

public class EuclideanScene extends Scene<EuclideanTangentVector, EuclideanTangentFrame> {

  public EuclideanScene(EuclideanTangentFrame frame) {
    super(frame);
  }

  public EuclideanScene() {
    super(new EuclideanTangentFrame());
  }

  @Override
  public void teleportTo(EuclideanTangentFrame frame) {
    setFrame(new EuclideanTangentFrame(frame));

  }

  @Override
  public void addSphere(double radius, Color color) {
    addEntity(new EuclideanSphere(getFrame(), radius, color));

  }

  @Override
  public void addPlane(Color color) {
    addEntity(new EuclideanPlane(getFrame(), false, color));

  }

  @Override
  public void addReflectingPlane(Color color) {
    addEntity(new EuclideanPlane(getFrame(), true, color));

  }

  @Override
  public void addCylinder(double length, double radius, Color color) {
    addEntity(new EuclideanCylinder(getFrame(), length, radius, color));
  }


}
