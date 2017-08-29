package chap19

object Queues5 extends App {
  val q = Queue5[Int]() enqueue 1 enqueue 2
  println(q)
}

trait Queue5[T] {
  def head: T
  def tail: Queue5[T]
  def enqueue(x: T): Queue5[T]
}

object Queue5 {

  def apply[T](xs: T*): Queue5[T] = new QueueImpl[T](xs.toList, Nil)

  private class QueueImpl[T](
      private val leading: List[T],
      private val trailing: List[T]
  ) extends Queue5[T] {

    private def mirror =
      if (leading.isEmpty) {
        new QueueImpl[T](trailing.reverse, Nil)
      } else {
        this
      }

    def head: T = mirror.leading.head

    def tail: QueueImpl[T] = {
      val q = mirror
      new QueueImpl(q.leading.tail, q.trailing)
    }

    def enqueue(x: T) =
      new QueueImpl(leading, x :: trailing)

    override def toString =
      leading ::: trailing.reverse mkString ("Queue5(", ", ", ")")
  }
}
