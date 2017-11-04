package chap30

trait Tree[+T] {
  def elem: T
  def left: Tree[T]
  def right: Tree[T]
}

object EmptyTree extends Tree[Nothing] {
  override def elem = throw new NoSuchElementException("EmptyTree.elem")

  override def left = throw new NoSuchElementException("EmptyTree.left")

  override def right = throw new NoSuchElementException("EmptyTree.right")
}

class Branch[+T](
    val elem: T,
    val left: Tree[T],
    val right: Tree[T]
) extends Tree[T] {

  override def equals(other: Any): Boolean = other match {
    case that: Branch[_] =>
      (that canEqual this) &&
      this.elem == that.elem &&
      this.left == that.elem &&
      this.right == that.right
    case _ => false
  }

  def canEqual(other: Any) = other.isInstanceOf[Branch[_]]

  override def hashCode(): Int = (elem, left, right).##
}
