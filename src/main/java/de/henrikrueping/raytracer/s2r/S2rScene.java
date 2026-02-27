package de.henrikrueping.raytracer.s2r;

import java.awt.Color;
import de.henrikrueping.raytracer.general.Scene;

public class S2rScene extends Scene<S2rTangentVector, S2rTangentFrame> {

  public S2rScene(S2rTangentFrame frame) {
    super(frame);
  }

  public S2rScene() {
    this(new S2rTangentFrame());
  }

  @Override
  public void addSphere(double radius, Color color) {
    addEntity(new S2rSphere(getFrame(), radius, color));
  }

  @Override
  public void addPlane(Color color) {
    addEntity(new S2rPlane(getFrame(), color));
  }

  @Override
  public void addReflectingPlane(Color color) {
    addEntity(new S2rPlane(getFrame(), color, true));
  }

  @Override
  public void addCylinder(double length, double radius, Color color) {
    addEntity(new S2rCylinder(getFrame(), length, radius, color));
  }

  @Override
  public void teleportTo(S2rTangentFrame frame) {
    setFrame(new S2rTangentFrame(frame));

  }



}
