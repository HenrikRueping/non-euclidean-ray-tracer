package de.henrikrueping.raytracer.euclidean;

import java.awt.Color;
import java.util.Optional;
import de.henrikrueping.raytracer.Entity;
import de.henrikrueping.raytracer.Helper;
import de.henrikrueping.raytracer.general.Intersection;

public class EuclideanPlane implements Entity<EuclideanTangentVector> {
  private EuclideanTangentVector normal;
  private Color color;
  private boolean isReflecting;

  public EuclideanPlane(EuclideanTangentVector normal, boolean isReflecting, Color color) {
    super();
    this.normal = new EuclideanTangentVector(normal);
    this.isReflecting = isReflecting;
    this.color = color;
  }

  @Override
  public Optional<Intersection<EuclideanTangentVector>> getFirstIntersection(
      EuclideanTangentVector hf) {
    double a = Helper.innerProduct(normal.getDirection(), hf.getDirection());
    double b = Helper.innerProduct(normal.getDirection(), normal.getPoint())
        - Helper.innerProduct(normal.getDirection(), hf.getPoint());
    if (a == 0)
      return Optional.empty();
    return Optional.of(new Intersection<>(b / a, this, new EuclideanTangentVector(hf).move(b / a)));
  }

  @Override
  public Color getColor(Intersection<EuclideanTangentVector> intersection) {
    float ip = (float) Helper.innerProduct(intersection.getTangentVector().getDirection(),
        normal.getDirection());
    return rescaleColor(ip, color);
  }

  @Override
  public boolean isReflecting() {
    return isReflecting;
  }

  @Override
  public EuclideanTangentVector reflect(EuclideanTangentVector hitAt) {
    double[][] reflected = new double[2][3];
    System.arraycopy(hitAt.getPoint(), 0, reflected[0], 0, 3);
    double ip = Helper.innerProduct(hitAt.getDirection(), normal.getDirection());
    for (int i = 0; i < 3; i++)
      reflected[1][i] = hitAt.getDirection()[i] - 2 * ip * normal.getDirection()[i];
    return new EuclideanTangentVector(reflected);
  }
}
