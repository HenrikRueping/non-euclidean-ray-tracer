package de.henrikrueping.raytracer.s2r;

import de.henrikrueping.raytracer.general.TangentFrame;

public class S2rTangentFrame extends S2rTangentVector implements TangentFrame<S2rTangentVector> {

  public S2rTangentFrame(S2rTangentFrame toCopy) {
    super(toCopy.d);
  }

  public S2rTangentFrame() {
    super(new double[][] {{1.0, 0.0, 0.0, 0.0}, {0, 1.0, 0.0, 0.0}, {0, 0.0, 1.0, 0.0},
        {0.0, 0.0, 0, 1.0}});
  }

  @Override
  public S2rTangentVector move(double dist) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public double[] getPoint() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public double[] getDirection() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void strafeRight(double dist) {
    // TODO Auto-generated method stub

  }

  @Override
  public void strafeUp(double dist) {
    // TODO Auto-generated method stub

  }

  @Override
  public void turnLeft(double angle) {
    // TODO Auto-generated method stub

  }

  @Override
  public void turnUp(double angle) {
    // TODO Auto-generated method stub

  }

  @Override
  public void pitch(double angle) {
    // TODO Auto-generated method stub

  }

  @Override
  public S2rTangentVector obtainRayFor(double d, double e) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public double[] getRight() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public double[] getDown() {
    // TODO Auto-generated method stub
    return null;
  }

}
