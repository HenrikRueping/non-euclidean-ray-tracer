package de.henrikrueping.raytracer.general;

import java.awt.Color;
import de.henrikrueping.raytracer.Helper;
import de.henrikrueping.raytracer.euclidean.EuclideanScene;
import de.henrikrueping.raytracer.euclidean.EuclideanTangentFrame;
import de.henrikrueping.raytracer.euclidean.EuclideanTangentVector;
import de.henrikrueping.raytracer.hyperbolic.HyperbolicFrame;
import de.henrikrueping.raytracer.hyperbolic.HyperbolicScene;
import de.henrikrueping.raytracer.hyperbolic.HyperbolicTangentVector;
import de.henrikrueping.raytracer.spherical.SphericalScene;
import de.henrikrueping.raytracer.spherical.SphericalTangentFrame;
import de.henrikrueping.raytracer.spherical.SphericalTangentVector;

public class GeometryBuilder {
  private Color backgroundColor = Color.BLACK;// new Color(135, 206, 235);
  private Color cylinderColor = Color.YELLOW;
  private Color sphereColor = Color.RED;// new Color(135, 206, 235);
  private double cylinderRadius = 0.05;
  private double sphereRadius = 0.15;

  private static final double EDGE_LENGTH_ORTHOGONAL_PENTAGRAM =
      Helper.edgeLengthOfTriangleWithAngles(2 * Math.PI / 5, Math.PI / 4, Math.PI / 4);
  private static final double CENTER_TO_EDGE_ORTHOGONAL_PENTAGRAM =
      Helper.edgeLengthOfTriangleWithAngles(Math.PI / 4, Math.PI / 2, Math.PI / 5);
  private static final double ANGLE_CUBE_DIAGONAL_FACE = Math.asin(1 / Math.sqrt(3));
  private static final double ANGLE_EDGE_CENTER_CENTER_FACE_CENTER_OTHOGONAL_DODECAHEDRON =
      Helper.angleOpposing(CENTER_TO_EDGE_ORTHOGONAL_PENTAGRAM, Math.PI / 2, Math.PI / 4);
  private static final double DISTANCE_CENTER_FACE_ORTHOGONAL_DODECAHEDRON =
      Helper.edgeLengthOfTriangleWithAngles(Math.PI / 4, Math.PI / 2,
          ANGLE_EDGE_CENTER_CENTER_FACE_CENTER_OTHOGONAL_DODECAHEDRON);

  private static final double TETRAHEDRON_INNER_ANGLE = Math.asin(2 / 3 * Math.sqrt(6)) * 2;



  public GeometryBuilder() {}

  public GeometryBuilder setBackgroundColor(Color backgroundColor) {
    this.backgroundColor = backgroundColor;
    return this;
  }

  public GeometryBuilder setCylinderColor(Color cylinderColor) {
    this.cylinderColor = cylinderColor;
    return this;
  }

  public GeometryBuilder setSphereColor(Color sphereColor) {
    this.sphereColor = sphereColor;
    return this;
  }

  public GeometryBuilder setCylinderRadius(double cylinderRadius) {
    this.cylinderRadius = cylinderRadius;
    return this;
  }

  public GeometryBuilder setSphereRadius(double sphereRadius) {
    this.sphereRadius = sphereRadius;
    return this;
  }

  public Scene<HyperbolicTangentVector, HyperbolicFrame> generateDodecahedralTilingFourAroundEachEdge() {
    final int edge_mask_1 = 0B01111;
    final int vertex_mask_1 = 0B01110;
    Scene<HyperbolicTangentVector, HyperbolicFrame> scene = new HyperbolicScene();
    scene.setBackgroundColor(backgroundColor);
    HyperbolicFrame origin = new HyperbolicFrame(scene.getFrame());
    scene.move(DISTANCE_CENTER_FACE_ORTHOGONAL_DODECAHEDRON);
    scene.addReflectingPlane(Color.MAGENTA);
    scene.turnUp(Math.PI / 2);
    scene.move(CENTER_TO_EDGE_ORTHOGONAL_PENTAGRAM);
    scene.turnLeft(Math.PI / 2);
    scene.move(-EDGE_LENGTH_ORTHOGONAL_PENTAGRAM / 2);

    for (int i = 0; i < 5; i++) {
      scene.pitch(Math.PI / 2);
      scene.turnUp(Math.PI / 2);
      scene.addReflectingPlane(Color.MAGENTA);
      scene.turnUp(-Math.PI / 2);
      for (int j = 0; j < 5; j++) {
        int mask = 1 << j;
        if ((vertex_mask_1 & mask) != 0)
          scene.addSphere(sphereRadius, sphereColor);
        if ((edge_mask_1 & mask) != 0)
          scene.addCylinder(EDGE_LENGTH_ORTHOGONAL_PENTAGRAM, cylinderRadius, cylinderColor);
        scene.move(EDGE_LENGTH_ORTHOGONAL_PENTAGRAM);
        scene.turnLeft(Math.PI / 2);
      }
      scene.pitch(-Math.PI / 2);
      // scene.addSphere(sphereRadius, sphereColor);
      // scene.addCylinder(EDGE_LENGTH_ORTHOGONAL_PENTAGRAM, cylinderRadius, cylinderColor);
      scene.move(+EDGE_LENGTH_ORTHOGONAL_PENTAGRAM);
      scene.turnLeft(Math.PI / 2);
    }
    scene.teleportTo(origin);
    scene.move(-DISTANCE_CENTER_FACE_ORTHOGONAL_DODECAHEDRON);
    scene.addReflectingPlane(Color.MAGENTA);
    scene.turnUp(-Math.PI / 2);
    scene.move(CENTER_TO_EDGE_ORTHOGONAL_PENTAGRAM);
    scene.turnLeft(Math.PI / 2);
    scene.move(-EDGE_LENGTH_ORTHOGONAL_PENTAGRAM / 2);
    final int edge_mask_2 = 0B00011;
    final int vertex_mask_2 = 0B00001;
    for (int i = 0; i < 5; i++) {
      scene.pitch(Math.PI / 2);
      scene.turnUp(Math.PI / 2);
      scene.addReflectingPlane(Color.MAGENTA);
      scene.turnUp(-Math.PI / 2);
      for (int j = 0; j < 5; j++) {
        int mask = 1 << j;
        if ((vertex_mask_2 & mask) != 0)
          scene.addSphere(sphereRadius, sphereColor);
        if ((edge_mask_2 & mask) != 0)
          scene.addCylinder(EDGE_LENGTH_ORTHOGONAL_PENTAGRAM, cylinderRadius, cylinderColor);
        scene.move(EDGE_LENGTH_ORTHOGONAL_PENTAGRAM);
        scene.turnLeft(Math.PI / 2);
      }
      scene.pitch(-Math.PI / 2);
      scene.move(EDGE_LENGTH_ORTHOGONAL_PENTAGRAM);
      scene.turnLeft(Math.PI / 2);
    }
    scene.teleportTo(origin);

    return scene;
  }

  public Scene<EuclideanTangentVector, EuclideanTangentFrame> createEuclideanTilingByCubes(
      double cubeLength) {
    Scene<EuclideanTangentVector, EuclideanTangentFrame> scene = new EuclideanScene();
    EuclideanTangentFrame origin = new EuclideanTangentFrame(scene.getFrame());

    double l = cubeLength / 2;
    for (int j = -1; j <= 1; j += 2) {

      scene.move(j * l);
      scene.addReflectingPlane(Color.MAGENTA);
      // scene.addPlane(Color.MAGENTA);

      scene.turnUp(Math.PI / 2);
      scene.move(l);
      scene.turnLeft(-Math.PI / 2);
      scene.move(-l);
      for (int i = 0; i < 4; i++) {
        scene.addSphere(sphereRadius, sphereColor);
        scene.addCylinder(cubeLength, cylinderRadius, cylinderColor);
        scene.move(cubeLength);
        scene.turnLeft(-Math.PI / 2);
      }
      scene.teleportTo(origin);
    }
    for (int j = 0; j < 4; j++) {
      scene.pitch(j * Math.PI / 2);
      scene.turnLeft(Math.PI / 2);
      scene.move(l);
      scene.addReflectingPlane(Color.MAGENTA);

      scene.turnUp(Math.PI / 2);
      scene.move(l);
      scene.turnLeft(Math.PI / 2);
      scene.move(-l);

      scene.addCylinder(cubeLength, cylinderRadius, cylinderColor);
      scene.teleportTo(origin);
    }
    return scene;
  }

  public Scene<SphericalTangentVector, SphericalTangentFrame> createHypercube() {
    Scene<SphericalTangentVector, SphericalTangentFrame> scene = new SphericalScene();
    SphericalTangentFrame origin = new SphericalTangentFrame(scene.getFrame());
    return scene;
  }

}

