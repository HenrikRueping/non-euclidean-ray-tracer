package de.henrikrueping.raytracer.spherical;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import de.henrikrueping.raytracer.Entity;
import de.henrikrueping.raytracer.Helper;
import de.henrikrueping.raytracer.general.Intersection;

public class SphericalCylinder implements Entity<SphericalTangentVector> {

  private SphericalTangentFrame frame;
  private double length;
  private double radius;
  private Color color;

  public SphericalCylinder(SphericalTangentFrame frame, double length, double radius, Color color) {
    super();
    this.frame = new SphericalTangentFrame(frame);
    this.length = length;
    this.radius = radius;
    this.color = color;
  }

  private double mod2PI(double t) {
    // tricky to make sure its always positive
    return (t % (Math.PI * 2) + Math.PI * 2) % (Math.PI * 2);
  }

  private double getHeightInCylinder(SphericalTangentVector tv) {
    double c = Helper.innerProduct(tv.getPoint(), frame.getPoint());
    double s = Helper.innerProduct(tv.getPoint(), frame.getDirection());
    double l = Math.sqrt(c * c + s * s);
    if (l == 0)
      return Double.NaN;
    c /= l;
    double height = s >= 0 ? Math.acos(c) : Math.PI * 2 - Math.acos(c);
    return height;
  }

  @Override
  public Optional<Intersection<SphericalTangentVector>> getFirstIntersection(
      SphericalTangentVector hf) {
    double hfx2 = Helper.innerProduct(hf.getPoint(), frame.getRight());
    double hfx3 = Helper.innerProduct(hf.getPoint(), frame.getDown());
    double hfv2 = Helper.innerProduct(hf.getDirection(), frame.getRight());
    double hfv3 = Helper.innerProduct(hf.getDirection(), frame.getDown());
    // (cos t hfx +sint hfv,v2)^2+(cos t hfx +sint hfv,v3)^2 = cos(radius)^2
    // cost^2 *(hfx,v2)^2+ 2 sint cost hfx2*hfv2 + sint^2*hfv2 + ...v3... =cos(radius)^2
    // of the form a cos²t + b sint cost +c sin²t = R
    double a = hfx2 * hfx2 + hfx3 * hfx3;
    double b = 2 * (hfx2 * hfv2 + hfx3 * hfv3);
    double c = hfv2 * hfv2 + hfv3 * hfv3;
    double R = Math.sin(radius) * Math.sin(radius);
    // a/4(e^ix+e^-ix)^2 + b/4i (e^ix-e^-ix) (e^ix+e^-ix) -c/4 (e^ix-e^-ix)² = R
    // (a-c)/4(e^i2x+e^-i2x)+b/4i(e^i2x-e^-i2x)) =R-a/2-c/2
    // (a-c)/2L cos2x + b/2L sin2x = (R - a/2 - c/2)/L

    double A = (a - c) / 2;
    double B = b / 2;
    double L = Math.sqrt(A * A + B * B);
    double R2 = R - a / 2 - c / 2;
    if (L == 0 || Math.abs(R2 = R2 / L) > 1)
      return Optional.empty();
    A /= L;
    B /= L;
    double T2 = -Math.acos(A);
    if (B < 0)
      T2 = -T2;
    double acosR2 = Math.acos(R2);
    // cos(2x+T2) = R2
    // (2x +T2) = +–acos(R2)
    // x= (acos(R2)-T2)/2,(-acos(R2)-T2)/2,PI+(+acos(R2)-T2)/2,PI+(-acos(R2)-T2)/2
    List<Double> candidates =
        new ArrayList<>(List.of(mod2PI((-acosR2 - T2) / 2), mod2PI((acosR2 - T2) / 2),
            mod2PI(Math.PI + (-acosR2 - T2) / 2), mod2PI(Math.PI + (acosR2 - T2) / 2)));
    candidates.sort(Comparator.naturalOrder());
    for (double t : candidates) {
      double shouldBeSmall = a * Math.cos(t) * Math.cos(t) + b * Math.cos(t) * Math.sin(t)
          + c * Math.sin(t) * Math.sin(t) - R;
    }
    // now we need to find the first intersection where the intersection really lies in the length
    // of the cylinder.
    Optional<Double> optFirstDist = candidates.stream()
        .filter(t -> getHeightInCylinder(new SphericalTangentVector(hf).move(t)) <= length)
        .findFirst();
    if (optFirstDist.isEmpty()) {
      return Optional.empty();
    }
    double t = optFirstDist.get();



    return Optional.of(new Intersection<>(optFirstDist.get(), this,
        new SphericalTangentVector(hf).move(optFirstDist.get())));
  }

  @Override
  public Color getColor(Intersection<SphericalTangentVector> intersection) {
    double s1 =
        Helper.innerProduct(intersection.getTangentVector().getDirection(), frame.getRight());
    double s2 =
        Helper.innerProduct(intersection.getTangentVector().getDirection(), frame.getDown());
    float ip = (float) Math.sqrt(s1 * s1 + s2 * s2);
    return rescaleColor(ip, color);
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
