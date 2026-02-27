package de.henrikrueping.raytracer.h2r;

import java.awt.Color;
import java.util.Optional;
import de.henrikrueping.raytracer.Entity;
import de.henrikrueping.raytracer.general.Intersection;

public class H2rCylinder implements Entity<H2rTangentVector> {

  private H2rTangentFrame position;
  private double length, radius;
  private Color color;
  private boolean reflecting;


  public H2rCylinder(H2rTangentFrame position, double length, double radius, Color color,
      boolean reflecting) {
    super();
    this.position = new H2rTangentFrame(position);
    this.length = length;
    this.radius = radius;
    this.color = color;
    this.reflecting = reflecting;
  }

  public H2rCylinder(H2rTangentFrame position, double length, double radius, Color color) {
    this(position, length, radius, color, false);
  }

  @Override
  public Optional<Intersection<H2rTangentVector>> getFirstIntersection(H2rTangentVector hf) {
    // // min t' d(hf.move(t),position.move(t')) =cosh(radius) && 0<=t'<=length
    // // min t' -B(cosh(t)hf.x + sinh(t)hf.v,cosh(t')pos.x + sinh(t')pos.v)
    // // Find t':
    // // 0 = -sinh(t')B (cosh(t)hf.x + sinh(t)hf.v,pos.x)-cosh(t')B(cosh(t)hf.x + sinh(t)hf.v,
    // pos.v)
    // // tanht2 = - B(cosh(t)hf.x + sinh(t)hf.v, pos.v)/B(cosh(t)hf.x + sinh(t)hf.v,pos.x);
    // double posRight = Helper.hypInnerProduct(hf.getPoint(), position.getRight());
    // double dirRight = Helper.hypInnerProduct(hf.getDirection(), position.getRight());
    // double posDown = Helper.hypInnerProduct(hf.getPoint(), position.getDown());
    // double dirDown = Helper.hypInnerProduct(hf.getDirection(), position.getDown());
    // double a = posRight * posRight + posDown * posDown;
    // double b = 2 * posRight * dirRight + 2 * posDown * dirDown;
    // double c = dirRight * dirRight + dirDown * dirDown;
    // // we need to solve
    // // a*cosh(t)^2+bcosh(t)sinh(t)+c*sinh(t)^2=sinh(radius)^2
    // // <=> (a+c)/2 cosh(2t) +b/2 sinh(2t) =sinh(radius)^2 + (c-a)/2
    // double length = Math.sqrt((a + c) * (a + c) / 4 - b * b / 4);
    // double sinht2 = b / 2 / length;
    // double t2 = Math.log(sinht2 + Math.sqrt(sinht2 * sinht2 + 1));
    // // cosh(2t + t2)= (cosh(radius)+(c-a)/2)/length
    // double cosh2tt2 = (Math.sinh(radius) * Math.sinh(radius) + (c - a) / 2) / length;
    // if (cosh2tt2 < 1)
    // return Optional.empty();
    // double twott2 = Math.log(cosh2tt2 + Math.sqrt(cosh2tt2 * cosh2tt2 - 1));
    //
    // double posDir = Helper.hypInnerProduct(hf.getPoint(), position.getDirection());
    // double dirDir = Helper.hypInnerProduct(hf.getDirection(), position.getDirection());
    // double dist;
    // if ((dist = (-twott2 - t2) / 2) > 0) {
    // double sinhl = Math.cosh(dist) * posDir + Math.sinh(dist) * dirDir;
    // if (sinhl >= 0 && sinhl <= Math.sinh(this.length))
    // return Optional.of(new Intersection<>(dist, this, new H2rTangentVector(hf).move(dist)));
    // }
    // if ((dist = (twott2 - t2) / 2) > 0) {
    // double sinhl = Math.cosh(dist) * posDir + Math.sinh(dist) * dirDir;
    // if (sinhl >= 0 && sinhl <= Math.sinh(this.length))
    // return Optional.of(new Intersection<>(dist, this, new H2rTangentVector(hf).move(dist)));
    // }
    // return Optional.empty();
    return null;
  }

  @Override
  public Color getColor(Intersection<H2rTangentVector> intersection) {
    // double skaldown =
    // Helper.hypInnerProduct(intersection.getTangentVector().getPoint(), this.position.getDown());
    // double skalright = Helper.hypInnerProduct(intersection.getTangentVector().getPoint(),
    // this.position.getRight());
    // double length = Math.sqrt(skaldown * skaldown + skalright * skalright);
    // skaldown /= length;
    // skalright /= length;
    // float factor = (float)
    // ((Helper.hypInnerProduct(intersection.getTangentVector().getDirection(),
    // this.position.getDown()) * skaldown
    // + Helper.hypInnerProduct(intersection.getTangentVector().getDirection(),
    // this.position.getRight()) * skalright)
    // / Math.cosh(radius));
    // return rescaleColor(factor, color);
    return null;
  }

  @Override
  public boolean isReflecting() {
    return reflecting;
  }

  @Override
  public H2rTangentVector reflect(H2rTangentVector hitAt) {
    return null;
  }
}
