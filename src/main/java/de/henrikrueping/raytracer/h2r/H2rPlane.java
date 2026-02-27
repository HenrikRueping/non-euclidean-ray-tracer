package de.henrikrueping.raytracer.h2r;

import java.awt.Color;
import java.util.Optional;
import de.henrikrueping.raytracer.Entity;
import de.henrikrueping.raytracer.Helper;
import de.henrikrueping.raytracer.general.Intersection;

public class H2rPlane implements Entity<H2rTangentVector> {

  private H2rTangentFrame normal;
  private Color color;
  private boolean reflecting;

  public H2rPlane(H2rTangentFrame normal, Color color) {
    this(normal, color, false);
  }

  public H2rPlane(H2rTangentFrame normal, Color color, boolean reflecting) {
    super();
    this.normal = new H2rTangentFrame(normal);
    this.color = color;
    this.reflecting = reflecting;
  }

  @Override
  public Optional<Intersection<H2rTangentVector>> getFirstIntersection(H2rTangentVector hf) {
    // double wanted = Helper.hypInnerProduct(normal.getPoint(), normal.getDirection());
    // double pointn = Helper.hypInnerProduct(hf.getPoint(), normal.getDirection());
    // double dirn = Helper.hypInnerProduct(hf.getDirection(), normal.getDirection());
    // if (Math.abs(dirn) <= Math.abs(pointn))
    // return Optional.empty();
    // double l = Math.sqrt(dirn * dirn - pointn * pointn);
    // double dist = (Helper.asinh(wanted / l) - Helper.asinh(pointn / l)) * Math.signum(dirn);
    //
    // H2rTangentVector intersectionPoint = new H2rTangentVector(hf).move(dist);
    // return Optional.of(new Intersection<>(dist, this, intersectionPoint));
    return null;
  }

  @Override
  public Color getColor(Intersection<H2rTangentVector> intersection) {
    return rescaleColor((float) Helper
        .hypInnerProduct(intersection.getTangentVector().getDirection(), normal.getDirection()),
        color);
  }

  @Override
  public boolean isReflecting() {
    return reflecting;
  }

  @Override
  public H2rTangentVector reflect(H2rTangentVector hitAt) {
    // double ip = Helper.hypInnerProduct(hitAt.getDirection(), normal.getDirection());
    // double[][] reflected = new double[2][4];
    // System.arraycopy(hitAt.getPoint(), 0, reflected[0], 0, 4);
    // for (int i = 0; i < 4; i++)
    // reflected[1][i] = hitAt.getDirection()[i] - 2 * ip * normal.getDirection()[i];
    //
    // return new H2rTangentVector(reflected);
    return null;
  }


}
