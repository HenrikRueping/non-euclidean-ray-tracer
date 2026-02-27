package de.henrikrueping.raytracer.sol;

import de.henrikrueping.raytracer.euclidean.EuclideanTangentVector;
import de.henrikrueping.raytracer.general.TangentFrame;
import de.henrikrueping.raytracer.general.TangentVector;

public class SolTangentFrame extends SolTangentVector
    implements TangentFrame<EuclideanTangentVector> {

  @Override
  public TangentVector move(double dist) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public double[] getPoint() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public double[] getDirection() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void strafeRight(double dist) {
    // TODO Auto-generated method stub

  }

  @Override
  public void strafeUp(double dist) {
    // TODO Auto-generated method stub

  }

  @Override
  public void turnLeft(double angle) {
    // TODO Auto-generated method stub

  }

  @Override
  public void turnUp(double angle) {
    // TODO Auto-generated method stub

  }

  @Override
  public void pitch(double angle) {
    // TODO Auto-generated method stub

  }

  @Override
  public EuclideanTangentVector obtainRayFor(double d, double e) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public double[] getRight() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public double[] getDown() {
    // TODO Auto-generated method stub
    return null;
  }

}
