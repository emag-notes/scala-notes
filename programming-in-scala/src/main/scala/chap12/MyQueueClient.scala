package chap12

object MyQueueClient extends App {

  val queue = new MyQueue

  queue.put(10)
  queue.put(-1)

  println(s"queue.length: ${queue.length}")
  println(s"queue.get() : ${queue.get()}")

}
