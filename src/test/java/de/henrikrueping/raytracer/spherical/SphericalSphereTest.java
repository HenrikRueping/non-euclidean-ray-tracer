package de.henrikrueping.raytracer.spherical;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.awt.Color;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import de.henrikrueping.raytracer.general.Intersection;

class SphericalSphereTest {

  SphericalScene scene;

  @BeforeEach
  void createScene() {
    scene = new SphericalScene();
    scene.addSphere(0.2, Color.WHITE);
  }

  @Test
  @DisplayName("Sphere in front of me -> intersection exists")
  void SphereInFrontOfMe() {
    scene.move(-1);
    SphericalTangentVector ray = scene.getFrame().obtainRayFor(0, 0);
    Optional<Intersection<SphericalTangentVector>> optIntersection =
        scene.findFirstIntersection(ray, 10);
    assertTrue(optIntersection.isPresent());
    assertEquals(Color.WHITE, optIntersection.get().getColor());
  }

  @Test
  @DisplayName("Sphere behinde me -> intersection (around the world)")
  void SphereBehindMe() {
    scene.move(1);
    SphericalTangentVector ray = scene.getFrame().obtainRayFor(0, 0);
    assertTrue(scene.findFirstIntersection(ray, 10).isPresent());
  }

  @Test
  @DisplayName("InsideOfSphere-> intersection exists")
  void insideOfSphere() {
    SphericalTangentVector ray = scene.getFrame().obtainRayFor(0, 0);
    assertTrue(scene.findFirstIntersection(ray, 10).isPresent());
  }

  @Test
  @DisplayName("MissingSphere-> no intersection ")
  void RayNotIntersecting() {
    scene.move(1);
    scene.turnLeft(Math.PI / 2);
    SphericalTangentVector ray = scene.getFrame().obtainRayFor(0, 0);
    assertTrue(scene.findFirstIntersection(ray, 10).isEmpty());
  }
}
