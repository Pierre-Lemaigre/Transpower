package org.bestcolis
package compatibility

import model.*

import org.scalatest.wordspec.AnyWordSpec

import java.util.UUID

class ShippingCompatibilityTest extends AnyWordSpec {
  "Shipping Compatibility" should {
    "Return Nonexistent as zero constraints is respected" in {
      val shipperWorkTime: TimeSlots = TimeSlots.apply("08:00", "12:10")
      val shippingTZ: TimeSlots = TimeSlots.apply("14:00", "18:20")
      val workZone: GeoZone = GeoZone(Coordinates(48.829430, 2.378130), 1)
      val pickupZone: Coordinates = Coordinates(48.829430, 2.378130)
      val deliveryZone: Coordinates = Coordinates(48.830634, 2.355328)
      val shipper: Shipper = Shipper(UUID.randomUUID(), "Deliver", shipperWorkTime, workZone, 100, 20, 10, 28.9, 2.37)
      val packages: List[Package] = List(Package(20, 15), Package(10, 10), Package(3, 2))
      val shipping: Shipping = Shipping(UUID.randomUUID(), pickupZone, deliveryZone, shippingTZ, packages)
      val notationFunction = (constraintsResult: List[Boolean]) => constraintsResult.foldLeft(0) { (acc, v) => if (v) acc + 1 else acc }

      val compatibilityLevel = Compatibility.compatibilityLevel(ShippingCompatibility, shipper, shipping, notationFunction)
      assert(compatibilityLevel == Nonexistent)
    }

    "Return Partial as Zones are respected" in {
      val shipperWorkTime: TimeSlots = TimeSlots.apply("08:00", "12:10")
      val shippingTZ: TimeSlots = TimeSlots.apply("14:00", "18:20")
      val workZone: GeoZone = GeoZone(Coordinates(48.829430, 2.378130), 5)
      val pickupZone: Coordinates = Coordinates(48.829430, 2.378130)
      val deliveryZone: Coordinates = Coordinates(48.830634, 2.355328)
      val shipper: Shipper = Shipper(UUID.randomUUID(), "Deliver", shipperWorkTime, workZone, 100, 20, 10, 28.9, 2.37)
      val packages: List[Package] = List(Package(200, 15), Package(10, 10), Package(3, 2))
      val shipping: Shipping = Shipping(UUID.randomUUID(), pickupZone, deliveryZone, shippingTZ, packages)
      val notationFunction = (constraintsResult: List[Boolean]) => constraintsResult.foldLeft(0) { (acc, v) => if (v) acc + 1 else acc }

      val compatibilityLevel = Compatibility.compatibilityLevel(ShippingCompatibility, shipper, shipping, notationFunction)
      assert(compatibilityLevel == Partial)
    }
    
    "Return Partial as Delivery times are respected" in {
      val shipperWorkTime: TimeSlots = TimeSlots.apply("08:00", "12:10")
      val shippingTZ: TimeSlots = TimeSlots.apply("10:00", "11:20")
      val workZone: GeoZone = GeoZone(Coordinates(48.829430, 2.378130), 1)
      val pickupZone: Coordinates = Coordinates(48.829430, 2.378130)
      val deliveryZone: Coordinates = Coordinates(48.830634, 2.355328)
      val shipper: Shipper = Shipper(UUID.randomUUID(), "Deliver", shipperWorkTime, workZone, 100, 20, 10, 28.9, 2.37)
      val packages: List[Package] = List(Package(200, 15), Package(10, 10), Package(3, 2))
      val shipping: Shipping = Shipping(UUID.randomUUID(), pickupZone, deliveryZone, shippingTZ, packages)
      val notationFunction = (constraintsResult: List[Boolean]) => constraintsResult.foldLeft(0) { (acc, v) => if (v) acc + 1 else acc }

      val compatibilityLevel = Compatibility.compatibilityLevel(ShippingCompatibility, shipper, shipping, notationFunction)
      assert(compatibilityLevel == Partial)
    }

    "Return Partial as Weights and Volume are respected" in {
      val shipperWorkTime: TimeSlots = TimeSlots.apply("08:00", "12:10")
      val shippingTZ: TimeSlots = TimeSlots.apply("14:00", "18:20")
      val workZone: GeoZone = GeoZone(Coordinates(48.829430, 2.378130), 1)
      val pickupZone: Coordinates = Coordinates(48.829430, 2.378130)
      val deliveryZone: Coordinates = Coordinates(48.830634, 2.355328)
      val shipper: Shipper = Shipper(UUID.randomUUID(), "Deliver", shipperWorkTime, workZone, 100, 20, 27, 28.9, 2.37)
      val packages: List[Package] = List(Package(20, 15), Package(10, 10), Package(3, 2))
      val shipping: Shipping = Shipping(UUID.randomUUID(), pickupZone, deliveryZone, shippingTZ, packages)
      val notationFunction = (constraintsResult: List[Boolean]) => constraintsResult.foldLeft(0) { (acc, v) => if (v) acc + 1 else acc }

      val compatibilityLevel = Compatibility.compatibilityLevel(ShippingCompatibility, shipper, shipping, notationFunction)
      assert(compatibilityLevel == Partial)
    }
  }
}
