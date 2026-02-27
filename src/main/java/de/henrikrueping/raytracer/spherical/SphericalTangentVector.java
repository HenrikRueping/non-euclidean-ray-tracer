package de.henrikrueping.raytracer.spherical;

import de.henrikrueping.raytracer.general.TangentVector;

public class SphericalTangentVector implements TangentVector {
  protected double[][] d;

  public SphericalTangentVector() {
    this.d = new double[][] {{1, 0, 0, 0}, {0, 1, 0, 0}};
  }

  public SphericalTangentVector(SphericalTangentVector toCopy) {
    this(toCopy.d);
  }

  public SphericalTangentVector(double[] point, double[] dir) {
    d = new double[2][3];
    System.arraycopy(point, 0, d[0], 0, 4);
    System.arraycopy(dir, 0, d[1], 0, 4);
  }

  public SphericalTangentVector(double[][] coords) {
    d = new double[coords.length][4];
    for (int i = 0; i < coords.length; i++) {
      System.arraycopy(coords[i], 0, d[i], 0, 4);
    }
  }

  protected void rotate(double angle, int fromCoord, int toCoord) {
    double cos = Math.cos(angle);
    double sin = Math.sin(angle);
    for (int i = 0; i < 4; i++) {
      double buf = d[fromCoord][i];
      d[fromCoord][i] = cos * buf + sin * d[toCoord][i];
      d[toCoord][i] = -sin * buf + cos * d[toCoord][i];
    }
  }

  @Override
  public SphericalTangentVector move(double dist) {
    rotate(dist, 0, 1);
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
