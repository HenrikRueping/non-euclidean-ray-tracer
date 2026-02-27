package de.henrikrueping.raytracer.euclidean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.awt.Color;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import de.henrikrueping.raytracer.general.Intersection;

class EuclideanPlaneTest {
  @Nested
  @DisplayName("generic Tests")
  class Generic {
    EuclideanScene scene;

    @BeforeEach
    void createScene() {
      scene = new EuclideanScene();
      scene.addPlane(Color.WHITE);
    }

    @Test
    @DisplayName("Plane in front of me -> intersection exists")
    void PlaneInFrontOfMe() {
      scene.move(-1);
      EuclideanTangentVector ray = scene.getFrame().obtainRayFor(0, 0);
      Optional<Intersection<EuclideanTangentVector>> optIntersection =
          scene.findFirstIntersection(ray, 10);
      assertTrue(optIntersection.isPresent());
      assertEquals(Color.WHITE, optIntersection.get().getColor());
    }

    @Test
    @DisplayName("ray misses plane-> no intersection")
    void missingPlane() {
      scene.move(-1);
      scene.turnLeft(Math.PI / 2);
      EuclideanTangentVector ray = scene.getFrame().obtainRayFor(0, 0);
      assertTrue(scene.findFirstIntersection(ray, 10).isEmpty());
    }
  }

  @Nested
  @DisplayName("Reflecting Planes")
  class Reflections {
    @Test
    void planeHidingThings() {
      EuclideanScene scene = new EuclideanScene();
      scene.addSphere(0.2, Color.WHITE);
      scene.move(-.4);
      scene.addReflectingPlane(Color.WHITE);
      scene.move(-.4);
      EuclideanTangentVector ray = scene.getFrame().obtainRayFor(0, 0);
      assertTrue(scene.findFirstIntersection(ray, 10).isEmpty());
    }

    @Test
    void reflectionHitsThings() {
      EuclideanScene scene = new EuclideanScene();
      scene.addSphere(0.2, Color.WHITE);
      scene.move(.8);
      scene.addReflectingPlane(Color.WHITE);
      scene.move(-.4);
      EuclideanTangentVector ray = scene.getFrame().obtainRayFor(0, 0);
      assertTrue(scene.findFirstIntersection(ray, 10).isPresent());
    }

    @Test
    void reflectingAtAnAngle() {
      EuclideanScene scene = new EuclideanScene();
      scene.addSphere(0.2, Color.WHITE);
      scene.move(.8);
      scene.turnLeft(Math.PI / 4);
      scene.addReflectingPlane(Color.WHITE);
      scene.turnLeft(Math.PI / 4);
      scene.move(-.4);
      EuclideanTangentVector ray = scene.getFrame().obtainRayFor(0, 0);
      assertTrue(scene.findFirstIntersection(ray, 10).isPresent());
    }
  }
}
