package recur_fetch

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import org.scalatest.{AsyncFlatSpec, DiagrammedAssertions, Matchers}
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.Future

class ClientSpec extends AsyncFlatSpec with Matchers with DiagrammedAssertions {

  "recursiveFetch" should "fetch the responses recursively" in {

    implicit val system       = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val ec           = system.dispatcher

    val client = new Client()

    def recursiveFetch(url: String, acc: List[String] = Nil): Future[List[String]] = {
      client.fetch(url).flatMap { response =>
        val result = acc :+ response
        (Json.parse(response) \ "next").toOption match {
          case Some(next) if isEmpty(next) => Future(result)
          case Some(next)                  => recursiveFetch(trimExtraQuote(next), result)
        }
      }
    }

    val f = recursiveFetch("/1")
    f.onComplete(_ => client.close())

    for {
      result <- f
    } yield assert(result.size === 3)

  }

  private def isEmpty(string: JsValue)        = Json.stringify(string) == "\"\""
  private def trimExtraQuote(string: JsValue) = Json.stringify(string).replaceAll("\"", "")

}
