package circe

import io.circe.Decoder.Result
import io.circe._
import io.circe.parser._

class HCursorSpec extends BaseSpec {

  "HCursor" should "extract values from a json and transform them" in {
    val jsonString = """{
                       |   "id": "c730433b-082c-4984-9d66-855c243266f0",
                       |   "name": "Foo",
                       |   "counts": [
                       |     1,
                       |     2,
                       |     3
                       |   ],
                       |   "values": {
                       |     "bar": true,
                       |     "baz": 100.001,
                       |     "qux": [
                       |       "a",
                       |       "b"
                       |     ]
                       |   }
                       | }""".stripMargin

    val doc: Json = parse(jsonString).getOrElse(Json.Null)
    val cursor: HCursor = doc.hcursor

    val values = cursor.downField("values")
    val baz: Result[Double] = values.downField("baz").as[Double]
    assert(baz === Right(100.001))

    val baz2: Result[Double] = values.get[Double]("baz")
    assert(baz2 === Right(100.001))

    val qux: Result[Seq[String]] = values.downField("qux").as[Seq[String]]
    assert(qux === Right(List("a", "b")))

    val secondQux: Result[String] =
      values.downField("qux").downArray.right.as[String]
    assert(secondQux === Right("b"))

    val reversedNameCursor: ACursor =
      cursor.downField("name").withFocus(_.mapString(_.reverse))
    val deletedHeadArray = reversedNameCursor.up
      .downField("values")
      .downField("qux")
      .downArray
      .deleteGoRight
    val maybeJson = deletedHeadArray.top
    println(maybeJson)
  }
}
