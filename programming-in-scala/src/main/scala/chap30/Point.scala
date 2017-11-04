package chap30

class Point(val x: Int, val y: Int) {

  override def hashCode(): Int = (x, y).##

  override def equals(other: Any): Boolean = other match {
    case that: Point =>
      (that canEqual this) &&
      (this.x == that.x) &&
      (this.y == that.y)
    case _ => false
  }

  def canEqual(other: Any) = other.isInstanceOf[Point]

}
