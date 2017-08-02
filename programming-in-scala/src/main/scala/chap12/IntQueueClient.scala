package chap12

object IntQueueClient extends App {

  val queue = new BasicIntQueue

  queue.put(10)
  queue.put(20)

  println(queue.get())
  println(queue.get())

}
