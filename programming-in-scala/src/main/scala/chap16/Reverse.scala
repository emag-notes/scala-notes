package chap16

class Reverse {

  def reverseLeft[T](xs: List[T]) =
    (List[T]() /: xs) { (ys, y) =>
      y :: ys
    }

}

object ReverseClient extends App {
  val reverse = new Reverse

  println(s"reverse.reverseLeft(List(5, 7, 1, 3)): ${reverse.reverseLeft(List(5, 7, 1, 3))}")
}
