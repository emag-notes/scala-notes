package chap19

object Queues1 extends App {
  val q = new SlowHeadQueue[Int](Nil) enqueue 1 enqueue 2
  println(q)
}

class SlowAppendQueue[T](elems: List[T]) {
  def head          = elems.head
  def tail          = new SlowHeadQueue(elems.tail)
  def enqueue(x: T) = new SlowHeadQueue(elems ::: List(x))

  override def toString = elems mkString ("SlowAppendQueue(", ", ", ")")
}
