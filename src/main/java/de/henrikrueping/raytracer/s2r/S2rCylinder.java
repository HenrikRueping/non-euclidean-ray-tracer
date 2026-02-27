package de.henrikrueping.raytracer.s2r;

import java.awt.Color;
import java.util.Optional;
import de.henrikrueping.raytracer.Entity;
import de.henrikrueping.raytracer.general.Intersection;

public class S2rCylinder implements Entity<S2rTangentVector> {

  private S2rTangentFrame position;
  private double length, radius;
  private Color color;
  private boolean reflecting;


  public S2rCylinder(S2rTangentFrame position, double length, double radius, Color color,
      boolean reflecting) {
    super();
    this.position = new S2rTangentFrame(position);
    this.length = length;
    this.radius = radius;
    this.color = color;
    this.reflecting = reflecting;
  }

  public S2rCylinder(S2rTangentFrame position, double length, double radius, Color color) {
    this(position, length, radius, color, false);
  }

  @Override
  public Optional<Intersection<S2rTangentVector>> getFirstIntersection(S2rTangentVector hf) {
    return null;
  }

  @Override
  public Color getColor(Intersection<S2rTangentVector> intersection) {
    return null;
  }

  @Override
  public boolean isReflecting() {
    return reflecting;
  }

  @Override
  public S2rTangentVector reflect(S2rTangentVector hitAt) {
    return null;
  }
}
