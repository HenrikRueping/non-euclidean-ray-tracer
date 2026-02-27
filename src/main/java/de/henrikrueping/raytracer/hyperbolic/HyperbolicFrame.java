package de.henrikrueping.raytracer.hyperbolic;

import de.henrikrueping.raytracer.Helper;
import de.henrikrueping.raytracer.general.TangentFrame;

public class HyperbolicFrame extends HyperbolicTangentVector
    implements TangentFrame<HyperbolicTangentVector> {

  public HyperbolicFrame() {
    this.d = new double[][] {{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
  }

  public HyperbolicFrame(HyperbolicFrame toCopy) {
    d = new double[4][4];
    for (int i = 0; i < d.length; i++) {
      System.arraycopy(toCopy.d[i], 0, d[i], 0, 4);
    }
  }


  @Override
  public void strafeRight(double dist) {
    coshTransform(2, dist);
  }

  @Override
  public void strafeUp(double dist) {
    coshTransform(3, dist);
  }

  private void cosTransform(double angle, int frameIndex, int frameIndex2) {
    double cos = Math.cos(angle);
    double sin = Math.sin(angle);
    double buf;
    for (int i = 0; i < 4; i++) {
      buf = cos * d[frameIndex][i] + sin * d[frameIndex2][i];
      d[frameIndex2][i] = -sin * d[frameIndex][i] + cos * d[frameIndex2][i];
      d[frameIndex][i] = buf;
    }
  }

  @Override
  public void turnLeft(double angle) {
    cosTransform(angle, 1, 2);
  }

  @Override
  public void turnUp(double angle) {
    cosTransform(angle, 1, 3);
  }

  @Override
  public void pitch(double angle) {
    cosTransform(angle, 2, 3);
  }

  @Override
  public HyperbolicTangentVector obtainRayFor(double d, double e) {
    double[] tangent = new double[4];
    for (int i = 0; i < 4; i++) {
      tangent[i] = getDirection()[i] + d * getRight()[i] + e * getDown()[i];
    }
    double length = Math.sqrt(Helper.hypInnerProduct(tangent, tangent));
    for (int i = 0; i < 4; i++) {
      tangent[i] = tangent[i] / length;
    }
    return new HyperbolicTangentVector(getPoint(), tangent);
  }

  @Override
  public double[] getRight() {
    return d[2];
  }

  @Override
  public double[] getDown() {
    return d[3];
  }
}
