package de.henrikrueping.raytracer.hyperbolic;

import java.awt.Color;
import de.henrikrueping.raytracer.general.Scene;

public class HyperbolicScene extends Scene<HyperbolicTangentVector, HyperbolicFrame> {

  public HyperbolicScene(HyperbolicFrame frame) {
    super(frame);
  }

  public HyperbolicScene() {
    this(new HyperbolicFrame());
  }

  @Override
  public void addSphere(double radius, Color color) {
    addEntity(new HyperbolicSphere(getFrame(), radius, color));
  }

  @Override
  public void addPlane(Color color) {
    addEntity(new HyperbolicPlane(getFrame(), color));
  }

  @Override
  public void addReflectingPlane(Color color) {
    addEntity(new HyperbolicPlane(getFrame(), color, true));
  }

  @Override
  public void addCylinder(double length, double radius, Color color) {
    addEntity(new HyperbolicCylinder(getFrame(), length, radius, color));
  }

  @Override
  public void teleportTo(HyperbolicFrame frame) {
    setFrame(new HyperbolicFrame(frame));

  }



}
