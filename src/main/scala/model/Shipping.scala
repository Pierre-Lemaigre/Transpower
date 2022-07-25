package org.bestcolis
package model

import java.util.UUID

/**
 * A shipping seeking a Shipper
 * @param id UUID Identifier
 * @param pickupZone Geographic location of the starting point
 * @param deliveryZone Geographic location of the ending point
 * @param timeSlots Time span for the shipping arrival
 * @param packages Packages to be shipped (one to n)
 */
case class Shipping(id: UUID, pickupZone: Coordinates, deliveryZone: Coordinates, timeSlots: TimeSlots, packages: List[Package])
