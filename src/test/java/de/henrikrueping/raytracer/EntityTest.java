package de.henrikrueping.raytracer;

import java.awt.Color;
import java.util.Arrays;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import de.henrikrueping.raytracer.euclidean.EuclideanScene;
import de.henrikrueping.raytracer.euclidean.EuclideanTangentFrame;
import de.henrikrueping.raytracer.euclidean.EuclideanTangentVector;
import de.henrikrueping.raytracer.general.Camera;
import de.henrikrueping.raytracer.general.GeometryBuilder;
import de.henrikrueping.raytracer.general.Scene;

public class EntityTest {
  @Test
  @Disabled
  void renderHyperbolicTiling() {
    Scene scene = new GeometryBuilder().generateDodecahedralTilingFourAroundEachEdge();
    int width = (int) Math.round(67.5 * 300 / 2.54);
    int height = (int) Math.round(48 * 300 / 2.54);

    Camera c = new Camera(width, height);

    c.render(scene);
  }

  // @Test
  void testComp() {
    System.out.println("EDGE_LENGTH_ORTHOGONAL_PENTAGRAM ="
        + Helper.edgeLengthOfTriangleWithAngles(2 * Math.PI / 5, Math.PI / 4, Math.PI / 4));
    double CENTER_TO_EDGE_ORTHOGONAL_PENTAGRAM =
        Helper.edgeLengthOfTriangleWithAngles(Math.PI / 4, Math.PI / 2, Math.PI / 5);
    System.out
        .println("CENTER_TO_EDGE_ORTHOGONAL_PENTAGRAM =" + CENTER_TO_EDGE_ORTHOGONAL_PENTAGRAM);
    System.out.println("ANGLE_CUBE_DIAGONAL_FACE =" + Math.asin(1 / Math.sqrt(3)));
    double ANGLE_EDGE_CENTER_CENTER_FACE_CENTER_OTHOGONAL_DODECAHEDRON =
        Helper.angleOpposing(CENTER_TO_EDGE_ORTHOGONAL_PENTAGRAM, Math.PI / 2, Math.PI / 4);
    System.out.println("ANGLE_EDGE_CENTER_CENTER_FACE_CENTER_OTHOGONAL_DODECAHEDRON ="
        + ANGLE_EDGE_CENTER_CENTER_FACE_CENTER_OTHOGONAL_DODECAHEDRON);
    System.out.println(
        "DISTANCE_CENTER_FACE_ORTHOGONAL_DODECAHEDRON =" + Helper.edgeLengthOfTriangleWithAngles(
            Math.PI / 4, Math.PI / 2, ANGLE_EDGE_CENTER_CENTER_FACE_CENTER_OTHOGONAL_DODECAHEDRON));
  }

  @Test
  @Disabled
  void renderEuclideanTiling() {
    Scene<EuclideanTangentVector, EuclideanTangentFrame> scene =
        new GeometryBuilder().createEuclideanTilingByCubes(1);
    int width = (int) Math.round(1200);
    int height = (int) Math.round(1200);

    Camera c = new Camera(width, height).setMaxDist(50);

    c.render(scene);
    System.out.println(Arrays.toString(scene.getFrame().getPoint()));
    System.out.println(Arrays.toString(scene.getFrame().getDirection()));
    System.out.println(Arrays.toString(scene.getFrame().getRight()));
    System.out.println(Arrays.toString(scene.getFrame().getDown()));
  }

  @Test
  @Disabled
  void renderEuclideanDummy() {
    Scene<EuclideanTangentVector, EuclideanTangentFrame> scene = new EuclideanScene();
    scene.addCylinder(2, 1, Color.yellow);
    scene.turnLeft(Math.PI / 2);
    scene.move(-.5);
    scene.getColor(scene.getFrame().obtainRayFor(0, 0), 10);
  }
}
