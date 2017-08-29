package chap19

object Queues2 extends App {
  val q = new SlowHeadQueue[Int](Nil) enqueue 1 enqueue 2
  println(q)
}

class SlowHeadQueue[T](smele: List[T]) {
  def head          = smele.last
  def tail          = new SlowHeadQueue(smele.init)
  def enqueue(x: T) = new SlowHeadQueue(x :: smele)

  override def toString = smele.reverse mkString ("SlowHeadQueue(", ", ", ")")
}
