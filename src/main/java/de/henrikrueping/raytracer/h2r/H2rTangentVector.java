package de.henrikrueping.raytracer.h2r;

import de.henrikrueping.raytracer.general.TangentVector;

public class H2rTangentVector implements TangentVector {
  double[][] d;

  public H2rTangentVector() {
    this.d = new double[][] {{1, 0, 0, 0}, {0, 1, 0, 0}};
  }

  public H2rTangentVector(H2rTangentVector toCopy) {
    this(toCopy.d);
  }

  public H2rTangentVector(double[] point, double[] dir) {
    d = new double[2][4];
    System.arraycopy(point, 0, d[0], 0, 4);
    System.arraycopy(dir, 0, d[1], 0, 4);
  }

  public H2rTangentVector(double[][] coords) {
    d = new double[coords.length][4];
    for (int i = 0; i < coords.length; i++) {
      System.arraycopy(coords[i], 0, d[i], 0, 4);
    }
  }

  @Override
  public H2rTangentVector move(double dist) {
    d[0][3] += d[1][3] * dist;
    double factor = Math.sqrt(1 - d[1][3] * d[1][3]);
    double buf;
    double cosh = Math.cosh(dist * factor);
    double sinh = Math.sinh(dist * factor);
    for (int i = 0; i < 3; i++) {
      buf = d[0][i];
      d[0][i] = cosh * buf + sinh * d[1][i];
      d[1][i] = sinh * buf + cosh * d[1][i];
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
