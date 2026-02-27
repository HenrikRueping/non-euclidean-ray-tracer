package de.henrikrueping.raytracer.hyperbolic;

import java.awt.Color;
import java.util.Optional;
import de.henrikrueping.raytracer.Entity;
import de.henrikrueping.raytracer.Helper;
import de.henrikrueping.raytracer.general.Intersection;

public class HyperbolicSphere implements Entity<HyperbolicTangentVector> {

  public HyperbolicFrame center;
  public double radius;

  private Color color;
  private boolean reflecting;

  public HyperbolicSphere(HyperbolicFrame center, double radius, Color color) {
    this(center, radius, color, false);
  }

  public HyperbolicSphere(HyperbolicFrame center, double radius, Color color, boolean reflecting) {
    super();
    this.center = new HyperbolicFrame(center);
    this.radius = radius;
    this.color = color;
    this.reflecting = reflecting;
  }

  @Override
  public Optional<Intersection<HyperbolicTangentVector>> getFirstIntersection(
      HyperbolicTangentVector hf) {
    double basec = Helper.hypInnerProduct(hf.getPoint(), center.getPoint());
    double dirc = Helper.hypInnerProduct(hf.getDirection(), center.getPoint());
    // cosh(t) basec+sinh(t) dirc = -cosh(r)
    if (Math.abs(basec) <= Math.abs(dirc))
      // I do not know how to enter this. Maybe this is impossible
      return Optional.empty();
    double l = Math.sqrt(basec * basec - dirc * dirc);
    double coshRadius = Math.cosh(radius);
    if (coshRadius <= l)
      return Optional.empty();
    double dist1 = Helper.acosh(coshRadius / l);
    double dist2 = Helper.asinh(dirc / l);
    double dist;
    if (-dist1 + dist2 > 0)
      dist = -dist1 + dist2;
    else if (dist1 + dist2 > 0)
      dist = dist1 + dist2;
    else
      return Optional.empty();
    return Optional.of(new Intersection<>(dist, this, new HyperbolicTangentVector(hf).move(dist)));
  }

  @Override
  public Color getColor(Intersection<HyperbolicTangentVector> intersection) {
    double[] tang = new double[4];
    double ip1 =
        Helper.hypInnerProduct(intersection.getTangentVector().getPoint(), center.getPoint());

    for (int i = 0; i < 4; i++)
      tang[i] = ip1 * intersection.getTangentVector().getPoint()[i] + center.getPoint()[i];
    ip1 = Math.sqrt(Helper.hypInnerProduct(tang, tang));
    for (int i = 0; i < 4; i++)
      tang[i] = tang[i] / ip1;
    return rescaleColor(
        (float) Helper.hypInnerProduct(intersection.getTangentVector().getDirection(), tang),
        color);
  }

  @Override
  public boolean isReflecting() {
    return reflecting;
  }

  @Override
  public HyperbolicTangentVector reflect(HyperbolicTangentVector hitAt) {
    // TODO
    return null;
  }

}
