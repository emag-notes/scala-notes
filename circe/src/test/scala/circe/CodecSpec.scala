package circe

import io.circe
import io.circe.Decoder.Result
import io.circe.{Decoder, Encoder, HCursor, Json}
import io.circe.syntax._
import io.circe.parser.decode
import io.circe.generic.auto._

class CodecSpec extends BaseSpec {

  behavior of "Encoder/Decoder"

  it should "be able to encode/decode a json" in {
    val json: Json = List(1, 2, 3).asJson

    val decodedFromJson: Result[List[Int]] = json.as[List[Int]]
    assert(decodedFromJson === Right(List(1, 2, 3)))

    val decodedFromString: Either[circe.Error, List[Int]] =
      decode[List[Int]](json.noSpaces)
    assert(decodedFromString === Right(List(1, 2, 3)))

    val greeting = Greeting("Hey", Person("Chris"), 3)
    val encodedGreeting: Json = greeting.asJson
    println(s"encodedGreeting: $encodedGreeting")

    val decodedGreeting = encodedGreeting.as[Greeting]
    assert(decodedGreeting === Right(greeting))
  }

  it should "specify json field names" in {
    implicit val decoderUsr: Decoder[User] =
      Decoder.forProduct3("id", "first_name", "last_name")(User)

    implicit val encoderUser: Encoder[User] =
      Encoder.forProduct3("id", "first_name", "last_name")(u =>
        (u.id, u.firstName, u.lastName))

    val user = User(123, "first name", "last name")
    val userJson: Json = user.asJson
    println(s"userJson: $userJson")

    val decodedUserJson: Result[User] = userJson.as[User]
    assert(decodedUserJson === Right(user))
  }

  it should "change the data structure" in {
    implicit val encoder: Encoder[Thing] = new Encoder[Thing] {
      override def apply(t: Thing): Json = Json.obj(
        ("foo", Json.fromString(s"It's a ${t.foo}")),
        ("bar", Json.fromInt(t.bar * 1000)),
      )
    }

    implicit val decoder: Decoder[Thing] = new Decoder[Thing] {
      override def apply(c: HCursor): Result[Thing] =
        for {
          foo <- c.downField("foo").as[String]
          bar <- c.downField("bar").as[Int]
        } yield {
          Thing(foo, bar)
        }
    }

    val thing = Thing("test", 123)
    val encodedThing: Json = thing.asJson
    println(s"encodedThing: $encodedThing")

    val decodedEncodedThing: Result[Thing] = encodedThing.as[Thing]
    println(s"decodedEncodedThing: $decodedEncodedThing")
  }

  it should "be able to define a flexible encoder/decoder" in {
    val postSuccessJsonString = """{
                                  |   "ok": true,
                                  |   "channel": "通知先のチャンネルもしくはユーザーのID",
                                  |   "ts": "1536838057.000100",
                                  |   "message": {
                                  |     "text": "通知くんからの通知",
                                  |     "username": "通知くん",
                                  |     "bot_id": "BotのID",
                                  |     "type": "message",
                                  |     "subtype": "bot_message",
                                  |     "ts": "1536838057.000100"
                                  |   }
                                  | }""".stripMargin

    println(decode[SlackAPIResponse](postSuccessJsonString))

    val channelNotFoundJsonString = """{
                                      |   "ok": false,
                                      |   "error": "channel_not_found"
                                      | }""".stripMargin

    println(decode[SlackAPIResponse](channelNotFoundJsonString))

    val rateLimitedJsonString = """{
                                  |   "ok": false,
                                  |   "error": "rate_limited"
                                  | }""".stripMargin

    println(decode[SlackAPIResponse](rateLimitedJsonString))
  }

  case class Person(name: String)
  case class Greeting(salutation: String, person: Person, exclamationMarks: Int)

  case class User(id: Long, firstName: String, lastName: String)

  case class Thing(foo: String, bar: Int)

}
