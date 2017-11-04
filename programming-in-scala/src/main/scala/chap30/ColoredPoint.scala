package chap30

class ColoredPoint(x: Int, y: Int, val color: Color.Value) extends Point(x, y) {

  override def hashCode(): Int = (super.hashCode(), color).##

  override def equals(other: Any): Boolean = other match {
    case that: ColoredPoint =>
      (that canEqual this) &&
      (this.color == that.color) &&
      super.equals(that)
    case _ => false
  }

  override def canEqual(other: Any): Boolean = other.isInstanceOf[ColoredPoint]

}
