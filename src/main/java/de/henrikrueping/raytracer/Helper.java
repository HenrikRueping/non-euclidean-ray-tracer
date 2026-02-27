package de.henrikrueping.raytracer;

public class Helper {
  public static double hypInnerProduct(double[] d1, double[] d2) {
    double ret = -d1[0] * d2[0];
    for (int i = 1; i < 4; i++)
      ret += d1[i] * d2[i];
    return ret;
  }

  public static double innerProduct(double[] d1, double[] d2) {
    double ret = 0;
    for (int i = 0; i < d1.length; i++)
      ret += d1[i] * d2[i];
    return ret;
  }

  public static double asinh(double d) {
    return Math.log(d + Math.sqrt(d * d + 1.0));
  }

  public static double acosh(double d) {
    return Math.log(d + Math.sqrt(d * d - 1.0));
  }

  public static double edgeLengthOfTriangleWithAngles(double opposingAngle, double adjacentAngle1,
      double adjacentAngle2) {
    double coshOfEdge =
        (Math.cos(opposingAngle) + Math.cos(adjacentAngle1) * Math.cos(adjacentAngle2))
            / (Math.sin(adjacentAngle1) * Math.sin(adjacentAngle2));
    return Math.log(coshOfEdge + Math.sqrt(coshOfEdge * coshOfEdge - 1));
  }

  public static double angleOpposing(double opposingSideLength, double adjacentAngle1,
      double adjacentAngle2) {
    double cos = Math.sin(adjacentAngle1) * Math.sin(adjacentAngle2) * Math.cosh(opposingSideLength)
        - Math.cos(adjacentAngle1) * Math.cos(adjacentAngle2);
    return Math.acos(cos);
  }
}
