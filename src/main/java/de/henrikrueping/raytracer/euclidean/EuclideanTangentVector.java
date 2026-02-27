package de.henrikrueping.raytracer.euclidean;

import de.henrikrueping.raytracer.general.TangentVector;

public class EuclideanTangentVector implements TangentVector {
  protected double[][] d;

  public EuclideanTangentVector() {
    this.d = new double[][] {{0, 0, 0}, {1, 0, 0}};
  }

  public EuclideanTangentVector(EuclideanTangentVector toCopy) {
    this(toCopy.d);
  }

  public EuclideanTangentVector(double[] point, double[] dir) {
    d = new double[2][3];
    System.arraycopy(point, 0, d[0], 0, 3);
    System.arraycopy(dir, 0, d[1], 0, 3);
  }

  public EuclideanTangentVector(double[][] coords) {
    d = new double[coords.length][3];
    for (int i = 0; i < coords.length; i++) {
      System.arraycopy(coords[i], 0, d[i], 0, 3);
    }
  }

  @Override
  public EuclideanTangentVector move(double dist) {
    addMultipleOfCoord(dist, 1);
    return this;
  }

  protected void addMultipleOfCoord(double dist, int fromCoord) {
    for (int i = 0; i < 3; i++) {
      d[0][i] += d[fromCoord][i] * dist;
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
