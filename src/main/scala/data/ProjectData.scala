package org.bestcolis
package data

import model.{Coordinates, GeoZone, Package, Shipper, Shipping, ShippingCategory, TimeSlots}

import java.util.UUID
import scala.collection.mutable.ListBuffer

object ProjectData {
  val shippings: List[Shipping] = List(
    Shipping(UUID.fromString("4ec65944-cf4e-4ef5-856b-baacce5fce2f"), Coordinates(43.29583540101676, 5.3788710116393945), Coordinates(43.29917840427482, 5.374300527478018), TimeSlots("12:00", "14:00"), List(Package(40, 20), Package(30, 10), Package(12, 4))),
    Shipping(UUID.fromString("21880eda-d26b-49af-a95c-8b38756c993c"), Coordinates(43.30074101225062, 5.376253175640859), Coordinates(43.30058582687115, 5.366189527416982), TimeSlots("14:50", "16:30"), List(Package(28, 25), Package(12, 20))),
    Shipping(UUID.fromString("99739e96-1101-48d7-a078-69cbd0b0e838"), Coordinates(43.29724485312076, 5.3788710116393945), Coordinates(43.29917840427482, 5.374300527478018), TimeSlots("09:30", "10:00"), List(Package(15, 7), Package(2, 2))),
    Shipping(UUID.fromString("ab945737-376a-4915-af70-e195b0cc2839"), Coordinates(43.29583540101676, 5.378871011639394), Coordinates(43.29917840427482, 5.374300527478018), TimeSlots("12:00", "14:00"), List(Package(30, 10))),
    Shipping(UUID.fromString("a9d822ae-0040-4795-8287-5c7bf52daf05"), Coordinates(43.29583540101676, 5.3788710116393945), Coordinates(43.297717266044906,5.364193963909902), TimeSlots("16:00", "18:00"), List(Package(1, 1), Package(32, 22), Package(10, 8), Package(30, 10), Package(12, 4))),
  )

  val shippingCategorys: List[ShippingCategory] = List(
    ShippingCategory(TimeSlots("10:00", "16:00"), GeoZone(Coordinates(43.2969901, 5.3789783), 5), 130, 15, 1)
  )

  val shippers: ListBuffer[Shipper] = ListBuffer(
    Shipper(UUID.fromString("a9d822ae-0040-4795-8287-5c7bf52daf01"), "marcus chrono", TimeSlots("09:00", "14:00"), GeoZone(Coordinates(43.2969901, 5.3789783), 10), 200, 10, 12, 50, 13),
    Shipper(UUID.fromString("a9d822ae-0040-4795-8287-5c7bf52daf02"), "john express", TimeSlots("09:00", "18:00"), GeoZone(Coordinates(43.2969901, 5.3789783), 10), 200, 20, 12, 50, 15),
    Shipper(UUID.fromString("a9d822ae-0040-4795-8287-5c7bf52daf03"), "julia truck", TimeSlots("09:00", "17:00"), GeoZone(Coordinates(43.2969901, 5.3789783), 10), 120, 10, 12, 50, 14)
  )
}

