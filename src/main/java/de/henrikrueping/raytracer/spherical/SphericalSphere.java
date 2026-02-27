package de.henrikrueping.raytracer.spherical;

import java.awt.Color;
import java.util.Optional;
import de.henrikrueping.raytracer.Entity;
import de.henrikrueping.raytracer.Helper;
import de.henrikrueping.raytracer.general.Intersection;

public class SphericalSphere implements Entity<SphericalTangentVector> {

  private SphericalTangentFrame frame;
  private double radius;
  private Color color;


  public SphericalSphere(SphericalTangentFrame frame, double radius, Color color) {
    super();
    this.frame = new SphericalTangentFrame(frame);
    this.radius = radius;
    this.color = color;
  }

  @Override
  public Optional<Intersection<SphericalTangentVector>> getFirstIntersection(
      SphericalTangentVector hf) {
    double cosr = Math.cos(radius);
    double hfxx = Helper.innerProduct(hf.getPoint(), frame.getPoint());
    double hfvx = Helper.innerProduct(hf.getDirection(), frame.getPoint());
    double length = Math.sqrt(hfxx * hfxx + hfvx * hfvx);
    if (Math.abs(cosr) > length)
      return Optional.empty();
    cosr /= length;
    hfxx /= length;
    hfvx /= length;

    // (cos(t) hf.x +sint *hf.v,frame.x)=cosr
    // cost *(hfx,x)+ sint*(hf.v,x) = cosr
    // cos(t+t2)=cos(r) für t2 =-signum(hf.v,x)*acos(hfx,x)
    double t2 = -Math.signum(hfvx) * Math.acos(hfxx);
    double first = ((Math.acos(cosr) - t2) % (2 * Math.PI) + 2 * Math.PI) % (2 * Math.PI);
    double second = ((Math.acos(cosr) - t2) % (2 * Math.PI) + 2 * Math.PI) % (2 * Math.PI);
    double dist = Math.min(first, second);
    return Optional.of(new Intersection<>(dist, this, new SphericalTangentVector(hf).move(dist)));
  }

  @Override
  public Color getColor(Intersection<SphericalTangentVector> intersection) {
    double[] normal = new double[4];
    double ip1 = Helper.innerProduct(frame.getPoint(), intersection.getTangentVector().getPoint());
    for (int i = 0; i < 4; i++)
      normal[i] = frame.getPoint()[i] - ip1 * intersection.getTangentVector().getPoint()[i];
    double length = Helper.innerProduct(normal, normal);
    for (int i = 0; i < 4; i++)
      normal[i] /= -length;
    return rescaleColor(
        (float) Helper.innerProduct(normal, intersection.getTangentVector().getDirection()), color);
  }

  @Override
  public boolean isReflecting() {
    return false;
  }

  @Override
  public SphericalTangentVector reflect(SphericalTangentVector hitAt) {
    return null;
  }
}
