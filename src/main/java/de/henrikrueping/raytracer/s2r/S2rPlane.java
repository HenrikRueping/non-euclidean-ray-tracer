package de.henrikrueping.raytracer.s2r;

import java.awt.Color;
import java.util.Optional;
import de.henrikrueping.raytracer.Entity;
import de.henrikrueping.raytracer.Helper;
import de.henrikrueping.raytracer.general.Intersection;

public class S2rPlane implements Entity<S2rTangentVector> {

  private S2rTangentFrame normal;
  private Color color;
  private boolean reflecting;

  public S2rPlane(S2rTangentFrame normal, Color color) {
    this(normal, color, false);
  }

  public S2rPlane(S2rTangentFrame normal, Color color, boolean reflecting) {
    super();
    this.normal = new S2rTangentFrame(normal);
    this.color = color;
    this.reflecting = reflecting;
  }

  @Override
  public Optional<Intersection<S2rTangentVector>> getFirstIntersection(S2rTangentVector hf) {
    double wanted = Helper.hypInnerProduct(normal.getPoint(), normal.getDirection());
    double pointn = Helper.hypInnerProduct(hf.getPoint(), normal.getDirection());
    double dirn = Helper.hypInnerProduct(hf.getDirection(), normal.getDirection());
    if (Math.abs(dirn) <= Math.abs(pointn))
      return Optional.empty();
    double l = Math.sqrt(dirn * dirn - pointn * pointn);
    double dist = (Helper.asinh(wanted / l) - Helper.asinh(pointn / l)) * Math.signum(dirn);

    S2rTangentVector intersectionPoint = new S2rTangentVector(hf).move(dist);
    return Optional.of(new Intersection<>(dist, this, intersectionPoint));
  }

  @Override
  public Color getColor(Intersection<S2rTangentVector> intersection) {
    return rescaleColor((float) Helper
        .hypInnerProduct(intersection.getTangentVector().getDirection(), normal.getDirection()),
        color);
  }

  @Override
  public boolean isReflecting() {
    return reflecting;
  }

  @Override
  public S2rTangentVector reflect(S2rTangentVector hitAt) {
    double ip = Helper.hypInnerProduct(hitAt.getDirection(), normal.getDirection());
    double[][] reflected = new double[2][4];
    System.arraycopy(hitAt.getPoint(), 0, reflected[0], 0, 4);
    for (int i = 0; i < 4; i++)
      reflected[1][i] = hitAt.getDirection()[i] - 2 * ip * normal.getDirection()[i];

    return new S2rTangentVector(reflected);
  }


}
