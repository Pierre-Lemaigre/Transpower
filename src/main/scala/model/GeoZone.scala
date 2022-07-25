package org.bestcolis
package model

/**
 * Represent the Geo Zone (Geographic circle)
 * @param coordinates Geographic Coordinates (Latitude, Longitude)
 * @param radius Radius in Km around the Coordinates Position
 */
case class GeoZone(coordinates: Coordinates, radius: Double)