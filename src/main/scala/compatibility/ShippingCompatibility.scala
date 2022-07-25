package org.bestcolis
package compatibility

import model.{Shipper, Shipping}
import utils.Distance

import java.time.Duration

// TODO : Documentation
object ShippingCompatibility extends ConstraintsCompatibility[Shipper, Shipping] {
  val checkers: List[(Shipper, Shipping) => Boolean] = List(checkPickupAndDeliveryZones, checkDeliveryTime, checkWeightsAndVolumes)

  override def checkedAgainst: List[(Shipper, Shipping) => Boolean] = checkers

  def checkPickupAndDeliveryZones(shipper: Shipper, shipping: Shipping): Boolean = {
    Distance.circleContainsLocations(shipper.workZone, List(shipping.deliveryZone, shipping.pickupZone))
  }

  def checkDeliveryTime(shipper: Shipper, shipping: Shipping): Boolean = {
    val shipperTS = shipper.timeSlots
    val shippingTS = shipping.timeSlots
    // Check if the shipper works time windows is big enough
    val timeWindowExist = shipperTS.startTime.isBefore(shippingTS.startTime) && shipperTS.endTime.isAfter(shippingTS.endTime)

    // Check if the shipper has sufficient speed to deliver the package in time
    val deliveryDuration = shippingTS.endTime minus Duration.ofSeconds(shippingTS.startTime.toSecondOfDay)
    val speedPerSec = shipper.averageDeliverySpeed / 3.6
    val deliverInTime = (deliveryDuration.toSecondOfDay / speedPerSec) > Distance.distanceInKm(shipping.pickupZone, shipping.deliveryZone)
    timeWindowExist && deliverInTime
  }

  def checkWeightsAndVolumes(shipper: Shipper, shipping: Shipping): Boolean = {
    val utilityWeightCheck = shipping.packages.forall(pa => pa.weight <= shipper.utilityWeight)
    val maxWeightCheck = shipping.packages.map(pa => pa.weight).sum <= shipper.maxPackagesWeight
    val volumeCheck = shipping.packages.map(pa => pa.volume).sum <= shipper.maxVolume
    utilityWeightCheck && maxWeightCheck && volumeCheck
  }
}