package de.henrikrueping.raytracer.spherical;

import de.henrikrueping.raytracer.Helper;
import de.henrikrueping.raytracer.general.TangentFrame;

public class SphericalTangentFrame extends SphericalTangentVector
    implements TangentFrame<SphericalTangentVector> {

  public SphericalTangentFrame() {
    super(new double[][] {{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}});
  }

  public SphericalTangentFrame(SphericalTangentFrame toCopy) {
    super(toCopy.d);
  }

  @Override
  public void strafeRight(double dist) {
    rotate(dist, 0, 2);
  }

  @Override
  public void strafeUp(double dist) {
    rotate(dist, 0, 3);

  }

  @Override
  public void turnLeft(double angle) {
    rotate(angle, 1, 2);
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
  public SphericalTangentVector obtainRayFor(double d, double e) {
    double[][] ray = new double[2][4];
    System.arraycopy(this.d[0], 0, ray[0], 0, 4);
    for (int i = 0; i < 4; i++) {
      ray[1][i] = this.d[1][i] + d * this.d[2][i] + e * this.d[3][i];
    }
    double length = Math.sqrt(Helper.innerProduct(ray[1], ray[1]));
    for (int i = 0; i < 4; i++) {
      ray[1][i] /= length;
    }
    return new SphericalTangentVector(ray);
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
