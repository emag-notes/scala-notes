package chap25

final class RNA1 private (val groups: Array[Int], val length: Int) extends IndexedSeq[Base] {

  @Override
  override def apply(idx: Int): Base = {
    import RNA1._
    if (idx < 0 || length <= idx) {
      throw new IndexOutOfBoundsException
    }
    Base.fromInt(groups(idx / N) >> (idx % N * S) & M)
  }
}

object RNA1 {
  // グループを表現するために必要なビット数
  private val S = 2
  // Int に収まるグループの数
  private val N = 32 / S
  // グループを分離するビットマスク
  private val M = (1 << S) - 1

  def fromSeq(buf: Seq[Base]): RNA1 = {
    val groups = new Array[Int]((buf.length + N - 1) / N)
    for (i <- 0 until buf.length) {
      groups(i / N) |= Base.toInt(buf(i)) << (i % N * S)
    }
    new RNA1(groups, buf.length)
  }

  def apply(bases: Base*) = fromSeq(bases)
}
