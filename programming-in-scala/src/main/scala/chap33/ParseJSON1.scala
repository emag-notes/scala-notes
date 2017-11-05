package chap33

import java.io.{ BufferedReader, InputStreamReader }

object ParseJSON1 extends JSON1 {

  def main(args: Array[String]): Unit = {
    val reader = new BufferedReader(new InputStreamReader(getClass.getResourceAsStream("address-book.json")))
    println(parseAll(value, reader))
  }

}
