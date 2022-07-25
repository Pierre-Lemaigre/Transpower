package org.bestcolis
package utils

import model.Coordinates
import utils.Distance

import org.scalatest.wordspec.AnyWordSpec

import scala.language.postfixOps


class DistanceHTest extends AnyWordSpec {
  "Haversine distance" should {
    "compute Eiffel tower to Louvre" in {
      val distanceH = Distance.distanceInKm(Coordinates(48.858233472391554, 2.294503470023301), Coordinates(48.86098278504738, 2.3358387125858826))
      val distanceRounded = (math rint distanceH * 100) / 100
      assert(distanceRounded == 3.04)
    }

    "compute distance between the two Interforum sites" in {
      val distanceH = (math floor Distance.distanceInKm(Coordinates(48.2969901, 2.3789783), Coordinates(43.4806615, 5.3038333)) * 100) / 100
      assert(distanceH == 581.31)
    }
  }
}
