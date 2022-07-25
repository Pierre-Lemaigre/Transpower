package org.bestcolis
package model

/**
 * Geographic Coordinates, in Degrees
 * @param latitude Latitude degree
 * @param longitude Longitude degree
 */
case class Coordinates(latitude: Double, longitude: Double) {
  /**
   * Convert the coordinates in Radian
   * @return (latitude: Double, longitude: Double) in radians
   */
  def toRadian: (Double, Double) = {
    (math.toRadians(latitude), math.toRadians(longitude))
  }
}
