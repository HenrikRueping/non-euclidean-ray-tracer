package de.henrikrueping.raytracer.hyperbolic;

import java.awt.Color;
import java.util.Optional;
import de.henrikrueping.raytracer.Entity;
import de.henrikrueping.raytracer.Helper;
import de.henrikrueping.raytracer.general.Intersection;

public class HyperbolicPlane implements Entity<HyperbolicTangentVector> {

  private HyperbolicFrame normal;
  private Color color;
  private boolean reflecting;

  public HyperbolicPlane(HyperbolicFrame normal, Color color) {
    this(normal, color, false);
  }

  public HyperbolicPlane(HyperbolicFrame normal, Color color, boolean reflecting) {
    super();
    this.normal = new HyperbolicFrame(normal);
    this.color = color;
    this.reflecting = reflecting;
  }

  @Override
  public Optional<Intersection<HyperbolicTangentVector>> getFirstIntersection(
      HyperbolicTangentVector hf) {
    double wanted = Helper.hypInnerProduct(normal.getPoint(), normal.getDirection());
    double pointn = Helper.hypInnerProduct(hf.getPoint(), normal.getDirection());
    double dirn = Helper.hypInnerProduct(hf.getDirection(), normal.getDirection());
    if (Math.abs(dirn) <= Math.abs(pointn))
      return Optional.empty();
    double l = Math.sqrt(dirn * dirn - pointn * pointn);
    double dist = (Helper.asinh(wanted / l) - Helper.asinh(pointn / l)) * Math.signum(dirn);

    HyperbolicTangentVector intersectionPoint = new HyperbolicTangentVector(hf).move(dist);
    return Optional.of(new Intersection<>(dist, this, intersectionPoint));
  }

  @Override
  public Color getColor(Intersection<HyperbolicTangentVector> intersection) {
    return rescaleColor((float) Helper
        .hypInnerProduct(intersection.getTangentVector().getDirection(), normal.getDirection()),
        color);
  }

  @Override
  public boolean isReflecting() {
    return reflecting;
  }

  @Override
  public HyperbolicTangentVector reflect(HyperbolicTangentVector hitAt) {
    double ip = Helper.hypInnerProduct(hitAt.getDirection(), normal.getDirection());
    double[][] reflected = new double[2][4];
    System.arraycopy(hitAt.getPoint(), 0, reflected[0], 0, 4);
    for (int i = 0; i < 4; i++)
      reflected[1][i] = hitAt.getDirection()[i] - 2 * ip * normal.getDirection()[i];

    return new HyperbolicTangentVector(reflected);
  }


}
