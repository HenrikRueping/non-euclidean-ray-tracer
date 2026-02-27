package de.henrikrueping.raytracer.spherical;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.awt.Color;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import de.henrikrueping.raytracer.general.Intersection;

class SphericalPlaneTest {
  @Nested
  @DisplayName("generic Tests")
  class Generic {
    SphericalScene scene;

    @BeforeEach
    void createScene() {
      scene = new SphericalScene();
      scene.addPlane(Color.WHITE);
    }

    // in spherical geometry, any ray hits any plane...
    @Test
    @DisplayName("Plane in front of me -> intersection exists")
    void PlaneInFrontOfMe() {
      scene.move(-1);
      SphericalTangentVector ray = scene.getFrame().obtainRayFor(0, 0);
      Optional<Intersection<SphericalTangentVector>> optIntersection =
          scene.findFirstIntersection(ray, 10);
      assertTrue(optIntersection.isPresent());
      assertEquals(Color.WHITE, optIntersection.get().getColor());
    }

    // in spherical geometry, any ray hits any plane...
    @Test
    @DisplayName("ray inside the plane -> no intersection ")
    @Disabled
    // dont know how to exactly get 0...
    void PlanseInFrontOfMe() {
      scene.move(-1);
      SphericalTangentVector ray = scene.getFrame().obtainRayFor(0, 0);
      Optional<Intersection<SphericalTangentVector>> optIntersection =
          scene.findFirstIntersection(ray, 10);
      assertTrue(optIntersection.isPresent());
      assertEquals(Color.WHITE, optIntersection.get().getColor());
    }
  }

  @Nested
  @DisplayName("Reflecting Planes")
  class Reflections {
    @Test
    void planeHidingThings() {
      SphericalScene scene = new SphericalScene();
      scene.addSphere(0.2, Color.WHITE);
      scene.move(-.4);
      scene.addReflectingPlane(Color.WHITE);
      scene.move(-.4);
      SphericalTangentVector ray = scene.getFrame().obtainRayFor(0, 0);
      assertTrue(scene.findFirstIntersection(ray, 10).isEmpty());
    }

    @Test
    void reflectionHitsThings() {
      SphericalScene scene = new SphericalScene();
      scene.addSphere(0.2, Color.WHITE);
      scene.move(.8);
      scene.addReflectingPlane(Color.WHITE);
      scene.move(-.4);
      SphericalTangentVector ray = scene.getFrame().obtainRayFor(0, 0);
      assertTrue(scene.findFirstIntersection(ray, 10).isPresent());
    }

    @Test
    void reflectingAtAnAngle() {
      SphericalScene scene = new SphericalScene();
      scene.addSphere(0.2, Color.WHITE);
      scene.move(.8);
      scene.turnLeft(Math.PI / 4);
      scene.addReflectingPlane(Color.WHITE);
      scene.turnLeft(Math.PI / 4);
      scene.move(-.4);
      SphericalTangentVector ray = scene.getFrame().obtainRayFor(0, 0);
      assertTrue(scene.findFirstIntersection(ray, 10).isPresent());
    }
  }
}
