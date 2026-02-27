package de.henrikrueping.raytracer.general;

import java.awt.Color;
import java.util.Comparator;
import de.henrikrueping.raytracer.Entity;

public class Intersection<TV extends TangentVector> implements Comparable<Intersection<TV>> {
  private static final Comparator<Intersection<?>> COMPARATOR =
      Comparator.comparing(Intersection::getDist);


  public double dist;
  public Entity<TV> entity;
  public TV tangentVector;

  public Intersection(double dist, Entity<TV> entity, TV tangentVector) {
    super();
    this.dist = dist;
    this.entity = entity;
    this.tangentVector = tangentVector;
  }

  @Override
  public int compareTo(Intersection<TV> o) {
    return COMPARATOR.compare(this, o);
  }

  public double getDist() {
    return dist;
  }

  public TV getTangentVector() {
    return tangentVector;
  }

  public Color getColor() {
    return entity.getColor(this);
  }

  public Entity<TV> getEntity() {
    return entity;
  }

  @Override
  public String toString() {
    return "Intersection [dist=" + dist + ", entity=" + entity.getClass().getSimpleName()
        + ", tangentVector=" + tangentVector + "]";
  }


}
