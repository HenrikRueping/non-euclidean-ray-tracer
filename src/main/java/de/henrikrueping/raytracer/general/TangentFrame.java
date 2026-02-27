package de.henrikrueping.raytracer.general;

public interface TangentFrame<TV extends TangentVector> extends TangentVector {

  void strafeRight(double dist);

  void strafeUp(double dist);

  void turnLeft(double angle);

  void turnUp(double angle);

  void pitch(double angle);

  TV obtainRayFor(double d, double e);

  double[] getRight();

  double[] getDown();

}
