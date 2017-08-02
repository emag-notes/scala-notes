package chap12

object RationalClient extends App {

  val half     = new Rational(1, 2)
  val third    = new Rational(1, 3)
  val twoForth = new Rational(2, 4)

  println(half < third)
  println(half > third)
  println(half == twoForth)

}
