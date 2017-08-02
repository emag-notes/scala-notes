package chap13

class Rocket {

  import Rocket.fuel
  private def canGoHomeAgain: Boolean = fuel > 20

}

object Rocket {

  private def fuel = 10

  def chooseStrategy(rocket: Rocket): Unit =
    if (rocket.canGoHomeAgain) goHome()
    else pickAStar()

  def goHome(): Unit    = {}
  def pickAStar(): Unit = {}

}
