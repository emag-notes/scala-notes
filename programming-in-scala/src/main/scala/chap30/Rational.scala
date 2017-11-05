package chap30

class Rational(n: Int, d: Int) {

  require(d != 0)

  private val g                        = gcd(n.abs, d.abs)
  val numer                            = (if (d < 0) -n else n) / g
  val denom                            = d.abs / g
  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

  override def equals(other: Any): Boolean = other match {
    case that: Rational =>
      (that canEqual this) &&
      numer == that.numer &&
      denom == that.denom
    case _ => false
  }

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[Rational]

  override def hashCode(): Int = (numer, denom).##

  override def toString: String =
    if (denom == 1) numer.toString else numer + "/" + denom
}
