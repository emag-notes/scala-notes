package circe

import io.circe.Decoder.Result
import io.circe.generic.auto._
import io.circe.{Decoder, HCursor}

case class SlackPostMessageRequest(channel: String,
                                   username: String,
                                   text: String)

sealed trait SlackAPIResponse

case class PostMessageSuccess(
    ok: Boolean,
    channel: String,
    ts: String,
    message: SlackAPIMessage
) extends SlackAPIResponse

case object ChannelNotFoundError extends SlackAPIResponse

case class UnSupportError(error: String) extends SlackAPIResponse

case class SlackAPIMessage(
    text: String,
    username: String,
    botId: String,
    `type`: String,
    subType: String,
    ts: String
)

object SlackAPIResponse {

  implicit val decoder: Decoder[SlackAPIResponse] =
    new Decoder[SlackAPIResponse] {
      override def apply(c: HCursor): Result[SlackAPIResponse] =
        for {
          ok <- c.downField("ok").as[Boolean]
          maybeErrorString = c.downField("error").as[String].toOption
          result <- if (ok) c.as[PostMessageSuccess]
          else Right(buildError(maybeErrorString))
        } yield result

      private def buildError(error: Option[String]): SlackAPIResponse = {
        error
          .map {
            case "channel_not_found" => ChannelNotFoundError
            case msg                 => UnSupportError(msg)
          }
          .getOrElse(UnSupportError("error does not exist"))
      }
    }

  implicit val messageDecoder: Decoder[SlackAPIMessage] =
    Decoder.forProduct6("text", "username", "bot_id", "type", "subtype", "ts")(
      SlackAPIMessage)
}
