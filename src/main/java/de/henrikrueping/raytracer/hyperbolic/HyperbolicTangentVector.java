package de.henrikrueping.raytracer.hyperbolic;

import de.henrikrueping.raytracer.general.TangentVector;

public class HyperbolicTangentVector implements TangentVector {
  protected double[][] d;

  public HyperbolicTangentVector() {
    this.d = new double[][] {{1, 0, 0, 0}, {0, 1, 0, 0}};
  }

  public HyperbolicTangentVector(HyperbolicTangentVector toCopy) {
    this(toCopy.d);
  }

  public HyperbolicTangentVector(double[] point, double[] dir) {
    d = new double[2][4];
    System.arraycopy(point, 0, d[0], 0, 4);
    System.arraycopy(dir, 0, d[1], 0, 4);
  }

  public HyperbolicTangentVector(double[][] coords) {
    d = new double[2][4];
    for (int i = 0; i < d.length; i++) {
      System.arraycopy(coords[i], 0, d[i], 0, 4);
    }
  }

  @Override
  public HyperbolicTangentVector move(double dist) {
    coshTransform(1, dist);
    return this;
  }

  protected void coshTransform(int frameIndex, double dist) {
    double cosh = Math.cosh(dist);
    double sinh = Math.sinh(dist);
    double buf;
    for (int i = 0; i < 4; i++) {
      buf = cosh * d[0][i] + sinh * d[frameIndex][i];
      d[frameIndex][i] = sinh * d[0][i] + cosh * d[frameIndex][i];
      d[0][i] = buf;
    }
  }

  @Override
  public double[] getPoint() {
    return d[0];
  }

  @Override
  public double[] getDirection() {
    return d[1];
  }
}
