package recur_fetch

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import spray.json.DefaultJsonProtocol._

import scala.concurrent.Future

object Server extends App {

  val host = "localhost"
  val port = 8080

  implicit val system       = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val ec           = system.dispatcher

  implicit val messageFormat = jsonFormat3(Message)

  val route: Route =
    get {
      pathPrefix(LongNumber) { id =>
        onSuccess(Future(id)) {
          case _ if id == 1 => complete(Message(1, "One", "/2"))
          case _ if id == 2 => complete(Message(2, "Two", "/3"))
          case _ if id == 3 => complete(Message(3, "Three", ""))
          case _            => complete(StatusCodes.NotFound)
        }
      }
    }

  val log           = Logging(system.eventStream, "recur-fetch-server")
  val bindingFuture = Http().bindAndHandle(route, host, port)

  bindingFuture
    .map { serverBinding =>
      log.info(s"Bound to ${serverBinding.localAddress}")
    }
    .failed
    .foreach {
      case ex: Exception =>
        log.error(ex, "failed to bind to {}:{}", host, port)
        system.terminate()
    }
}

case class Message(id: Long, message: String, next: String)
