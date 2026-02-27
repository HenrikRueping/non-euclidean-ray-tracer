package de.henrikrueping.raytracer;

import java.awt.Color;
import java.util.Optional;
import de.henrikrueping.raytracer.general.Intersection;
import de.henrikrueping.raytracer.general.TangentVector;

public interface Entity<TV extends TangentVector> {

  public Optional<Intersection<TV>> getFirstIntersection(TV hf);

  public Color getColor(Intersection<TV> intersection);

  public boolean isReflecting();

  public TV reflect(TV hitAt);

  default Color rescaleColor(float factor, Color color) {
    factor = Math.min(1f, Math.abs(factor));
    return new Color((color.getRed() / 255.0f) * factor, (color.getGreen() / 255.0f) * factor,
        (color.getBlue() / 255.0f) * factor);
  }
}
