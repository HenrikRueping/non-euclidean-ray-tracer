package de.henrikrueping.raytracer.spherical;

import java.awt.Color;
import java.util.Optional;
import de.henrikrueping.raytracer.Entity;
import de.henrikrueping.raytracer.Helper;
import de.henrikrueping.raytracer.general.Intersection;

public class SphericalPlane implements Entity<SphericalTangentVector> {

  private SphericalTangentVector normal;
  private boolean isReflecting;
  private Color color;


  public SphericalPlane(SphericalTangentVector normal, boolean isReflecting, Color color) {
    super();
    this.normal = new SphericalTangentVector(normal);
    this.isReflecting = isReflecting;
    this.color = color;
  }

  @Override
  // Note that in spherical geometry any great circle intersects any plane;
  public Optional<Intersection<SphericalTangentVector>> getFirstIntersection(
      SphericalTangentVector hf) {
    double pn = Helper.innerProduct(hf.getPoint(), normal.getDirection());
    double vn = Helper.innerProduct(hf.getDirection(), normal.getDirection());
    double l = Math.sqrt(pn * pn + vn * vn);
    if (l == 0) // ray is in the plane.
      return Optional.empty();
    double dist = Math.acos(vn / l);
    return Optional.of(new Intersection<>(dist, this, new SphericalTangentVector(hf).move(dist)));
  }

  @Override
  public Color getColor(Intersection<SphericalTangentVector> intersection) {
    float ip = Math.min(1.0f, Math.abs((float) Helper
        .innerProduct(intersection.getTangentVector().getDirection(), normal.getDirection())));
    return new Color((color.getRed() / 255.0f) * ip, (color.getGreen() / 255.0f) * ip,
        (color.getBlue() / 255.0f) * ip);
  }

  @Override
  public boolean isReflecting() {
    return isReflecting;
  }

  @Override
  public SphericalTangentVector reflect(SphericalTangentVector hitAt) {
    double[][] newDir = new double[2][4];
    System.arraycopy(hitAt.d[0], 0, newDir[0], 0, 4);
    double ip = Helper.innerProduct(hitAt.getDirection(), this.normal.getDirection());
    for (int i = 0; i < 4; i++)
      newDir[1][i] = hitAt.getDirection()[i] - 2 * ip * normal.getDirection()[i];
    return new SphericalTangentVector(newDir);
  }


}
