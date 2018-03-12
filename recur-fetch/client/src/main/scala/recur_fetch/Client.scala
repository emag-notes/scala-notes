package recur_fetch

import akka.stream.ActorMaterializer
import play.api.libs.ws.ahc.StandaloneAhcWSClient

import scala.concurrent.{ExecutionContext, Future}

class Client(apiEndpoint: String = "http://localhost:8080")(implicit ec: ExecutionContext,
                                                            materializer: ActorMaterializer) {

  private val wsClient = StandaloneAhcWSClient()

  def fetch(path: String): Future[String] = {
    wsClient
      .url(s"$apiEndpoint$path")
      .get()
      .map { response =>
        response.body
      }
  }

  def close() = wsClient.close()
}
