package org.bestcolis
package api

import compatibility.CompatibilityLevel
import controller.{TranspowerController, TranspowerControllerImpl}
import data.ProjectData
import model.{GeoZone, Shipper, Shipping, ShippingCategory, TimeSlots}

import cats.effect.*
import cats.implicits._
import com.comcast.ip4s.*
import io.circe.*
import io.circe.Decoder.Result
import io.circe.literal.*
import io.circe.syntax.*
import io.circe.generic.auto.*
import org.http4s.*
import org.http4s.circe.*
import org.http4s.dsl.io.*
import org.http4s.ember.*
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.Router
import org.http4s.implicits.*

import java.util.UUID

object TranspowerServer extends IOApp {
  val controller: TranspowerControllerImpl = TranspowerControllerImpl()

  implicit val shipperEncoder: KeyEncoder[Shipper] = (key: Shipper) => key.name
  implicit val categoryDecoder: EntityDecoder[IO, ShippingCategory] = jsonOf[IO, ShippingCategory]
  implicit val shipperEntityDecoder: EntityDecoder[IO, Shipper] = jsonOf[IO, Shipper]

  val categoryApi: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "category" =>
      val shippers = ProjectData.shippers
      val category = ProjectData.shippingCategorys.head
      Ok(controller.getBestShipperForCategory(category, shippers).asJson)
    case req@POST -> Root / "category" =>
      val shippers = ProjectData.shippers
      for {
        shippingCategory <- req.as[ShippingCategory]
        resp <- Ok(controller.getBestShipperForCategory(shippingCategory, shippers).asJson)
      } yield resp
  }

  val shipperApi: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "shippers" / "all" =>
      Ok(ProjectData.shippers.asJson)
    case req @ POST -> Root / "shippers" / "add" =>
      for {
        shipper <- req.as[Shipper]
        resp <- Ok(ProjectData.shippers.addOne(shipper).asJson)
      } yield resp
  }

  val apis: HttpRoutes[IO] = categoryApi <+> shipperApi

  val httpApp: HttpApp[IO] = Router("/api/v1/" -> apis).orNotFound

  def run(args: List[String]): IO[ExitCode] =
    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(httpApp)
      .build
      .use(_ => IO.never)
      .as(ExitCode.Success)
}