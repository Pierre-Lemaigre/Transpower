package org.bestcolis
package controller

import compatibility.{Compatibility, CompatibilityLevel, Partial, ShippingCategoryCompatibility}
import model.{Shipper, ShippingCategory}

import scala.collection.mutable.ListBuffer


trait TranspowerController {
  def getBestShipperForCategory(shippingCategory: ShippingCategory, shippers: ListBuffer[Shipper]): Map[Shipper, String]
}


class TranspowerControllerImpl extends TranspowerController {
  override def getBestShipperForCategory(shippingCategory: ShippingCategory, shippers: ListBuffer[Shipper]): Map[Shipper, String] = {
    shippers.zipWithIndex.map {
      case (shipper, _) =>
        val compatibilityLevel = Compatibility.compatibilityLevel(ShippingCategoryCompatibility, shipper, shippingCategory)
        (shipper, compatibilityLevel)
    }.sortWith {
      _._2.order < _._2.order
    }.map { t =>
      (t._1, t._2.toString)
    }.toMap
  }
}

object TranspowerControllerImpl {
}
