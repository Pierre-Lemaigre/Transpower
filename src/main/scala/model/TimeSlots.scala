package org.bestcolis
package model

import java.text.SimpleDateFormat
import java.time.{LocalDateTime, LocalTime}
import java.time.format.{DateTimeFormatter, FormatStyle}
import java.time.temporal.TemporalAccessor
import java.util.Locale

/**
 * Work time slot for a Shipper
 * @param startTime Debut of the timeslot
 * @param endTime end of the timeslot
 */
case class TimeSlots(startTime: LocalTime, endTime: LocalTime)

object TimeSlots {

  // unhandled exception, be careful
  def apply(startDate: String, endDate: String): TimeSlots = {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    TimeSlots(LocalTime.parse(startDate, formatter), LocalTime.parse(endDate, formatter))
  }
}
