package de.henrikrueping.raytracer.euclidean;

import java.awt.Color;
import java.util.Optional;
import de.henrikrueping.raytracer.Entity;
import de.henrikrueping.raytracer.Helper;
import de.henrikrueping.raytracer.general.Intersection;

public class EuclideanCylinder implements Entity<EuclideanTangentVector> {

  private EuclideanTangentFrame frame;
  private double length;
  private double radius;
  private Color color;

  public EuclideanCylinder(EuclideanTangentFrame frame, double length, double radius, Color color) {
    super();
    this.frame = new EuclideanTangentFrame(frame);
    this.length = length;
    this.radius = radius;
    this.color = color;
  }
  // the "inner product" that ignores the direction of the cylinder

  private double nonInnerProduct(double[] a, double[] b) {
    return Helper.innerProduct(a, b) - Helper.innerProduct(a, frame.getDirection())
        * Helper.innerProduct(b, frame.getDirection());
  }

  @Override
  public Optional<Intersection<EuclideanTangentVector>> getFirstIntersection(
      EuclideanTangentVector hf) {
    double[] xmc = new double[3];
    for (int i = 0; i < 3; i++)
      xmc[i] = hf.getPoint()[i] - frame.getPoint()[i];
    double v2 = nonInnerProduct(hf.getDirection(), hf.getDirection());
    if (v2 == 0)
      return Optional.empty();
    double vxmc = nonInnerProduct(xmc, hf.getDirection()) / v2;
    double xmc2 = nonInnerProduct(xmc, xmc) / v2;
    // (vt+x-c,vt+x-c)=radius²
    // t^2(v,v)+2t(v,x-c) +(x-c,x-c)=radius²
    double rhs = radius * radius / v2 - xmc2 + vxmc * vxmc;
    if (rhs < 0)
      return Optional.empty();
    double sqrhs = Math.sqrt(rhs);
    double dist, dirComp;
    double xdir = Helper.innerProduct(hf.getPoint(), frame.getDirection());
    double vdir = Helper.innerProduct(hf.getDirection(), frame.getDirection());
    double cdir = Helper.innerProduct(frame.getPoint(), frame.getDirection());
    if ((dist = -vxmc - sqrhs) > 0 && (dirComp = vdir * dist + xdir - cdir) >= 0
        && dirComp <= length) {
    } else if ((dist = -vxmc + sqrhs) > 0 && (dirComp = vdir * dist + xdir - cdir) >= 0
        && dirComp <= length) {
    } else
      return Optional.empty();
    // now we need to check the length
    return Optional.of(new Intersection<>(dist, this, new EuclideanTangentVector(hf).move(dist)));
  }

  @Override
  public Color getColor(Intersection<EuclideanTangentVector> intersection) {

    double ip = (nonInnerProduct(intersection.getTangentVector().getPoint(),
        intersection.getTangentVector().getDirection())
        - nonInnerProduct(frame.getPoint(), intersection.getTangentVector().getDirection()))
        / radius;
    return rescaleColor((float) ip, color);
  }

  @Override
  public boolean isReflecting() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public EuclideanTangentVector reflect(EuclideanTangentVector hitAt) {
    // TODO Auto-generated method stub
    return null;
  }

}
