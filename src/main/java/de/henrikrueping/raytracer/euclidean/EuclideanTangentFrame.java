package de.henrikrueping.raytracer.euclidean;

import de.henrikrueping.raytracer.Helper;
import de.henrikrueping.raytracer.general.TangentFrame;

public class EuclideanTangentFrame extends EuclideanTangentVector
    implements TangentFrame<EuclideanTangentVector> {

  public EuclideanTangentFrame(EuclideanTangentFrame toCopy) {
    super(toCopy.d);
  }

  public EuclideanTangentFrame() {
    super(new double[][] {{0.0, 0.0, 0.0}, {1.0, 0.0, 0.0}, {0.0, 1.0, 0.0}, {0.0, 0.0, 1.0}});
  }

  @Override
  public void strafeRight(double dist) {
    addMultipleOfCoord(dist, 2);
  }

  @Override
  public void strafeUp(double dist) {
    addMultipleOfCoord(dist, 3);
  }

  @Override
  public void turnLeft(double angle) {
    rotate(angle, 1, 2);
  }

  private void rotate(double angle, int fromCoord, int toCoord) {
    double cos = Math.cos(angle);
    double sin = Math.sin(angle);
    for (int i = 0; i < 3; i++) {
      double buf = d[fromCoord][i];
      d[fromCoord][i] = cos * buf + sin * d[toCoord][i];
      d[toCoord][i] = -sin * buf + cos * d[toCoord][i];
    }
  }

  @Override
  public void turnUp(double angle) {
    rotate(angle, 1, 3);
  }

  @Override
  public void pitch(double angle) {
    rotate(angle, 2, 3);

  }

  @Override
  public EuclideanTangentVector obtainRayFor(double d, double e) {
    double[][] ray = new double[2][3];
    System.arraycopy(this.d[0], 0, ray[0], 0, 3);
    for (int i = 0; i < 3; i++) {
      ray[1][i] = this.d[1][i] + d * this.d[2][i] + e * this.d[3][i];
    }
    double length = Math.sqrt(Helper.innerProduct(ray[1], ray[1]));
    for (int i = 0; i < 3; i++) {
      ray[1][i] /= length;
    }
    return new EuclideanTangentVector(ray);
  }

  @Override
  public double[] getRight() {
    return this.d[2];
  }

  @Override
  public double[] getDown() {
    return this.d[3];
  }

}
