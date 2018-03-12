package recur_fetch

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import org.scalatest.{AsyncFlatSpec, DiagrammedAssertions, Matchers}
import play.api.libs.json.Json

import scala.collection.mutable.ListBuffer
import scala.concurrent.Future

class ClientSpec extends AsyncFlatSpec with Matchers with DiagrammedAssertions {

  "recurFetch" should "fetch responses recursively" in {

    implicit val system       = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val ec           = system.dispatcher

    val client = new Client()
    val buf    = ListBuffer.empty[String]

    def recursiveFetch(url: String): Future[ListBuffer[String]] = {
      client
        .fetch(url)
        .map(res => {
          (Json.parse(res) \ "next").toOption match {
            case Some(next) if Json.stringify(next) == "\"\"" =>
              buf += res
            case Some(next) =>
              buf += res
              recursiveFetch(Json.stringify(next).replaceAll("\"", "").replaceAll("/", ""))
              buf
          }
        })
    }

    // https://stackoverflow.com/questions/43032430/recursive-call-future-in-scala/43034115
    def recursiveFetch2(url: String): Future[ListBuffer[String]] = Future.unit.flatMap { _ =>
      client
        .fetch(url)
        .map(res => {
          (Json.parse(res) \ "next").toOption match {
            case Some(next) if Json.stringify(next) == "\"\"" =>
              buf += res
            case Some(next) =>
              buf += res
              recursiveFetch(Json.stringify(next).replaceAll("\"", "").replaceAll("/", ""))
              buf
          }
        })
    }

    recursiveFetch("1").map { result =>
      assert(result.size === 3)
    }

  }
}
