package org.bestcolis
package compatibility

import model.{Shipper, ShippingCategory}
import utils.Distance


object ShippingCategoryCompatibility extends ConstraintsCompatibility[Shipper, ShippingCategory] {
  val checkers: List[(Shipper, ShippingCategory) => Boolean] = List(checkZoneCompatibility, checkTimesSlotsCompatibility, checkWeightsAndVolumes)

  override def checkedAgainst: List[(Shipper, ShippingCategory) => Boolean] = {
    checkers
  }

  def checkZoneCompatibility(shipper: Shipper, category: ShippingCategory): Boolean = {
    Distance.containsCircle(shipper.workZone, category.deliveryZone)
  }

  def checkTimesSlotsCompatibility(shipper: Shipper, category: ShippingCategory): Boolean = {
    val shipWT = shipper.timeSlots
    val catWT = category.timeSlots
    shipWT.startTime.isBefore(catWT.startTime) && shipWT.endTime.isAfter(catWT.endTime)
  }

  def checkWeightsAndVolumes(shipper: Shipper, category: ShippingCategory): Boolean = {
    shipper.maxPackagesWeight >= category.maxPackagesWeight
      && shipper.utilityWeight >= category.utilityPackageWeight
      && shipper.maxVolume >= category.maxVolume
  }
}
