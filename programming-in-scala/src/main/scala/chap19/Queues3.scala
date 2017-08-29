package chap19

object Queues3 extends App {
  val q = Queue3[Int]() enqueue 1 enqueue 2
  println(q)
}

class Queue3[T](
    private val leading: List[T],
    private val trailing: List[T]
) {

  private def mirror =
    if (leading.isEmpty) {
      new Queue3[T](trailing.reverse, Nil)
    } else {
      this
    }

  def head = mirror.leading.head

  def tail = {
    val q = mirror
    new Queue3(q.leading.tail, q.trailing)
  }

  def enqueue(x: T) =
    new Queue3(leading, x :: trailing)

  override def toString =
    leading ::: trailing.reverse mkString ("Queue3(", ", ", ")")
}

object Queue3 {
  def apply[T](xs: T*) = new Queue3[T](xs.toList, Nil)
}
