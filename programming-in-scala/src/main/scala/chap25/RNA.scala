package chap25

import collection.IndexedSeqLike
import collection.mutable.{ ArrayBuffer, Builder }
import scala.collection.generic.CanBuildFrom

final class RNA private (val groups: Array[Int], val length: Int)
    extends IndexedSeq[Base]
    with IndexedSeqLike[Base, RNA] {

  import RNA._

  override def newBuilder: Builder[Base, RNA] =
    RNA.newBuilder

  override def apply(idx: Int): Base = {

    if (idx < 0 || length <= idx) {
      throw new IndexOutOfBoundsException
    }
    Base.fromInt(groups(idx / N) >> (idx % N * S) & M)
  }

  // 効率を上げるための foreach のオプションの再実装
  override def foreach[U](f: (Base) => U): Unit = {
    var i = 0
    var b = 0
    while (i < length) {
      b = if (i % N == 0) groups(i / N) else b >>> S
      f(Base.fromInt(b & M))
      i += 1
    }
  }
}

object RNA {
  // グループを表現するために必要なビット数
  private val S = 2
  // Int に収まるグループの数
  private val N = 32 / S
  // グループを分離するビットマスク
  private val M = (1 << S) - 1

  def fromSeq(buf: Seq[Base]): RNA = {
    val groups = new Array[Int]((buf.length + N - 1) / N)
    for (i <- 0 until buf.length) {
      groups(i / N) |= Base.toInt(buf(i)) << (i % N * S)
    }
    new RNA(groups, buf.length)
  }

  def apply(bases: Base*) = fromSeq(bases)

  def newBuilder: Builder[Base, RNA] =
    new ArrayBuffer mapResult fromSeq

  implicit def canBuildFrom: CanBuildFrom[RNA, Base, RNA] =
    new CanBuildFrom[RNA, Base, RNA] {
      override def apply(): Builder[Base, RNA]          = newBuilder
      override def apply(from: RNA): Builder[Base, RNA] = newBuilder
    }
}
