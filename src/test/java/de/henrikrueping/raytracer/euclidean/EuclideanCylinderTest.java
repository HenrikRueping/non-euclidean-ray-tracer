package de.henrikrueping.raytracer.euclidean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.awt.Color;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import de.henrikrueping.raytracer.general.Intersection;

class EuclideanCylinderTest {
  EuclideanScene scene;

  @BeforeEach
  void createScene() {
    scene = new EuclideanScene();
    scene.addCylinder(2, 0.2, Color.WHITE);
  }

  @Test
  @DisplayName("cylinder in front of me -> intersection exists")
  void cylinderInFrontOfMe() {
    scene.move(1);
    scene.turnLeft(Math.PI / 2);
    scene.move(-1);
    EuclideanTangentVector ray = scene.getFrame().obtainRayFor(0, 0);
    Optional<Intersection<EuclideanTangentVector>> optIntersection =
        scene.findFirstIntersection(ray, 10);
    assertTrue(optIntersection.isPresent());
    assertEquals(Color.WHITE, optIntersection.get().getColor());
  }

  @Test
  @DisplayName("looking from the inside of a cylinder -> intersection point")
  void AmInCylinder() {
    scene.move(1);
    scene.turnLeft(Math.PI / 2);
    EuclideanTangentVector ray = scene.getFrame().obtainRayFor(0, 0);
    Optional<Intersection<EuclideanTangentVector>> optIntersection =
        scene.findFirstIntersection(ray, 10);
    assertTrue(optIntersection.isPresent());
    assertTrue(optIntersection.get().getDist() > 0);
    // TODO verbessern auf 'genauen' Wert
  }

  @Test
  @DisplayName("if the cylinder is behind me -> no intersection")
  void cylinderBehindMe() {
    scene.move(1);
    scene.turnLeft(Math.PI / 2);
    scene.move(.5);
    EuclideanTangentVector ray = scene.getFrame().obtainRayFor(0, 0);
    Optional<Intersection<EuclideanTangentVector>> optIntersection =
        scene.findFirstIntersection(ray, 10);
    assertTrue(optIntersection.isEmpty());
  }

  @Test
  @DisplayName("looking into cylinder from bottom -> intersection")
  void lookingIntoCylinderFromBottom() {
    scene.turnLeft(Math.PI / 3);
    scene.move(-1);
    EuclideanTangentVector ray = scene.getFrame().obtainRayFor(0, 0);
    Optional<Intersection<EuclideanTangentVector>> optIntersection =
        scene.findFirstIntersection(ray, 10);
    assertTrue(optIntersection.isPresent());
    assertTrue(optIntersection.get().getDist() > 0);
  }

  @Test
  @DisplayName("looking into cylinder from top -> intersection")
  void lookingIntoCylinderFromTop() {
    scene.move(2);
    scene.turnLeft(Math.PI * 2 / 3);
    scene.move(-1);
    EuclideanTangentVector ray = scene.getFrame().obtainRayFor(0, 0);
    Optional<Intersection<EuclideanTangentVector>> optIntersection =
        scene.findFirstIntersection(ray, 10);
    assertTrue(optIntersection.isPresent());
    assertTrue(optIntersection.get().getDist() > 0);
  }

  @Test
  @DisplayName("completely missing a cylinder-> no intersection")
  void rayMissingEntireCylinder() {
    scene.turnLeft(Math.PI / 2);
    scene.move(2);
    scene.turnUp(Math.PI / 2);

    EuclideanTangentVector ray = scene.getFrame().obtainRayFor(0, 0);
    Optional<Intersection<EuclideanTangentVector>> optIntersection =
        scene.findFirstIntersection(ray, 10);
    assertTrue(optIntersection.isEmpty());
  }

  @Test
  @DisplayName("A ray below the bottom -> no intersection")
  void testRayBelowBottom() {
    scene.move(-1);
    scene.turnLeft(Math.PI / 2);
    EuclideanTangentVector ray = scene.getFrame().obtainRayFor(0, 0);
    Optional<Intersection<EuclideanTangentVector>> optIntersection =
        scene.findFirstIntersection(ray, 10);
    assertTrue(optIntersection.isEmpty());
  }

  @Test
  @DisplayName("A ray above the top-> no intersection")
  void testRayAboveTop() {
    scene.move(3);
    scene.turnLeft(Math.PI / 2);
    EuclideanTangentVector ray = scene.getFrame().obtainRayFor(0, 0);
    Optional<Intersection<EuclideanTangentVector>> optIntersection =
        scene.findFirstIntersection(ray, 10);
    assertTrue(optIntersection.isEmpty());
  }

  @Test
  @DisplayName("Ray parallel to cylinder -> no intersection")
  void testRayParallel() {
    EuclideanTangentVector ray = scene.getFrame().obtainRayFor(0, 0);
    Optional<Intersection<EuclideanTangentVector>> optIntersection =
        scene.findFirstIntersection(ray, 10);
    assertTrue(optIntersection.isEmpty());
  }



}
