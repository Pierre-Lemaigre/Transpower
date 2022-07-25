package org.bestcolis
package model

import java.text.SimpleDateFormat
import java.util.UUID

/**
 * Shipper, core of the application Model
 *
 * @param id                   Represent a unique Shipper
 * @param name                 Name of the Shipper
 * @param timeSlots            Work times of the Shipper
 * @param workZone             Geographic work zone
 * @param maxPackagesWeight            Maximum weight of all packages
 * @param maxVolume            Maximum volumes of all packages
 * @param utilityWeight        Maximum weight of one package
 * @param averageDeliverySpeed Average speed of the delivery
 * @param deliveryCost         Cost for one full delivery (back and forth)
 */
case class Shipper(id: UUID, name: String, timeSlots: TimeSlots, workZone: GeoZone, maxPackagesWeight: Double, utilityWeight: Double, maxVolume: Double, averageDeliverySpeed: Double, deliveryCost: Double)
