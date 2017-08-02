package chap12

object RectangularClient extends App {

  val rect = new Rectangle(new Point(1, 1), new Point(10, 10))

  println(s"rect.left : ${rect.left}")
  println(s"rect.right: ${rect.right}")
  println(s"rect.width: ${rect.width}")

}
