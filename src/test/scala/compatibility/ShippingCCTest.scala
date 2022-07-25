package org.bestcolis
package compatibility

import compatibility.{Compatibility, Nonexistent, Partial, ShippingCategoryCompatibility}
import model.*

import org.scalatest.wordspec.AnyWordSpec

import java.util.UUID

class ShippingCCTest extends AnyWordSpec {
  "ShippingCategoryCompatibility" should {
    "Give Nonexistent for Non compatible Shipper" in {
      val shipperWorkTime: TimeSlots = TimeSlots.apply("08:00", "12:10")
      val shippingTZ: TimeSlots = TimeSlots.apply("18:00", "20:20")
      val workZone: GeoZone = GeoZone(Coordinates(48.829430, 2.378130), 5)
      val deliveryZone: GeoZone = GeoZone(Coordinates(48.890325, 2.321789), 7)
      val shipper: Shipper = Shipper(UUID.randomUUID(), "Deliver", shipperWorkTime, workZone, 100, 20, 10, 28.9, 2.37)
      val shippingCategory: ShippingCategory = ShippingCategory(shippingTZ, deliveryZone, 150, 40, 30)
      val notationFunction = (constraintsResult: List[Boolean]) => constraintsResult.foldLeft(0) { (acc, v) => if (v) acc + 1 else acc }

      val compatibilityLevel = Compatibility.compatibilityLevel(ShippingCategoryCompatibility, shipper, shippingCategory, notationFunction)
      assert(compatibilityLevel == Nonexistent)
    }

    "Give Partial as only the work zones are compatible" in {
      val shipperWorkTime: TimeSlots = TimeSlots.apply("08:00", "12:10")
      val shippingTZ: TimeSlots = TimeSlots.apply("18:00", "20:20")
      val workZone: GeoZone = GeoZone(Coordinates(48.829430, 2.378130), 5)
      val deliveryZone: GeoZone = GeoZone(Coordinates(48.830634, 2.355328), 2)
      val shipper: Shipper = Shipper(UUID.randomUUID(), "Deliver", shipperWorkTime, workZone, 100, 20, 10, 28.9, 2.37)
      val shippingCategory: ShippingCategory = ShippingCategory(shippingTZ, deliveryZone, 150, 40, 30)
      val notationFunction = (constraintsResult: List[Boolean]) => constraintsResult.foldLeft(0) { (acc, v) => if (v) acc + 1 else acc }

      val compatibilityLevel = Compatibility.compatibilityLevel(ShippingCategoryCompatibility, shipper, shippingCategory, notationFunction)
      assert(compatibilityLevel == Partial)
    }

    "Give Partial as only the time slots are compatible" in {
      val shipperWorkTime: TimeSlots = TimeSlots.apply("08:00", "12:10")
      val shippingTZ: TimeSlots = TimeSlots.apply("10:00", "10:20")
      val workZone: GeoZone = GeoZone(Coordinates(48.829430, 2.378130), 5)
      val deliveryZone: GeoZone = GeoZone(Coordinates(48.830634, 2.355328), 10)
      val shipper: Shipper = Shipper(UUID.randomUUID(), "Deliver", shipperWorkTime, workZone, 100, 20, 10, 28.9, 2.37)
      val shippingCategory: ShippingCategory = ShippingCategory(shippingTZ, deliveryZone, 150, 40, 30)
      val notationFunction = (constraintsResult: List[Boolean]) => constraintsResult.foldLeft(0) { (acc, v) => if (v) acc + 1 else acc }

      val compatibilityLevel = Compatibility.compatibilityLevel(ShippingCategoryCompatibility, shipper, shippingCategory, notationFunction)
      assert(compatibilityLevel == Partial)
    }

    "Give Partial as only the max weights are compatible" in {
      val shipperWorkTime: TimeSlots = TimeSlots.apply("08:00", "12:10")
      val shippingTZ: TimeSlots = TimeSlots.apply("12:30", "14:20")
      val workZone: GeoZone = GeoZone(Coordinates(48.829430, 2.378130), 5)
      val deliveryZone: GeoZone = GeoZone(Coordinates(48.830634, 2.355328), 10)
      val shipper: Shipper = Shipper(UUID.randomUUID(), "Deliver", shipperWorkTime, workZone, 160, 50, 40, 28.9, 2.37)
      val shippingCategory: ShippingCategory = ShippingCategory(shippingTZ, deliveryZone, 150, 40, 30)
      val notationFunction = (constraintsResult: List[Boolean]) => constraintsResult.foldLeft(0) { (acc, v) => if (v) acc + 1 else acc }

      val compatibilityLevel = Compatibility.compatibilityLevel(ShippingCategoryCompatibility, shipper, shippingCategory, notationFunction)
      assert(compatibilityLevel == Partial)

    }

    "Give full as all the constraints are respected" in {
      val shipperWorkTime: TimeSlots = TimeSlots.apply("08:00", "12:10")
      val shippingTZ: TimeSlots = TimeSlots.apply("10:00", "12:09")
      val workZone: GeoZone = GeoZone(Coordinates(48.829430, 2.378130), 5)
      val deliveryZone: GeoZone = GeoZone(Coordinates(48.830634, 2.355328), 2)
      val shipper: Shipper = Shipper(UUID.randomUUID(), "Deliver", shipperWorkTime, workZone, 150, 40, 30, 28.9, 2.37)
      val shippingCategory: ShippingCategory = ShippingCategory(shippingTZ, deliveryZone, 150, 40, 30)
      val notationFunction = (constraintsResult: List[Boolean]) => constraintsResult.foldLeft(0) { (acc, v) => if (v) acc + 1 else acc }

      val compatibilityLevel = Compatibility.compatibilityLevel(ShippingCategoryCompatibility, shipper, shippingCategory, notationFunction)
      assert(compatibilityLevel == Complete)
    }
  }
}
