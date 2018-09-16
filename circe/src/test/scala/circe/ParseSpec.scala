package circe

import io.circe.{Json, ParsingFailure}
import io.circe.parser._

class ParseSpec extends BaseSpec {

  "circe" should "be able to parse a json" in {
    val jsonString =
      """{
        |  "foo" : "foo value",
        |  "bar" : {
        |    "bar_child" : "bar child value"
        |  },
        |  "array":[
        |    { "content" : 1 },
        |    { "content" : 2 },
        |    { "content" : 3 }
        |  ]
        |}""".stripMargin

    val parsedJson: Either[ParsingFailure, Json] = parse(jsonString)

    println(parsedJson)

    val invalidJsonString = "Invalid Json"

    println(parse(invalidJsonString))
  }
}
