package chap25

import collection.IndexedSeqLike
import collection.mutable.{ ArrayBuffer, Builder }

final class RNA2 private (val groups: Array[Int], val length: Int)
    extends IndexedSeq[Base]
    with IndexedSeqLike[Base, RNA2] {

  import RNA2._

  override def newBuilder: Builder[Base, RNA2] =
    new ArrayBuffer[Base] mapResult fromSeq

  override def apply(idx: Int): Base = {

    if (idx < 0 || length <= idx) {
      throw new IndexOutOfBoundsException
    }
    Base.fromInt(groups(idx / N) >> (idx % N * S) & M)
  }
}

object RNA2 {
  // グループを表現するために必要なビット数
  private val S = 2
  // Int に収まるグループの数
  private val N = 32 / S
  // グループを分離するビットマスク
  private val M = (1 << S) - 1

  def fromSeq(buf: Seq[Base]): RNA2 = {
    val groups = new Array[Int]((buf.length + N - 1) / N)
    for (i <- 0 until buf.length) {
      groups(i / N) |= Base.toInt(buf(i)) << (i % N * S)
    }
    new RNA2(groups, buf.length)
  }

  def apply(bases: Base*) = fromSeq(bases)
}
