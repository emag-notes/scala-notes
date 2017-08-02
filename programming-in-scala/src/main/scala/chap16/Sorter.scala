package chap16

class Sorter {

  def isort(xs: List[Int]): List[Int] = xs match {
    case List()   => List()
    case x :: xs1 => insert(x, isort(xs1))
  }

  def insert(x: Int, xs: List[Int]): List[Int] = xs match {
    case List() => x :: xs
    case y :: ys =>
      if (x <= y) x :: xs
      else y :: insert(x, ys)
  }

  def msort[T](less: (T, T) => Boolean)(xs: List[T]): List[T] = {
    def merge(xs: List[T], ys: List[T]): List[T] =
      (xs, ys) match {
        case (Nil, _) => ys
        case (_, Nil) => xs
        case (x :: xs1, y :: ys1) =>
          if (less(x, y)) x :: merge(xs1, ys)
          else y :: merge(xs, ys1)
      }

    val n = xs.length / 2
    if (n == 0) xs
    else {
      val (ys, zs) = xs splitAt n
      merge(msort(less)(ys), msort(less)(zs))
    }
  }

}

object InsertionSortClient extends App {
  val sorter = new Sorter

  println("sorter.isort(List(5, 3, 12)): " + sorter.isort(List(5, 3, 12)))
  println(
    "sorter.msort((x: Int, y: Int) => x < y)(List(5, 7, 1, 3)): " + sorter
      .msort((x: Int, y: Int) => x < y)(List(5, 7, 1, 3))
  )

  val intSort        = sorter.msort((x: Int, y: Int) => x < y) _
  val reverseIntSort = sorter.msort((x: Int, y: Int) => x > y) _

  val mixedInts = List(4, 1, 9, 0, 5, 8, 3, 6, 2, 7)

  println(s"mixedInts: $mixedInts")
  println(s"intSort(mixedInts): ${intSort(mixedInts)}")
  println(s"reverseIntSort(mixedInts): ${reverseIntSort(mixedInts)}")
}
