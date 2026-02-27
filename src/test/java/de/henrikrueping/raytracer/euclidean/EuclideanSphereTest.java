package de.henrikrueping.raytracer.euclidean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.awt.Color;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import de.henrikrueping.raytracer.general.Intersection;

class EuclideanSphereTest {
  EuclideanScene scene;

  @BeforeEach
  void createScene() {
    scene = new EuclideanScene();
    scene.addSphere(0.2, Color.WHITE);
  }

  @Test
  @DisplayName("Sphere in front of me -> intersection exists")
  void SphereInFrontOfMe() {
    scene.move(-1);
    EuclideanTangentVector ray = scene.getFrame().obtainRayFor(0, 0);
    Optional<Intersection<EuclideanTangentVector>> optIntersection =
        scene.findFirstIntersection(ray, 10);
    assertTrue(optIntersection.isPresent());
    assertEquals(Color.WHITE, optIntersection.get().getColor());
  }

  @Test
  @DisplayName("Sphere behinde me -> no intersection ")
  void SphereBehindMe() {
    scene.move(1);
    EuclideanTangentVector ray = scene.getFrame().obtainRayFor(0, 0);
    assertTrue(scene.findFirstIntersection(ray, 10).isEmpty());
  }

  @Test
  @DisplayName("InsideOfSphere-> intersection exists")
  void insideOfSphere() {
    EuclideanTangentVector ray = scene.getFrame().obtainRayFor(0, 0);
    assertTrue(scene.findFirstIntersection(ray, 10).isPresent());
  }

  @Test
  @DisplayName("MissingSphere-> no intersection ")
  void RayNotIntersecting() {
    scene.move(1);
    scene.turnLeft(Math.PI / 2);
    EuclideanTangentVector ray = scene.getFrame().obtainRayFor(0, 0);
    assertTrue(scene.findFirstIntersection(ray, 10).isEmpty());
  }

}
