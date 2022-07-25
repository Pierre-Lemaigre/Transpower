package org.bestcolis
package utils

import model.{Coordinates, GeoZone}

// TODO : Documentation
object Distance {
  /**
   * This function calculates the distance between two GCS Positions in KM using the Haversine formulae
   *
   * @param origin      GPS Coordinates of the starting Point (Latitude, Longitude) in °
   * @param destination GPS Coordinates of the ending Point (Latitude, Longitude) in °
   * @return Distance between the two Points, in KM
   */
  def distanceInKm(origin: Coordinates, destination: Coordinates): Double = {
    import math.*

    // Earth radius needed for the final formulae (size of the sphere)
    val earthRadius: Double = 6_371

    // Haversine function is based on radian (not deg)
    val (oLat, oLong) = origin.toRadian
    val (dLat, dLong) = destination.toRadian

    val deltaLat = dLat - oLat
    val deltaLong = dLong - oLong
    // Haversine function : sin^2(tau/2)
    val haversine = pow(sin(deltaLat / 2), 2) + cos(oLat) * cos(dLat) * pow(sin(deltaLong / 2), 2)

    // because hav(d/r) = x, we have d = r * reversehav(x), so d = r * 2asin(sqr(x))
    2 * earthRadius * asin(sqrt(haversine))
  }

  def containsCircle(outerCircle: GeoZone, insiderCircle: GeoZone): Boolean = {
    outerCircle.radius >= (Distance.distanceInKm(outerCircle.coordinates, insiderCircle.coordinates) + insiderCircle.radius)
  }

  def circleContainsLocations(circle: GeoZone, points: List[Coordinates]): Boolean = {
    points.forall(point => circleContainsLocation(circle, point))
  }

  def circleContainsLocation(circle: GeoZone, point: Coordinates): Boolean = {
    Distance.distanceInKm(circle.coordinates, point) <= circle.radius
  }
}
