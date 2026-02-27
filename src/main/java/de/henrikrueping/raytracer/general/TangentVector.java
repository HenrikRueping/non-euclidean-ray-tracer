package de.henrikrueping.raytracer.general;

public interface TangentVector {

  TangentVector move(double dist);

  double[] getPoint();

  double[] getDirection();

}
