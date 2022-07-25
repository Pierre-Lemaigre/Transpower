package org.bestcolis
package api

import model.{Coordinates, GeoZone, Shipper, ShippingCategory, TimeSlots}

import cats.effect.IO
import io.circe.{KeyDecoder, KeyEncoder}
import io.circe.generic.auto.*
import org.http4s.Method.POST
import org.http4s.circe.jsonOf
import org.http4s.client.dsl.io.*
import org.http4s.ember.client.*
import org.http4s.syntax.literals.uri
import org.http4s.circe.CirceEntityEncoder.circeEntityEncoder
import io.circe.Encoder.encodeString
import org.http4s.EntityDecoder
import cats.effect.unsafe.IORuntime

import java.util.UUID


object TranspowerClient {
  def clientShippingCat(args : Array[String]): IO[Unit] = {
    val req = POST(ShippingCategory(TimeSlots("14:00", "16:00"), GeoZone(Coordinates(43.2969901, 5.3789783), 5), 120, 15, 1), uri"http://localhost:8080/api/v1/category")
    EmberClientBuilder.default[IO].build.use { httpClient =>
      val res: IO[Map[String, String]] = httpClient.expect(req)(jsonOf[IO, Map[String, String]])
      res.map(es => es.foreach(println))
    }
  }

  def shipperAdderClient(): IO[Unit] = {
    val req = POST(Shipper(UUID.fromString("a9d822ae-0040-5795-8287-5c7bf52daf01"), "papacita express", TimeSlots("09:00", "14:00"), GeoZone(Coordinates(43.2969901, 5.3789783), 10), 200, 10, 12, 50, 13), uri"http://localhost:8080/api/v1/shippers/add")
    EmberClientBuilder.default[IO].build.use { httpClient =>
      val res: IO[List[Shipper]] = httpClient.expect(req)(jsonOf[IO, List[Shipper]])
      res.map(es => es.foreach(println))
    }
  }

  def main(args: Array[String]): Unit = {
    implicit val runtime: IORuntime = cats.effect.unsafe.IORuntime.global
    clientShippingCat(args).unsafeRunSync()
    shipperAdderClient().unsafeRunSync()
  }
}