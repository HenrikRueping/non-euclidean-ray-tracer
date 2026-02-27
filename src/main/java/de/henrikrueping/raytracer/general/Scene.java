package de.henrikrueping.raytracer.general;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import de.henrikrueping.raytracer.Entity;
import de.henrikrueping.raytracer.hyperbolic.HyperbolicSphere;

public abstract class Scene<TV extends TangentVector, TF extends TangentFrame<TV>> {

  private TF frame;
  private List<Entity<TV>> entities = new ArrayList<>();
  private Color backgroundColor = Color.BLACK;

  public Scene(TF frame) {
    super();
    this.frame = frame;
  }



  private <X> X log(X x) {
    if (x instanceof Intersection i && i.getEntity() instanceof HyperbolicSphere) {
      System.out.println(x);
      System.out.println(i.getEntity().isReflecting());
    }

    return x;
  }

  public Color getColor(TV vector, double maxDist) {
    Optional<Intersection<TV>> optIntersection = findFirstIntersection(vector, maxDist);
    return optIntersection.isPresent() ? optIntersection.get().getColor() : backgroundColor;
  }



  public Optional<Intersection<TV>> findFirstIntersection(TV vector, double maxDist) {
    Optional<Intersection<TV>> optIntersection = Optional.empty();
    double distRemaining = maxDist;
    TV currentVector = vector;
    while (vector != null) {
      final double dist = distRemaining;
      final TV vector2 = currentVector;
      optIntersection = entities.stream().map(e -> e.getFirstIntersection(vector2))// .map(this::log)
          .filter(Optional::isPresent).map(Optional::get)// .map(this::log)
          .filter(i -> i.getDist() > 0.00001 && i.getDist() < dist).sorted().findFirst();
      if (optIntersection.isEmpty())
        return Optional.empty();
      Intersection<TV> intersection = optIntersection.get();
      if (!intersection.getEntity().isReflecting())
        break;
      currentVector = intersection.getEntity().reflect(intersection.getTangentVector());
      distRemaining -= intersection.getDist();
    }
    return optIntersection;
  }

  public void strafeRight(double dist) {
    frame.strafeRight(dist);
  }

  public void strafeUp(double dist) {
    frame.strafeUp(dist);
  }

  public void move(double dist) {
    frame.move(dist);
  }

  public void turnLeft(double angle) {
    frame.turnLeft(angle);
  }

  public void pitch(double angle) {
    frame.pitch(angle);
  }

  public void turnUp(double angle) {
    frame.turnUp(angle);
  }

  public TF getFrame() {
    return frame;
  }

  public void setFrame(TF frame) {
    this.frame = frame;
  }

  public void addEntity(Entity<TV> e) {
    this.entities.add(e);
  }

  public abstract void teleportTo(TF frame);

  public void setBackgroundColor(Color backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  public abstract void addSphere(double radius, Color color);

  public abstract void addPlane(Color color);

  public abstract void addReflectingPlane(Color color);

  public abstract void addCylinder(double length, double radius, Color color);

}
