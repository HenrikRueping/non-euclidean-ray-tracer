package de.henrikrueping.raytracer.s2r;

import de.henrikrueping.raytracer.general.TangentVector;

public class S2rTangentVector implements TangentVector {
  double[][] d;

  public S2rTangentVector() {
    this.d = new double[][] {{1, 0, 0, 0}, {0, 1, 0, 0}};
  }

  public S2rTangentVector(S2rTangentVector toCopy) {
    this(toCopy.d);
  }

  public S2rTangentVector(double[] point, double[] dir) {
    d = new double[2][4];
    System.arraycopy(point, 0, d[0], 0, 4);
    System.arraycopy(dir, 0, d[1], 0, 4);
  }

  public S2rTangentVector(double[][] coords) {
    d = new double[coords.length][4];
    for (int i = 0; i < coords.length; i++) {
      System.arraycopy(coords[i], 0, d[i], 0, 4);
    }
  }

  @Override
  public S2rTangentVector move(double dist) {
    d[0][3] += d[1][3] * dist;
    double factor = Math.sqrt(1 - d[1][3] * d[1][3]);
    double buf;
    double cos = Math.cos(dist * factor);
    double sin = Math.sin(dist * factor);
    for (int i = 0; i < 3; i++) {
      buf = d[0][i];
      d[0][i] = cos * buf + sin * d[1][i];
      d[1][i] = -sin * buf + cos * d[1][i];
    }
    return this;
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
