package chap12

object FrogClient extends App {

  val frog = new Frog
  println(frog)
  frog.philosophize()

  val phil: Philosophical = frog
  println(phil)
  phil.philosophize()

}
