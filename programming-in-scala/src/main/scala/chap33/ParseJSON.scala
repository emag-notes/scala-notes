package chap33

import java.io.{ BufferedReader, InputStreamReader }

object ParseJSON extends JSON {

  def main(args: Array[String]): Unit = {
    val reader = new BufferedReader(new InputStreamReader(getClass.getResourceAsStream("address-book.json")))
    println(parseAll(value, reader))
  }

}
