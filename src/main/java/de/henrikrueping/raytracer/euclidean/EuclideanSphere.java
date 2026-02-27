package de.henrikrueping.raytracer.euclidean;

import java.awt.Color;
import java.util.Optional;
import de.henrikrueping.raytracer.Entity;
import de.henrikrueping.raytracer.Helper;
import de.henrikrueping.raytracer.general.Intersection;

public class EuclideanSphere implements Entity<EuclideanTangentVector> {

  private EuclideanTangentFrame frame;
  private double radius;
  private Color color;

  public EuclideanSphere(EuclideanTangentFrame frame, double radius, Color color) {
    this.frame = new EuclideanTangentFrame(frame);
    this.radius = radius;
    this.color = color;
  }


  @Override
  public Optional<Intersection<EuclideanTangentVector>> getFirstIntersection(
      EuclideanTangentVector hf) {
    // (x+vt-c,x+vt-c) =R²
    // t² + 2(v,x-c)t +(x-c,x-c) = R²
    // (t+(v,x-c))² =R²+(v,x-c)²-(x-c,x-c)
    double[] xmc = new double[3];
    for (int i = 0; i < 3; i++)
      xmc[i] = hf.getPoint()[i] - frame.getPoint()[i];
    double vxmc = Helper.innerProduct(hf.getDirection(), xmc);
    double rhs = radius * radius + vxmc * vxmc - Helper.innerProduct(xmc, xmc);
    if (rhs < 0)
      return Optional.empty();
    double sqrhs = Math.sqrt(rhs);
    double dist;
    if (-vxmc - sqrhs > 0)
      dist = -vxmc - sqrhs;
    else if (-vxmc + sqrhs > 0)
      dist = -vxmc + sqrhs;
    else
      return Optional.empty();
    return Optional.of(new Intersection<>(dist, this, new EuclideanTangentVector(hf).move(dist)));
  }

  @Override
  public Color getColor(Intersection<EuclideanTangentVector> intersection) {
    // looking for d/dt dist(hf.move(t),c)|t=0
    // =d/dt (x-c+vt,x-c+vt)^0.5 |t=0
    // = 1/(2radius) d/dt (x-c+vt,x-c+vt)
    // 1/radius * (x-c,v);
    double ip = (Helper.innerProduct(intersection.getTangentVector().getPoint(),
        intersection.getTangentVector().getDirection())
        - Helper.innerProduct(frame.getPoint(), intersection.getTangentVector().getDirection()))
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
