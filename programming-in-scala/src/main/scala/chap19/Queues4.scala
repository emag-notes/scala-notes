package chap19

object Queues4 extends App {
  val q = Queue4[Int]() enqueue 1 enqueue 2
  println(q)
}

class Queue4[T] private (
    private val leading: List[T],
    private val trailing: List[T]
) {

  private def mirror =
    if (leading.isEmpty) {
      new Queue4[T](trailing.reverse, Nil)
    } else {
      this
    }

  def head = mirror.leading.head

  def tail = {
    val q = mirror
    new Queue4(q.leading.tail, q.trailing)
  }

  def enqueue(x: T) =
    new Queue4(leading, x :: trailing)

  override def toString =
    leading ::: trailing.reverse mkString ("Queue4(", ", ", ")")
}

object Queue4 {
  def apply[T](xs: T*) = new Queue4[T](xs.toList, Nil)
}
