package chap25

object PrefixMapClient extends App {

  val m = PrefixMap("hello" -> 5, "hi" -> 2)
  println(m)
  println(m map { case (k, v) => (k + "!", "x" * v)})

  val e: PrefixMap[String] = PrefixMap.empty[String]
  println(e)

}
