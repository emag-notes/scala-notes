package chap26

object Twice {

  def apply(s: String): String = s + s
  def unapply(s: String): Option[String] = {
    val length = s.length / 2
    val half   = s.substring(0, length)
    if (half == s.substring(length)) Some(half) else None
  }

}
