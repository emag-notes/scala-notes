package chap12

class Animal

trait HasLegs

class Frog extends Animal with Philosophical with HasLegs {

  override def philosophize(): Unit =
    println(s"It ain't easy being $toString!")

  override def toString: String = "green"

}
